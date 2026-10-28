package drills.blackrock;

import java.io.*;
import java.util.*;

import static java.util.stream.Collectors.joining;

/**
 * Fun With Anagrams
 */
public class FunWithAnagram {

    /*
     * Complete the 'funWithAnagrams' function below.
     *
     * The function is expected to return a STRING_ARRAY.
     * The function accepts STRING_ARRAY text as parameter.
     *
     * Remove every string that is an anagram of an EARLIER string, then return the rest sorted.
     */
    /* n = number of strings, m = max string length
     * Time Complexity:  O(n * m log m) to build keys + O(n log n * m) for the final sort
     * Space complexity: O(n * m)
     */

    public static List<String> funWithAnagrams(List<String> text) {
        List<String> result = new ArrayList<>();
        Set<String> seen = new HashSet<>();               // sorted-letter signature of each kept word
        for (String str : text) {
            char[] array = str.toCharArray();
            Arrays.sort(array);
            if (seen.add(new String(array))) {            // first of its anagram group -> keep
                result.add(str);
            }
        }
        Collections.sort(result);
        return result;
    }

    private static void testFunWithAnagrams(List<String> input, List<String> expected) {
        List<String> actual = funWithAnagrams(input);
        if (!expected.equals(actual)) {
            throw new AssertionError("Expected " + expected + " but got " + actual);
        }
    }

    public static void main(String[] args) throws IOException {
        testFunWithAnagrams(
                List.of("code", "doce", "ecod", "framer", "frame"),
                List.of("code", "frame", "framer"));
        testFunWithAnagrams(
                List.of("listen", "silent", "enlist", "abc", "bca", "xyz"),
                List.of("abc", "listen", "xyz"));
        testFunWithAnagrams(List.of(), List.of());
        System.out.println("All funWithAnagrams tests passed.");
    }

}
