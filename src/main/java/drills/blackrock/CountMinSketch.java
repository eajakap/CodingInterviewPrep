package drills.blackrock;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

class CountMinSketch {
    private final long[][] t;
    private final int d, w;
    private final int[] seeds;
    CountMinSketch(int d, int w) {
        this.d = d;
        this.w = w;
        t = new long[d][w];
        seeds = new Random(42).ints(d).toArray();
    }
    private int idx(String x, int i) {
        return Math.floorMod(x.hashCode() * 31 + seeds[i] * 0x9E3779B9, w);
    }
    void add(String x) {
        for (int i = 0; i < d; i++)
            t[i][idx(x, i)]++;
    }
    long estimate(String x) {
        long m = Long.MAX_VALUE;
        for (int i = 0; i < d; i++)
            m = Math.min(m, t[i][idx(x, i)]);
        return m;
    }

    public static void main(String[] args) {
        // Skewed stream: "item0" is very common, "item4999" is rare (Zipf-like, like real traffic/tickers)
        int distinct = 5_000, events = 200_000;
        Random rnd = new Random(7);
        String[] stream = new String[events];
        for (int k = 0; k < events; k++)
            stream[k] = "item" + (int) (distinct * Math.pow(rnd.nextDouble(), 3));

        Map<String, Long> exact = new HashMap<>();
        for (String s : stream) exact.merge(s, 1L, Long::sum);

        CountMinSketch cms = new CountMinSketch(5, 1_000);       // 5 x 1000 longs = 40 KB
        for (String s : stream) cms.add(s);

        System.out.printf("%,d events, %,d distinct keys%n%n", events, exact.size());
        System.out.printf("%-10s %8s %8s %6s%n", "key", "exact", "cms", "over");
        for (String key : List.of("item0", "item1", "item10", "item100", "item1000", "item4000", "never-seen")) {
            long ex = exact.getOrDefault(key, 0L), est = cms.estimate(key);
            System.out.printf("%-10s %8d %8d %6d%n", key, ex, est, est - ex);
        }

        // Guarantee: estimate >= exact always; with prob 1-e^-d, error <= (e/w) * events
        System.out.printf("%nerror bound (e/w)*N = %.0f%n", Math.E / 1_000 * events);
        for (int depth : new int[]{1, 2, 5, 10}) {
            CountMinSketch s = new CountMinSketch(depth, 1_000);
            for (String x : stream) s.add(x);
            long under = 0, totalOver = 0;
            for (Map.Entry<String, Long> e : exact.entrySet()) {
                long diff = s.estimate(e.getKey()) - e.getValue();
                if (diff < 0) under++;
                totalOver += diff;
            }
            System.out.printf("d=%-2d  avg overestimate=%7.1f  underestimates=%d%n",
                    depth, (double) totalOver / exact.size(), under);
        }
    }
}