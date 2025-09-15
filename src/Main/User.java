package Main;

import java.awt.*;
import java.util.ArrayList;

public class User { // hmmmm
    private final String username;
    private final String password;
    private final Map<String, Card> claimedCardsById = new LinkedHashMap<>();

    private String merchantKey;
    private Token token;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
//        this.giftCards = new ArrayList<>();
        this.merchantKey = null;
        this.token = null;
    }

}
