package Main;

import javax.smartcardio.Card;
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class User {
    private final String username;
    private final String password;
    private final List<Card> giftCards = new ArrayList<>();
//    private final Map<String, Card> giftCards = new LinkedHashMap<>();
    // Ponerle una instancia de sistema tmb
    // id?

    private String merchantKey;
    private Token token;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
//        this.giftCards = new ArrayList<>();
        this.merchantKey = null;
        this.token = null;
    }

    // Metodo que haga login (se comunica con el sistema y este le da el token y el merchant calculo yo).
    // En cada accion que haga tmb le tiene que pedir al sistema que actualice su token.
    // - accion Reclamar: Decirle al sistema che! esta tarjetita reclamamela! le pasa el token (cuando se le asignan cards? al construirlo o que onda?)
    // - accion charge: once again pasa un token y una tarjeta y el mkey y le dice al sistema eyyy, a toodo esto, la tarjeta deberia ser lit tarjeta o id de una tarjeta?
    //   o sea, debería tener lit una lista de objetos tarjeta o como ids de tarjetas y ahí el sistema dice listoop, me paso este id ahora tomo la tarjeta tal (objeto)
    // - accion see log: pasa token, tarjeta y mkey al sistema...

}
