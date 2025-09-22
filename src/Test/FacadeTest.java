package Test;

import Main.Facade;
import Main.User;
import Main.GiftCard;
import Main.Merchant;
import Main.Token;
import Main.Movement;
import org.junit.jupiter.api.*;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class FacadeTest {
    private String VALID_MERCHANT_KEY = "SHOP-1";
    private User roma;
    private GiftCard cardA;
    private Merchant shop;
    private Facade facade;

    @BeforeEach
    void setUp() {
        roma = new User("roma", "pa$$");
        cardA = new GiftCard("CARD-1", 1000, VALID_MERCHANT_KEY);
        shop  = new Merchant(VALID_MERCHANT_KEY, "Store X");

        facade = new Facade(
                List.of(roma),
                List.of(cardA),
                List.of(shop)
        );
    }

    @Test public void test01SeedsAreLoaded() {
        assertDoesNotThrow(() -> facade.login("roma", "pa$$"));
        assertEquals(1000, facade.getGiftCardBalance("CARD-1"));
    }

    @Test public void test02LoginSuccessAssignsToken() {
        Token t = facade.login("roma", "pa$$");
        assertNotNull(t);
    }

    @Test public void test03LoginFailsUnknownUser() {
        assertThrows(SecurityException.class, () -> facade.login("messi", "franciasegundo"));
    }

    @Test public void test04LoginFailsWrongPassword() {
        assertThrows(SecurityException.class, () -> facade.login("roma", "wrong"));
    }

    @Test public void test05ClaimGiftCardHappyPath() {
        assertDoesNotThrow(() -> loginAndClaim("roma", "pa$$", "CARD-1"));
        assertEquals(cardA.claimedBy(), "roma");
    }

    @Test public void test06ClaimGiftCardFailsUnknownUser() {
        assertThrows(IllegalArgumentException.class, () -> facade.claimGiftCard("messi", "CARD-1"));
    }

    @Test public void test07ClaimGiftCardFailsWithoutLogin() {
        assertThrows(SecurityException.class, () -> facade.claimGiftCard("roma", "CARD-1"));
    }

    @Test public void test08ClaimGiftCardFailsUnknownCard() {
        facade.login("roma", "pa$$");
        assertThrows(IllegalArgumentException.class, () -> facade.claimGiftCard("roma", "NADA"));
    }

    @Test public void test09CannotClaimAlreadyClaimedByAnotherUser() {
        User bob = new User("bob", "1234");
        facade = new Facade(
                List.of(roma, bob),
                List.of(cardA),
                List.of(shop)
        );
        loginAndClaim("bob", "1234", "CARD-1");
        facade.login("roma", "pa$$");
        assertThrows(IllegalStateException.class, () -> facade.claimGiftCard("roma", "CARD-1"));
    }

    @Test public void test10ChargeFailsWithUnknownMerchant() {
        assertThrows(SecurityException.class, () -> facade.chargeGiftCard("CARD-1", "NOMERCHANT", 100));
    }

    @Test public void test11ChargeFailsWhenCardNotClaimed() {
        facade.login("roma", "pa$$");
        assertThrows(IllegalStateException.class,
                () -> facade.chargeGiftCard("CARD-1", "SHOP-1", 100));
    }

    @Test public void test12ChargeHappyPathAfterClaim() {
        loginAndClaim("roma", "pa$$", "CARD-1");
        assertDoesNotThrow(() -> facade.chargeGiftCard("CARD-1", "SHOP-1", 100));
        assertEquals(900, facade.getGiftCardBalance("CARD-1"));
    }

    @Test public void test13ChargeFailsWithInvalidAmount() {
        loginAndClaim("roma", "pa$$", "CARD-1");
        assertThrows(IllegalArgumentException.class, () -> facade.chargeGiftCard("CARD-1", "SHOP-1", 0));
        assertThrows(IllegalArgumentException.class, () -> facade.chargeGiftCard("CARD-1", "SHOP-1", -5));
    }

    @Test public void test14ChargeFailsUnknownCard() {
        assertThrows(NullPointerException.class, () -> facade.chargeGiftCard("NOPE", "SHOP-1", 50));
    }

    @Test public void test15GetBalanceHappyPath() {
        assertEquals(1000, facade.getGiftCardBalance("CARD-1"));
    }

    @Test public void test16GetBalanceFailsUnknownCard() {
        assertThrows(IllegalArgumentException.class, () -> facade.getGiftCardBalance("NOPE"));
    }

    @Test public void test17GetLogHappyPathReturnsMap() {
        loginAndClaim("roma", "pa$$", "CARD-1");
        Map<Integer, Movement> log = facade.getGiftCardLog("CARD-1");
        assertNotNull(log);
        assertEquals(0, log.size());
    }

    @Test public void test18GetLogFailsUnknownCard() {
        assertThrows(IllegalArgumentException.class, () -> facade.getGiftCardLog("NOPE"));
    }

    private void loginAndClaim(String username, String password, String cardId) {
        facade.login(username, password);
        facade.claimGiftCard(username, cardId);
    }


}
