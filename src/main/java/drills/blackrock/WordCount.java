package drills.blackrock;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/** Word count: single-JVM analogue of MapReduce using a parallel stream. */
class WordCount {
    private static final Pattern NON_WORD = Pattern.compile("(?U)\\W+");   // Unicode-aware, compiled once

    static Map<String, Long> wordCounts(Path path) throws IOException {
        try (Stream<String> lines = Files.lines(path)) {                     // closes the file handle
            return lines.parallel()
                    .flatMap(NON_WORD::splitAsStream)                        // map: line -> words
                    .map(w -> w.toLowerCase(Locale.ROOT))
                    .filter(w -> !w.isEmpty())
                    .collect(Collectors.groupingByConcurrent(               // shuffle + reduce
                            Function.identity(), Collectors.counting()));
        }
    }

    /** Most frequent first; ties broken alphabetically so output is deterministic. */
    static List<Map.Entry<String, Long>> topN(Map<String, Long> counts, int n) {
        return counts.entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue(Comparator.reverseOrder())
                        .thenComparing(Map.Entry.comparingByKey()))
                .limit(n)
                .toList();
    }

    public static void main(String[] args) throws IOException {
        Path path;
        boolean temp = args.length == 0;
        if (temp) {                                                          // no arg: use a sample file
            path = Files.createTempFile("wordcount", ".txt");
            Files.writeString(path, """
                    The quick brown fox jumps over the lazy dog.
                    THE DOG barks; the fox runs!

                      "Café" is naïve, isn't it? The café is open.
                    """);
        } else {
            path = Path.of(args[0]);
        }

        try {
            Map<String, Long> counts = wordCounts(path);
            System.out.println("distinct words: " + counts.size()
                    + ", total words: " + counts.values().stream().mapToLong(Long::longValue).sum());
            topN(counts, 10).forEach(e -> System.out.printf("%-8s %d%n", e.getKey(), e.getValue()));
        } finally {
            if (temp) Files.deleteIfExists(path);
        }
    }
}
