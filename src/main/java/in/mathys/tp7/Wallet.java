package in.mathys.tp7;
import java.util.Random;

public class Wallet {
    private String owner;
    private int token;
    private int isepCoins;
    private static Random random = new Random();

    public Wallet(String owner) {
        this.owner = owner;
        this.token = random.nextInt(10000);
    }

    public String getOwner() {
        return owner;
    }

    public int getToken() {
        return token;
    }

    public int getIsepCoins() {
        return isepCoins;
    }

    public void setIsepCoins(int isepCoins) {
        this.isepCoins = isepCoins;
    }

    public void addIsepCoins(int isepCoins) {
        setIsepCoins(this.isepCoins + isepCoins);
    }

    public boolean canPay(int isepCoins) {
        return this.isepCoins - isepCoins > 0;
    }
}
