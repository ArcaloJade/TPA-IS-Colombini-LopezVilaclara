package Main;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class User {
    private final String username;
    private final String password;
    private Token token;
    private final Map<String, GiftCard> claimedGiftCards = new HashMap<>();

    public static final String InvalidUsername = "Username must not be empty";
    public static final String InvalidPassword = "Password must not be empty";

    public User(String username, String password) {
        if (username == null || username.isBlank()) {
            throw new IllegalArgumentException(InvalidUsername);
        }
        if (password == null || password.isBlank()) {
            throw new IllegalArgumentException(InvalidPassword);
        }
        this.username = username.trim();
        this.password = password;
    }

    public String getUsername() { return username; }

    public Token getToken() { return token; }

    public void assignToken(Token token) {
        this.token = token;
    }


    public void claimGiftCard(GiftCard giftCard) {
        if (giftCard == null) throw new IllegalArgumentException("GiftCard cannot be null");
        claimedGiftCards.put(giftCard.getId(), giftCard);
    }

    public Map<String, GiftCard> getClaimedGiftCards() {
        return Collections.unmodifiableMap(claimedGiftCards);
    }

    public boolean matchesPassword(String candidate) {
        return candidate != null && candidate.equals(password);
    }

}