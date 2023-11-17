package in.mathys.tp7;

public class Transaction {
    private final Wallet wallet;
    private final Wallet destinationWallet;
    private final int isepCoins;
    private boolean payed;

    public Transaction(Wallet wallet, Wallet destinationWallet, int isepCoins, boolean payed) {
        this.wallet = wallet;
        this.destinationWallet = destinationWallet;
        this.isepCoins = isepCoins;
        this.payed = payed;
    }

    public Wallet getWallet() {
        return wallet;
    }

    public Wallet getDestinationWallet() {
        return destinationWallet;
    }

    public int getIsepCoin() {
        return isepCoins;
    }

    public boolean isPayed() {
        return payed;
    }

    public boolean pay() {
        if(!isPayed() && !wallet.equals(destinationWallet) && wallet.canPay(isepCoins)) {
            wallet.addIsepCoins(-isepCoins);
            destinationWallet.addIsepCoins(isepCoins);
            payed = true;
            return true;
        }
        else
            return false;
    }
}
