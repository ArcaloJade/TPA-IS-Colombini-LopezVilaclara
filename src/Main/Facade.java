package Main;

import java.time.LocalDateTime;
import java.util.*;

public class Facade {

    private final Map<String, User> users = new HashMap<>();
    private final Map<String, GiftCard> giftCards = new HashMap<>();
    private final Map<String, Merchant> merchants = new HashMap<>();

    public Facade(List<User> preUsers, List<GiftCard> preGiftCards, List<Merchant> preMerchants) {
        preUsers.forEach(u -> users.put(u.getUsername(), u));
        preGiftCards.forEach(g -> giftCards.put(g.getId(), g));
        preMerchants.forEach(m -> merchants.put(m.getKey(), m));
    }

    /** Login: valida username/password y genera token */
    public Token login(String username, String password) {
        User user = users.get(username);
        if (user == null || !user.matchesPassword(password)) {
            throw new SecurityException("Invalid username or password");
        }
        Token token = new Token(LocalDateTime.now());
        user.assignToken(token);
        return token;
    }

    /** Reclamar una gift card */
    public void claimGiftCard(String username, String giftCardId) {
        User user = users.get(username);
        if (user == null) throw new IllegalArgumentException("Unknown user");

        Token token = user.getToken();
        if (token == null || token.isExpired()) throw new SecurityException("Token expired or missing");

        GiftCard giftCard = giftCards.get(giftCardId);
        if (giftCard == null) throw new IllegalArgumentException("GiftCard not found");

        giftCard.claim(token, username);
        //user.claimGiftCard(giftCard);
    }

    /** Merchant hace cargo a tarjeta */
    public void chargeGiftCard(String giftCardId, String merchantKey, int amount) {
        GiftCard giftCard = giftCards.get(giftCardId);
        Merchant merchant = merchants.get(merchantKey);
        if (merchant == null) throw new SecurityException("Invalid merchant");

        merchant.charge(giftCard, amount);
    }

    /** Consultar saldo y log de tarjeta */
    public long getGiftCardBalance(String giftCardId) {
        GiftCard giftCard = giftCards.get(giftCardId);
        if (giftCard == null) throw new IllegalArgumentException("GiftCard not found");
        return giftCard.getBalance();
    }

    public Map<Integer, Movement> getGiftCardLog(String giftCardId) {
        GiftCard giftCard = giftCards.get(giftCardId);
        if (giftCard == null) {
            throw new IllegalArgumentException("GiftCard not found");
        }

        return giftCard.getLog(
                giftCard.getBalance() >= 0 ? new Token(LocalDateTime.now()) : null
        );
    }
}
