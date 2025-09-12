package Main;

import java.awt.*;
import java.util.ArrayList;

public class User {
    private String name;
    private String password;
//    private List<GiftCard> giftCards;
    private String merchantKey;
    private Token token;

    public User(String name, String password) {
        this.name = name;
        this.password = password;
//        this.giftCards = new ArrayList<>();
        this.merchantKey = null;
        this.token = null;
    }
}
