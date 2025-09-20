package Test;

import Main.GiftCard;
import Main.Merchant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.*;

public class MerchantTest {
    private static final String VALID_KEY = "MERCHANT-1";
    private static final String VALID_NAME = "Roma";
    private static final String INVALID_KEY = "";

    private Merchant merchant;

    @BeforeEach
    void setUp() {
        merchant = new Merchant(VALID_KEY, VALID_NAME);
    }

    // 1) Camino feliz de construcción
    @Test public void test01CreatesActiveMerchantWithValidData() {
        Merchant m = new Merchant("K-123", "  Store X  ");
        assertEquals("K-123", m.getKey());
        assertEquals("Store X", m.getName()); // trimmed
        assertTrue(m.isActive());
    }

    // 2) Validación de entrada: key inválido
    @Test public void test02FailsWhenKeyIsBlank() {
        assertThrowsLike(() -> new Merchant("", "Store"), Merchant.InvalidMerchantKey);
        assertThrowsLike(() -> new Merchant(null, "Store"), Merchant.InvalidMerchantKey);
    }

    // 3) Validación de entrada: nombre inválido
    @Test public void test03FailsWhenNameIsBlank() {
        assertThrowsLike(() -> new Merchant("K-123", ""), Merchant.EmptyName);
        assertThrowsLike(() -> new Merchant("K-123", null), Merchant.EmptyName);
    }

    // 4) Método auxiliar simple: matchesKey
    @Test public void test04MatchesKeyReturnsTrueForSameKey() {
        assertTrue(merchant.matchesKey(VALID_KEY));
        assertFalse(merchant.matchesKey("OTHER"));
    }

    // 5) Validación de seguridad explícita
    @Test public void test05AssertValidKeyThrowsOnInvalidKey() {
        assertDoesNotThrow(() -> merchant.assertValidKey(VALID_KEY));
        assertThrowsLike(() -> merchant.assertValidKey("OTHER"), Merchant.InvalidMerchantKey);
    }

    // 6) Cambio de estado: desactivar
    @Test public void test06DeactivatePreventsOperations() {
        merchant.deactivate();
        assertThrowsLike(() -> merchant.assertActive(), Merchant.InactiveMerchant);
    }

    // 7) Cambio de estado: reactivar
    @Test public void test07ActivateRestoresOperations() {
        merchant.deactivate();
        merchant.activate();
        assertDoesNotThrow(() -> merchant.assertActive());
    }

    // 8) Igualdad basada en key
    @Test public void test08EqualityIsBasedOnKey() {
        Merchant m1 = new Merchant("K-123", "Store A");
        Merchant m2 = new Merchant("K-123", "Store B");
        Merchant m3 = new Merchant("K-456", "Store C");

        assertEquals(m1, m2);
        assertNotEquals(m1, m3);
    }

    // 9) Consistencia de hashCode
    @Test public void test09HashCodeConsistentWithEquals() {
        Merchant m1 = new Merchant("K-123", "Store A");
        Merchant m2 = new Merchant("K-123", "Store B");

        assertEquals(m1.hashCode(), m2.hashCode());
    }

    // 10) Representación en String
    @Test public void test10ToStringContainsKeyNameAndActive() {
        String repr = merchant.toString();
        assertTrue(repr.contains(merchant.getKey()));
        assertTrue(repr.contains(merchant.getName()));
        assertTrue(repr.contains(String.valueOf(merchant.isActive())));
    }

    // 11) Integración con GiftCard
    @Test public void test11CannotChargeUnclaimedCardThroughMerchant() {
        GiftCard unclaimed = new GiftCard("CARD-X", 1000, merchant.getKey());
        assertThrowsLike(() -> merchant.charge(unclaimed, 10), unclaimed.NotClaimed);
    }

    private void assertThrowsLike(Executable executable, String message) {
        assertEquals(
                message,
                assertThrows(Exception.class, executable).getMessage()
        );
    }
}


