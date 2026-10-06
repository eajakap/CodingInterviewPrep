package drills.goldman;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class FriendsExpenseSharing {

    public static int[] splitUnevenAmount(int totalCents, int nFriends, int payerIndex) {
        int[] shares = new int[nFriends];

        int baseShare = totalCents / nFriends;
        int remainder = totalCents % nFriends;

        // Everyone gets the base share
        for (int i = 0; i < nFriends; i++) {
            shares[i] = baseShare;
        }

        // Distribute remainder starting from payer
        int index = payerIndex;
        while (remainder > 0) {
            shares[index]++;
            remainder--;
            index = (index + 1) % nFriends;  // wrap around
        }

        return shares;
    }

    // Split expense equally among all friends
    private static void splitExpense(Map<String, Double> balance, String[] friends,
                                     String payer, double totalCost) {

        double share = totalCost / friends.length;

        for (String f : friends) {
            if (f.equals(payer)) {
                balance.put(f, balance.get(f) + (totalCost - share));
            } else {
                balance.put(f, balance.get(f) - share);
            }
        }
    }

    // Print who owes whom
    private static void settle(Map<String, Double> balance) {
        for (String f1 : balance.keySet()) {
            for (String f2 : balance.keySet()) {
                if (!f1.equals(f2)) {
                    double diff = balance.get(f1) - balance.get(f2);
                    if (diff > 0) {
                        System.out.println(f2 + " owes " + f1 + ": " + diff / 2);
                    }
                }
            }
        }
    }

    public static void main(String[] args) {

        // Three friends
        String[] friends = {"A", "B", "C"};

        // Balance sheet: positive means others owe him, negative means he owes others
        Map<String, Double> balance = new HashMap<>();
        for (String f : friends) balance.put(f, 0.0);

        // --- Restaurant ---
        double restaurantCost = 90;   // total bill
        String restaurantPayer = "A"; // A pays
        splitExpense(balance, friends, restaurantPayer, restaurantCost);

        // --- Movie ---
        double movieCost = 60;        // total bill
        String moviePayer = "C";      // C pays
        splitExpense(balance, friends, moviePayer, movieCost);

        // Final settlement
        System.out.println("Final Balances:");
        for (String f : friends) {
            System.out.println(f + " balance: " + balance.get(f));
        }

        System.out.println("\nWho owes whom:");
        settle(balance);
    }

    static class ExpenseSharing {

        private final int nFriends;
        private final long[] balance; // positive = others owe him, negative = he owes others

        public ExpenseSharing(int nFriends) {
            this.nFriends = nFriends;
            this.balance = new long[nFriends];
        }

        // Split cost among friends, distributing uneven cents starting from payer
        private long[] splitUnevenAmount(long totalCents, int payerIndex) {
            long[] shares = new long[nFriends];

            long baseShare = totalCents / nFriends;
            long remainder = totalCents % nFriends;

            // Everyone gets base share
            Arrays.fill(shares, baseShare);

            // Distribute leftover cents starting from payer
            int idx = payerIndex;
            while (remainder > 0) {
                shares[idx]++;
                remainder--;
                idx = (idx + 1) % nFriends;
            }

            return shares;
        }

        // Record an activity
        public void addActivity(long totalCents, int payerIndex) {
            long[] shares = splitUnevenAmount(totalCents, payerIndex);

            for (int i = 0; i < nFriends; i++) {
                if (i == payerIndex) {
                    // Payer paid full amount but owes only his share
                    balance[i] += (totalCents - shares[i]);
                } else {
                    // Others owe their share
                    balance[i] -= shares[i];
                }
            }
        }

        // Print final balances
        public void printBalances() {
            System.out.println("Final Balances (in cents):");
            for (int i = 0; i < nFriends; i++) {
                System.out.println("Friend " + i + ": " + balance[i]);
            }
        }

        // Compute who owes whom
        public void printSettlement() {
            System.out.println("\nSettlement:");
            for (int i = 0; i < nFriends; i++) {
                for (int j = 0; j < nFriends; j++) {
                    if (i == j) continue;

                    long diff = balance[j] - balance[i];
                    if (diff > 0) {
                        long amount = diff / 2;
                        if (amount > 0) {
                            System.out.println("Friend " + i + " owes Friend " + j + ": " + amount + " cents");
                        }
                    }
                }
            }
        }

        // Demo
        public static void main(String[] args) {
            ExpenseSharing sharing = new ExpenseSharing(3);

            // Activity 1: Restaurant
            sharing.addActivity(100, 0); // 100 cents, payer = Friend 0

            // Activity 2: Movie
            sharing.addActivity(101, 2); // 101 cents, payer = Friend 2

            sharing.printBalances();
            sharing.printSettlement();
        }
    }

    static class Expense {

        private final long totalCents;
        private final int payerIndex;
        private final int nFriends;

        public Expense(long totalCents, int payerIndex, int nFriends) {
            this.totalCents = totalCents;
            this.payerIndex = payerIndex;
            this.nFriends = nFriends;
        }

        // Split cost evenly, distributing leftover cents starting from payer
        public long[] computeShares() {
            long[] shares = new long[nFriends];

            long baseShare = totalCents / nFriends;
            long remainder = totalCents % nFriends;

            Arrays.fill(shares, baseShare);

            int idx = payerIndex;
            while (remainder > 0) {
                shares[idx]++;
                remainder--;
                idx = (idx + 1) % nFriends;
            }

            return shares;
        }

        public long getTotalCents() {
            return totalCents;
        }

        public int getPayerIndex() {
            return payerIndex;
        }
    }

    static class BillSplitter {

        private final int nFriends;
        private final long[] balance; // positive = others owe him, negative = he owes others

        public BillSplitter(int nFriends) {
            this.nFriends = nFriends;
            this.balance = new long[nFriends];
        }

        public void addExpense(Expense expense) {
            long[] shares = expense.computeShares();
            long total = expense.getTotalCents();
            int payer = expense.getPayerIndex();

            for (int i = 0; i < nFriends; i++) {
                if (i == payer) {
                    balance[i] += (total - shares[i]); // payer paid full amount
                } else {
                    balance[i] -= shares[i];           // others owe their share
                }
            }
        }

        public void printBalances() {
            System.out.println("Final Balances (in cents):");
            for (int i = 0; i < nFriends; i++) {
                System.out.println("Friend " + i + ": " + balance[i]);
            }
        }

        public void printSettlement() {
            System.out.println("\nSettlement:");
            for (int i = 0; i < nFriends; i++) {
                for (int j = 0; j < nFriends; j++) {
                    if (i == j) continue;

                    long diff = balance[j] - balance[i];
                    if (diff > 0) {
                        long amount = diff / 2;
                        if (amount > 0) {
                            System.out.println("Friend " + i + " owes Friend " + j + ": " + amount + " cents");
                        }
                    }
                }
            }
        }

        public static void main(String[] args) {

            BillSplitter splitter = new BillSplitter(3);

            // Activity 1: Restaurant
            splitter.addExpense(new Expense(100, 0, 3)); // 100 cents, payer = Friend 0

            // Activity 2: Movie
            splitter.addExpense(new Expense(101, 2, 3)); // 101 cents, payer = Friend 2

            splitter.printBalances();
            splitter.printSettlement();
        }
    }

}
