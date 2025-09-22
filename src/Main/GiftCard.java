package Main;

import org.junit.jupiter.api.function.Executable;

import java.util.*;

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
    private long balance;
    private boolean claimed;
    private String claimedBy;
    private final Map<Integer, Movement> log;
    private int movementCounter;

    public GiftCard(String id, long initialBalance, String merchantKey) {
        if (initialBalance < 0) {
            throw new IllegalArgumentException(CannotHaveInitialNegativeBalance);
        }
        this.id = id;
        this.balance = initialBalance;
        this.merchantKey = merchantKey;
        this.claimed = false;
        this.claimedBy = "";
        this.log = new HashMap<>();
        this.movementCounter = 0;
    }

    public void claim(Token validToken, String username) {
        if (claimed) {
            throw new IllegalStateException(AlreadyClaimed);
        }
        if (validToken.isExpired()) {
            throw new SecurityException(ExpiredToken);
        }

        this.claimed = true;
        this.claimedBy = username;
    }


    public GiftCard charge(long amount, String merchantKey) {
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
        this.addMovement(new Movement(amount));
        return this;
    }

    public void addMovement(Movement m) {
        log.put(++movementCounter, m);
    }

    public Map<Integer, Movement> getLog(Token token) {
        if (!claimed) {
            throw new IllegalStateException(NotClaimed);
        }
        return Collections.unmodifiableMap(log);
    }

    public long getBalance() {
        return balance;
    }

    public String claimedBy() { return claimedBy; }

    public String getId() {
        return id;
    }
}
