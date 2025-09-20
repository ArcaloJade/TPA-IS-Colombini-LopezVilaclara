package Main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class User {
    private final String username;
    private final String password;
    private Token token;
    //private final List<GiftCard> claimedGiftCards = new ArrayList<>(); // PASAR A MAP? COMO SIN LISTAS NI ARRAYS? ***

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

    /** Usado por el sistema al autenticar */
    public void assignToken(Token token) {
        this.token = token;
    }

    /** El usuario reclama una GiftCard (ejecutado via el sistema, pero guardado acá) */

    /**
    public void claimGiftCard(GiftCard giftCard) {
        if (giftCard == null) throw new IllegalArgumentException("GiftCard cannot be null"); // PEDIR TOKEN? ***
        claimedGiftCards.add(giftCard);
    }
     */

    /**
    public List<GiftCard> getClaimedGiftCards() {
        return Collections.unmodifiableList(claimedGiftCards);
    }
     */

    /** Ayuda para verificar password (usada en el login del sistema) */
    public boolean matchesPassword(String candidate) {
        return candidate != null && candidate.equals(password);
    }

}