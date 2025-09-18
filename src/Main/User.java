package Main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class User {
    private final String username;
    private final String password;
    private Token token;
    private final List<GiftCard> claimedGiftCards = new ArrayList<>();

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
    public String getPassword() { return password; }

    public Token getToken() { return token; }

    /** Usado por el sistema al autenticar */
    public void assignToken(Token token) {
        this.token = token;
    }

    /** El usuario reclama una GiftCard (ejecutado via el sistema, pero guardado acá) */
    public void claimGiftCard(GiftCard giftCard) {
        if (giftCard == null) throw new IllegalArgumentException("GiftCard cannot be null");
        claimedGiftCards.add(giftCard);
    }

    public List<GiftCard> getClaimedGiftCards() {
        return Collections.unmodifiableList(claimedGiftCards);
    }

    /** Ayuda para verificar password (usada en el login del sistema) */
    public boolean matchesPassword(String candidate) {
        return candidate != null && candidate.equals(password);
    }
}


// Metodo que haga login (se comunica con el sistema y este le da el token y el merchant calculo yo).
// En cada accion que haga tmb le tiene que pedir al sistema que actualice su token.
// - accion Reclamar: Decirle al sistema che! esta tarjetita reclamamela! le pasa el token (cuando se le asignan cards? al construirlo o que onda?)
// - accion charge: once again pasa un token y una tarjeta y el mkey y le dice al sistema eyyy, a toodo esto, la tarjeta deberia ser lit tarjeta o id de una tarjeta?
//   o sea, debería tener lit una lista de objetos tarjeta o como ids de tarjetas y ahí el sistema dice listoop, me paso este id ahora tomo la tarjeta tal (objeto)
// - accion see log: pasa token, tarjeta y mkey al sistema...

