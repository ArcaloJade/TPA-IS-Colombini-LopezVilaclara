package Main;

import org.junit.jupiter.api.function.Executable;

import java.util.ArrayList;
import java.util.List;

public class GiftCard {
    public String NotClaimed = "The gift card has not been claimed yet.";
    public String AlreadyClaimed = "The gift card has already been claimed.";
    public String ExpiredToken = "The token has expired.";
    public String InvalidMerchantKey = "The merchant key provided is invalid.";
    public String CannotChargeNegativeAmount = "Cannot charge a negative amount.";
    public String CannotChargeMoreThanWhatItHas = "Cannot charge more than the current balance.";
    public String CannotHaveInitialNegativeBalance = "The initial balance cannot be negative.";

    private final String id;
    private final String merchantKey;
    private int balance;
    private boolean claimed;
    private final List<Movement> log;

    public GiftCard(String id, int initialBalance, String merchantKey) {
        if (initialBalance < 0) {
            throw new IllegalArgumentException(CannotHaveInitialNegativeBalance);
        }
        this.id = id;
        this.balance = initialBalance;
        this.merchantKey = merchantKey;
        this.claimed = false;
        this.log = new ArrayList<>();
    }

    public void claim(Token validToken) {
        if (claimed) {
            throw new IllegalStateException(AlreadyClaimed);
        }
        if (validToken.isExpired()) {
            throw new SecurityException(ExpiredToken);
        }

        this.claimed = true;
    }

    public GiftCard charge(int amount, String merchantKey) {
        if (!claimed) {
            throw new IllegalStateException(NotClaimed);
        }
        if (!this.merchantKey.equals(merchantKey)) {
            throw new SecurityException(InvalidMerchantKey);
        }
        if (amount <= 0) {
            throw new IllegalArgumentException(CannotChargeNegativeAmount);
        }
        if (amount > balance) {
            throw new IllegalStateException(CannotChargeMoreThanWhatItHas);
        }

        this.balance -= amount;
        this.log.add(new Movement(amount));
        return this;
    }

    public ArrayList<Movement> getLog(Token validToken) {
        if (!claimed) {
            throw new IllegalStateException(NotClaimed);
        }
        return new ArrayList<>(log);
    }

    public int getBalance() {
        return balance;
    }

    public String getId() {
        return id;
    }
}
