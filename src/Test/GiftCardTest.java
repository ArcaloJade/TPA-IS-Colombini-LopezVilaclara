package Test;

import Main.GiftCard;
import Main.Movement;
import Main.Token;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class GiftCardTest {

    private static final String VALID_USER = "user-123"; // *** de momento es string
    private static final String VALID_MERCHANT_KEY = "MERCHANT-1"; // *** de momento es string
    private static final String INVALID_MERCHANT_KEY = "HACKER";

    private Token VALID_TOKEN;
    private GiftCard unclaimedCard;
    private GiftCard claimedCard;
    private int claimedCardBalance = 1000; // *** deberia ser int plata?

    @BeforeEach
    void setUp() {
        VALID_TOKEN = new Token(
                VALID_USER,
                LocalDateTime.now()
        );

        unclaimedCard = new GiftCard("CARD-1", claimedCardBalance, VALID_MERCHANT_KEY);
        claimedCard = new GiftCard("CARD-2", 2000, VALID_MERCHANT_KEY);
        claimedCard.claim(VALID_TOKEN);
    }

    @Test public void test01CanNotChargeIfNotClaimed () {
        assertThrowsLike( () -> unclaimedCard.charge( 10, VALID_MERCHANT_KEY), unclaimedCard.NotClaimed);
    }

    @Test public void test02CanNotsSeeLogIfNotClaimed () {
        assertThrowsLike( () -> unclaimedCard.getLog(VALID_TOKEN), unclaimedCard.NotClaimed);
    }

    @Test public void test03CanNotBeClaimedIfAlreadyClaimed () {
        assertThrowsLike( () -> claimedCard.claim(VALID_TOKEN), claimedCard.AlreadyClaimed); // *** que sea un boolean
    }

    @Test public void test04CannotBeUsedIfInvalidMerchantKey () {
        assertThrowsLike( () -> claimedCard.charge( 10, INVALID_MERCHANT_KEY), claimedCard.InvalidMerchantKey);
    }

    @Test public void test05CannotChargeNegativeAmount () {
        assertThrowsLike( () -> claimedCard.charge( -10, VALID_MERCHANT_KEY), claimedCard.CannotChargeNegativeAmount);
    }

    @Test public void test06CannotChargeMoreThanWhatItHas() {
        int realBalance = claimedCard.getBalance();  // usa el saldo actual del objeto
        assertThrowsLike(
                () -> claimedCard.charge(realBalance + 10, VALID_MERCHANT_KEY),
                claimedCard.CannotChargeMoreThanWhatItHas
        );
    }


    @Test public void test07ChargeSuccessfulyLogsMovement () {
        assertEquals(new Movement(10), claimedCard.charge(10, VALID_MERCHANT_KEY)
                .getLog(VALID_TOKEN)
                .get(claimedCard.getLog(VALID_TOKEN).size() - 1));
    }

    @Test public void test08ChargeSuccessfullyUpdatesBalance() {
        int before = claimedCard.getBalance();
        int after = claimedCard.charge(10, VALID_MERCHANT_KEY).getBalance();
        assertEquals(before - 10, after);
    }


    private void assertThrowsLike(Executable executable, String message ) {
        assertEquals( message,
                assertThrows( Exception.class, executable )
                        .getMessage() );
    }

}

