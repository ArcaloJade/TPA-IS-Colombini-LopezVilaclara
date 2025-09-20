package Main;

public class Merchant {

    public static final String InvalidMerchantKey = "Invalid merchant key";
    public static final String InactiveMerchant = "Merchant is inactive";
    public static final String EmptyName = "Name must not be empty";

    private final String key;
    private String name;
    private boolean active;

    public Merchant(String key, String name) {
        if (key == null || key.isBlank()) {
            throw new IllegalArgumentException(InvalidMerchantKey);
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException(EmptyName);
        }
        this.key = key;
        this.name = name.trim();
        this.active = true;
    }

    public boolean matchesKey(String candidateKey) {
        return candidateKey != null && candidateKey.equals(this.key);
    }

    public void assertValidKey(String candidateKey) {
        if (!matchesKey(candidateKey)) {
            throw new SecurityException(InvalidMerchantKey);
        }
    }

    public void assertActive() {
        if (!this.active) {
            throw new IllegalStateException(InactiveMerchant);
        }
    }

    public void deactivate() { this.active = false; }
    public void activate()   { this.active = true;  }

    public String getKey()   { return key; }
    public String getName()  { return name; }
    public boolean isActive() { return active; }

    @Override
    public boolean equals(Object o) { // SACAR??? ***
        if (this == o) return true;
        if (!(o instanceof Merchant)) return false;
        Merchant that = (Merchant) o;
        return key.equals(that.key);
    }

    @Override
    public String toString() {
        return "Merchant{key='" + key + "', name='" + name + "', active=" + active + "}";
    }

    public void charge(GiftCard giftCard, long amount) {
        giftCard.charge(amount, key);
    }

}
