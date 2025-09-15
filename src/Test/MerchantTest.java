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

    @Test public void test01CreatesActiveMerchantWithValidData() {
        Merchant m = new Merchant("K-123", "  Store X  ");
        assertEquals("K-123", m.getKey());
        assertEquals("Store X", m.getName()); // trimmed
        assertTrue(m.isActive());

    }

    @Test public void test02FailsWhenKeyIsBlank() {
        // crear merchant con key vacío o null y verificar excepción InvalidMerchantKey
        assertThrowsLike(() -> new Merchant("", "Store"), Merchant.InvalidMerchantKey);
        assertThrowsLike(() -> new Merchant(null, "Store"), Merchant.InvalidMerchantKey);
    }

    @Test public void test03FailsWhenNameIsBlank() {
        // crear merchant con nombre vacío o null y verificar excepción EmptyName
        assertThrowsLike(() -> new Merchant("K-123", ""), Merchant.EmptyName);
        assertThrowsLike(() -> new Merchant("K-123", null), Merchant.EmptyName);
    }

    @Test public void test04MatchesKeyReturnsTrueForSameKey() {
        // verificar que matchesKey devuelve true con el mismo key y false con otro
        assertTrue(merchant.matchesKey(VALID_KEY));
        assertFalse(merchant.matchesKey("OTHER"));
    }

    @Test public void test05AssertValidKeyThrowsOnInvalidKey() {
        // verificar que assertValidKey lanza SecurityException con InvalidMerchantKey
        assertDoesNotThrow(() -> merchant.assertValidKey(VALID_KEY));
        assertThrowsLike(() -> merchant.assertValidKey("OTHER"), Merchant.InvalidMerchantKey);
    }

    @Test public void test06DeactivatePreventsOperations() {
        // si desactivo merchant y luego assertActive debe lanzar InactiveMerchant
        merchant.deactivate();
        assertThrowsLike(() -> merchant.assertActive(), Merchant.InactiveMerchant);
    }

    @Test public void test07ActivateRestoresOperations() {
        // desactivar, reactivar y luego assertActive no debe lanzar exception
        merchant.deactivate();
        merchant.activate();
        assertDoesNotThrow(() -> merchant.assertActive());
    }

    @Test public void test08RenameChangesNameAndValidates() {
        // renombrar merchant y verificar cambio; renombrar vacío debe lanzar EmptyName
        merchant.rename("New Name");
        assertEquals("New Name", merchant.getName());
        assertThrowsLike(() -> merchant.rename(""), Merchant.EmptyName);
    }

    @Test public void test09EqualityIsBasedOnKey() {
        // merchants con mismo key son iguales, con distinto key no
        Merchant m1 = new Merchant("K-123", "Store A");
        Merchant m2 = new Merchant("K-123", "Store B");
        Merchant m3 = new Merchant("K-456", "Store C");

        assertEquals(m1, m2);
        assertNotEquals(m1, m3);
    }

    @Test public void test10HashCodeConsistentWithEquals() {
        // merchants con mismo key deben tener el mismo hashCode
        Merchant m1 = new Merchant("K-123", "Store A");
        Merchant m2 = new Merchant("K-123", "Store B");

        assertEquals(m1.hashCode(), m2.hashCode());
    }

    @Test public void test11ToStringContainsKeyNameAndActive() {
        // verificar que toString contiene key, name y estado activo/inactivo (info)
        String repr = merchant.toString();
        assertTrue(repr.contains(merchant.getKey()));
        assertTrue(repr.contains(merchant.getName()));
        assertTrue(repr.contains(String.valueOf(merchant.isActive())));

    }

    @Test public void test12CannotChargeUnclaimedCardThroughMerchant() {
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

