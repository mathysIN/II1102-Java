package in.mathys.tp7;

import java.util.LinkedList;
import java.util.Queue;


public class Block {
    private final Queue<Transaction> transactions;
    private final int MAX_TRANSACTION = 10;

    public Block(Queue<Transaction> transactions) {
        this.transactions = transactions;
    }

    public Block() {
        this.transactions = new LinkedList<>();
    }

    public Queue<Transaction> getTransactions() {
        return transactions;
    }

    public Block add(Transaction transaction) {
        if (transactions.size() < 10) {
            transactions.add(transaction);
        }
        if(isMaxTransaction()) {
            for(Transaction tempTransaction : transactions) {
                tempTransaction.pay();
            }
        }
        return this;
    }

    public boolean isMaxTransaction() {
        return transactions.size() == MAX_TRANSACTION;
    }
}
