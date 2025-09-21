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

    @Test
    void test01SeedsAreLoaded() {
        // podemos login & consultar por ids post construir
        assertDoesNotThrow(() -> facade.login("roma", "pa$$"));
        assertEquals(1000, facade.getGiftCardBalance("CARD-1"));
    }

    @Test
    void test02LoginSuccessAssignsToken() {
        Token t = facade.login("roma", "pa$$");
        assertNotNull(t);
    }

    @Test
    void test03LoginFailsUnknownUser() {
        assertThrows(SecurityException.class, () -> facade.login("messi", "franciasegundo"));
    }

    @Test
    void test04LoginFailsWrongPassword() {
        assertThrows(SecurityException.class, () -> facade.login("roma", "wrong"));
    }

    void test05ClaimGiftCardHappyPath() {
        facade.login("roma", "pa$$");
        assertDoesNotThrow(() -> facade.claimGiftCard("roma", "CARD-1"));
        assertEquals(cardA.claimedBy(), "roma");
    }

    @Test
    void test06ClaimGiftCardFailsUnknownUser() {
        assertThrows(IllegalArgumentException.class, () -> facade.claimGiftCard("messi", "CARD-1"));
    }

    @Test
    void test07ClaimGiftCardFailsWithoutLogin() {
        assertThrows(SecurityException.class, () -> facade.claimGiftCard("roma", "CARD-1"));
    }

    @Test
    void test08ClaimGiftCardFailsUnknownCard() {
        facade.login("roma", "pa$$");
        assertThrows(IllegalArgumentException.class, () -> facade.claimGiftCard("roma", "NADA"));
    }

    @Test
    void test09CannotClaimAlreadyClaimedByAnotherUser() { // ***
        User bob = new User("bob", "1234");

        facade = new Facade(
                List.of(roma, bob),
                List.of(cardA),
                List.of(shop)
        );

        facade.login("bob", "1234");
        facade.claimGiftCard("bob", "CARD-1");

        facade.login("roma", "pa$$");
        assertThrows(IllegalStateException.class, () -> facade.claimGiftCard("roma", "CARD-1"));
    } // ***

    @Test
    void test10ChargeFailsWithUnknownMerchant() {
        assertThrows(SecurityException.class, () -> facade.chargeGiftCard("CARD-1", "NOMERCHANT", 100));
    }

    @Test
    void test11ChargeFailsWhenCardNotClaimed() {
        // TODO !!!!!!
    }

    @Test
    void test12ChargeHappyPathAfterClaim() {
        facade.login("roma", "pa$$");
        facade.claimGiftCard("roma", "CARD-1");
        assertDoesNotThrow(() -> facade.chargeGiftCard("CARD-1", "SHOP-1", 100));
        assertEquals(900, facade.getGiftCardBalance("CARD-1"));
    }

    @Test
    void test13ChargeFailsWithInvalidAmount() {
        facade.login("roma", "pa$$");
        facade.claimGiftCard("roma", "CARD-1");

        assertThrows(IllegalArgumentException.class, () -> facade.chargeGiftCard("CARD-1", "SHOP-1", 0));
        assertThrows(IllegalArgumentException.class, () -> facade.chargeGiftCard("CARD-1", "SHOP-1", -5));
    }

    @Test
    void test14ChargeFailsUnknownCard() {
        assertThrows(NullPointerException.class, () -> facade.chargeGiftCard("NOPE", "SHOP-1", 50));
    }

    @Test
    void test15GetBalanceHappyPath() {
        assertEquals(1000, facade.getGiftCardBalance("CARD-1"));
    }

    @Test
    void test16GetBalanceFailsUnknownCard() {
        assertThrows(IllegalArgumentException.class, () -> facade.getGiftCardBalance("NOPE"));
    }

    @Test
    void test17GetLogHappyPathReturnsMap() {
        facade.login("roma", "pa$$");
        facade.claimGiftCard("roma", "CARD-1");
        Map<Integer, Movement> log = facade.getGiftCardLog("CARD-1");
        assertNotNull(log);
        assertEquals(0, log.size());
    }

    @Test
    void test18GetLogFailsUnknownCard() {
        assertThrows(IllegalArgumentException.class, () -> facade.getGiftCardLog("NOPE"));
    }

}
