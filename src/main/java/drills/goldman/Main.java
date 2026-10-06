package drills.goldman;

import java.util.*;

class Expense {
    String payer;
    long totalCents;

    public Expense(String payer, long totalCents) {
        if (payer == null || payer.trim().isEmpty()) {
            throw new IllegalArgumentException("Payer cannot be null or empty");
        }
        if (totalCents < 0) {
            throw new IllegalArgumentException("Expense total cannot be negative");
        }
        this.payer = payer;
        this.totalCents = totalCents;
    }

}

class BillSplitter {

    private final List<String> friends = new ArrayList<>();
    private final Map<String, Long> balance = new HashMap<>();

    public BillSplitter() {
        // Allows a splitter to be created without a fixed friend list and to accept
        // participants dynamically when individual expenses are added.
    }

    public BillSplitter(List<String> friends) {
        if (friends == null || friends.isEmpty()) {
            throw new IllegalArgumentException("Friends list cannot be null or empty");
        }
        registerFriends(friends);
    }

    private void registerFriends(List<String> participants) {
        if (participants == null || participants.isEmpty()) {
            throw new IllegalArgumentException("Friends list cannot be null or empty");
        }

        for (String friend : participants) {
            if (friend == null || friend.trim().isEmpty()) {
                throw new IllegalArgumentException("Friend names cannot be null or empty");
            }
            if (!this.friends.contains(friend)) {
                this.friends.add(friend);
                this.balance.put(friend, 0L);
            }
        }
    }

    // Split uneven cents starting from the payer and continuing in friend order.
    private Map<String, Long> splitShares(List<String> participants, long total, String payer) {
        if (participants == null || participants.isEmpty()) {
            throw new IllegalArgumentException("Participants list cannot be null or empty");
        }
        if (!participants.contains(payer)) {
            throw new IllegalArgumentException("Payer must be one of the friends");
        }

        Map<String, Long> shares = new LinkedHashMap<>();
        int n = participants.size();

        long base = total / n;
        long remainder = total % n;

        for (String friend : participants) {
            shares.put(friend, base);
        }

        int idx = participants.indexOf(payer);
        while (remainder > 0) {
            String friend = participants.get(idx);
            shares.put(friend, shares.get(friend) + 1L);
            remainder--;
            idx = (idx + 1) % n;
        }

        return shares;
    }

    private Map<String, Long> splitShares(long total, String payer) {
        return splitShares(friends, total, payer);
    }

    public void addExpense(Expense e, List<String> participants) {
        if (e == null) {
            throw new IllegalArgumentException("Expense cannot be null");
        }
        if (e.totalCents < 0) {
            throw new IllegalArgumentException("Expense total cannot be negative");
        }

        registerFriends(participants);
        if (!this.friends.contains(e.payer)) {
            addParticipant(e.payer);
        }

        Map<String, Long> shares = splitShares(participants, e.totalCents, e.payer);
        for (String friend : participants) {
            long friendShare = shares.get(friend);
            long delta = friend.equals(e.payer)
                    ? (e.totalCents - friendShare)
                    : -friendShare;
            balance.put(friend, balance.getOrDefault(friend, 0L) + delta);
        }
    }

    public void addExpense(Expense e) {
        if (e == null) {
            throw new IllegalArgumentException("Expense cannot be null");
        }
        if (this.friends.isEmpty()) {
            throw new IllegalArgumentException("No friends list configured for this expense");
        }
        if (!friends.contains(e.payer)) {
            throw new IllegalArgumentException("Payer must be one of the friends");
        }
        if (e.totalCents < 0) {
            throw new IllegalArgumentException("Expense total cannot be negative");
        }

        Map<String, Long> shares = splitShares(e.totalCents, e.payer);
        for (String friend : friends) {
            long friendShare = shares.get(friend);
            long delta = friend.equals(e.payer)
                    ? (e.totalCents - friendShare)
                    : -friendShare;
            balance.put(friend, balance.getOrDefault(friend, 0L) + delta);
        }
    }

    private void addParticipant(String friend) {
        if (friend == null || friend.trim().isEmpty()) {
            throw new IllegalArgumentException("Friend names cannot be null or empty");
        }
        if (!this.friends.contains(friend)) {
            this.friends.add(friend);
            this.balance.put(friend, 0L);
        }
    }

    public Map<String, Long> getBalance() {
        return balance;
    }
}

class Settlement {

    static class Transaction {
        String from, to;
        long amount;

        Transaction(String from, String to, long amount) {
            this.from = from;
            this.to = to;
            this.amount = amount;
        }

        public String toString() {
            return from + " pays " + to + ": " + amount + " cents";
        }
    }

    public List<Transaction> settle(Map<String, Long> balance) {
        List<Transaction> result = new ArrayList<>();

        List<String> creditors = new ArrayList<>();
        List<String> debtors = new ArrayList<>();
        Map<String, Long> creditAmt = new HashMap<>();
        Map<String, Long> debtAmt = new HashMap<>();

        for (String f : balance.keySet()) {
            long b = balance.get(f);
            if (b > 0) {
                creditors.add(f);
                creditAmt.put(f, b);
            } else if (b < 0) {
                debtors.add(f);
                debtAmt.put(f, -b);
            }
        }

        int c = 0, d = 0;

        while (c < creditors.size() && d < debtors.size()) {
            String cred = creditors.get(c);
            String debt = debtors.get(d);

            long pay = Math.min(creditAmt.get(cred), debtAmt.get(debt));

            result.add(new Transaction(debt, cred, pay));

            creditAmt.put(cred, creditAmt.get(cred) - pay);
            debtAmt.put(debt, debtAmt.get(debt) - pay);

            if (creditAmt.get(cred) == 0) c++;
            if (debtAmt.get(debt) == 0) d++;
        }

        return result;
    }
}

public class Main {
    public static void main(String[] args) {

        List<String> friends = Arrays.asList("Ana", "Ben", "Cara");
        BillSplitter splitter = new BillSplitter(friends);

        splitter.addExpense(new Expense("Ana", 10000)); // Restaurant
        splitter.addExpense(new Expense("Cara", 4500)); // Movie
        splitter.addExpense(new Expense("Ben", 850));   // Coffee
        splitter.addExpense(new Expense("Ben", 850), List.of("Ben", "Cara"));   // Coffee

        System.out.println("Final Balances:");
        splitter.getBalance().forEach((f, b) ->
                System.out.println(f + ": " + b + " cents"));

        Settlement settlement = new Settlement();
        List<Settlement.Transaction> txns = settlement.settle(splitter.getBalance());

        System.out.println("\nSettlement:");
        txns.forEach(System.out::println);
    }
}
