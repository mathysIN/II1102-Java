package in.mathys.tp7;


import java.util.*;

public class Main {
    private static Map<Integer, Wallet> wallets = new HashMap<Integer, Wallet>();
    private static List<Block> blockchain = new ArrayList<>();
    private static Random random = new Random();

    public static void main(String[] args) {
        String[] studentNames = {"Adam", "Hamallah", "Mathys", "Nina", "BruhMan"};
        for (String name : studentNames) {
            Wallet wallet = new Wallet(name);
            wallet.setIsepCoins(10);
            wallets.put(wallet.getToken(), wallet);
        }
        blockchain.add(new Block());
        simulation();
    }

    public static void simulation() {
        for (int i = 0; i < 55; i++) {
            Block block = blockchain.get(blockchain.size() - 1);

            if(block.isMaxTransaction())
            {
                block = new Block();
                blockchain.add(block);
            }

            Wallet wallet = wallets.get(wallets.keySet().toArray()[random.nextInt(wallets.size())]);
            Wallet wallet2 = wallets.get(wallets.keySet().toArray()[random.nextInt(wallets.size())]);
            int isepCoins = random.nextInt(5);

            Transaction transaction = new Transaction(wallet, wallet2, isepCoins, false);
            block.add(transaction);

        }

        Wallet maleciousWallet = wallets.get(wallets.keySet().toArray()[0]);

        printTransactions();
        System.out.println("\n> " + maleciousWallet.getOwner() + " est le priopriétaire du wallet malicieux" + "\n");
        printUserTransactions(maleciousWallet);
        System.out.println();
        printGraph();
    }

    public static void printTransactions() {
        System.out.println("> Transactions");
        System.out.println();
        for (int i = 0; i < blockchain.size(); i++) {
            Block block = blockchain.get(blockchain.size() - 1 - i);

            for (int j = 0; j < block.getTransactions().size(); j++) {
                Queue<Transaction> transactions = block.getTransactions();
                Transaction transaction = transactions.stream().toList().get(transactions.size() - j - 1);
                System.out.println(transaction.getWallet().getOwner() + " sended " + transaction.getIsepCoin() + " ISEP coin to " + transaction.getDestinationWallet().getOwner());
            }
        }
    }

    public static void printUserTransactions(Wallet wallet) {
        HashMap<Wallet, Integer> userTransactionCount = new HashMap<>();
        HashMap<Wallet, Integer> userIsepCoinsCoint = new HashMap<>();
        wallets.forEach((integer, w) -> {
            userTransactionCount.put(w, 0);
            userIsepCoinsCoint.put(w, 0);
        });

        for (Block block : blockchain) {
            for(Transaction transaction : block.getTransactions()) {
                userTransactionCount.put(transaction.getWallet(), userTransactionCount.get(transaction.getWallet()) + 1);
                userTransactionCount.put(transaction.getDestinationWallet(), userTransactionCount.get(transaction.getDestinationWallet()) + 1);
                userIsepCoinsCoint.put(transaction.getWallet(), userIsepCoinsCoint.get(transaction.getWallet()) + transaction.getIsepCoin());
                userIsepCoinsCoint.put(transaction.getDestinationWallet(), userIsepCoinsCoint.get(transaction.getDestinationWallet()) + transaction.getIsepCoin());
            }
        }

        System.out.println("> Wallet de " + wallet.getOwner() + " :");
        System.out.println("Transactions = " + userTransactionCount.get(wallet));
        System.out.println("Isep Coins Echangés = " + userIsepCoinsCoint.get(wallet));
    }

    public static void printGraph() {
        System.out.println("> Graphe des transactions");

        ArrayList<Wallet> tempWallet = new ArrayList();
        int maxSize = 0;
        final int EXTRA_SPACE = 3;

        for (Wallet wallet : wallets.values()) {
            tempWallet.add(wallet);
            if(maxSize < wallet.getOwner().length()) maxSize = wallet.getOwner().length();
        }

        int walletSize = wallets.size();
        int[][] walletGraph = new int[walletSize][walletSize];
        for (int i = 0; i < walletSize; i++) {
            for (int j = 0; j < walletSize; j++) {
                walletGraph[i][j] = 0;
            }
        }

        for (Block block : blockchain) {
            for(Transaction transaction : block.getTransactions()) {
                if(!transaction.isPayed()) continue;
                int index = tempWallet.indexOf(transaction.getWallet());
                int index2 = tempWallet.indexOf(transaction.getDestinationWallet());
                walletGraph[index][index2] = 1;
            }
        }
        System.out.print(" ".repeat(maxSize + EXTRA_SPACE));

        for (Wallet wallet : tempWallet) {
            System.out.print(wallet.getOwner() + " ".repeat(maxSize - wallet.getOwner().length() + EXTRA_SPACE));
        }
        for (int i = 0; i < walletSize; i++) {
            System.out.println();
            for (int j = 0; j < walletSize; j++) {
                if(j == 0) {
                    System.out.print(tempWallet.get(i).getOwner() + " ".repeat(maxSize - tempWallet.get(i).getOwner().length() + EXTRA_SPACE));
                }
                System.out.print(walletGraph[i][j] + " ".repeat(maxSize + EXTRA_SPACE - 1));

            }
            System.out.println();
        }
    }
}