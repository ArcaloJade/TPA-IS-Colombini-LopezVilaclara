package Test;

import Main.Merchant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.*;

public class MerchantTest {

    private Merchant merchant;

    @BeforeEach
    void setUp() {
        merchant = new Merchant("MERCHANT-1", "Roma");
    }

    @Test public void test01CreatesActiveMerchantWithValidData() {
        Merchant m = new Merchant("K-123", "  Store X  ");
        assertEquals("K-123", m.getKey());
        assertEquals("Store X", m.getName()); // trimmed
        assertTrue(m.isActive());

    }

    @Test public void test02FailsWhenKeyIsBlank() {
        // TODO: crear merchant con key vacío o null y verificar excepción InvalidMerchantKey
    }

    @Test public void test03FailsWhenNameIsBlank() {
        // TODO: crear merchant con nombre vacío o null y verificar excepción EmptyName
    }

    @Test public void test04MatchesKeyReturnsTrueForSameKey() {
        // TODO: verificar que matchesKey devuelve true con el mismo key y false con otro
    }

    @Test public void test05AssertValidKeyThrowsOnInvalidKey() {
        // TODO: verificar que assertValidKey lanza SecurityException con InvalidMerchantKey
    }

    @Test public void test06DeactivatePreventsOperations() {
        // TODO: desactivar merchant y luego assertActive debe lanzar InactiveMerchant
    }

    @Test public void test07ActivateRestoresOperations() {
        // TODO: desactivar, reactivar y luego assertActive no debe lanzar
    }

    @Test public void test08RenameChangesNameAndValidates() {
        // TODO: renombrar merchant y verificar cambio; renombrar vacío debe lanzar EmptyName
    }

    @Test public void test09EqualityIsBasedOnKey() {
        // TODO: merchants con mismo key son iguales, con distinto key no
    }

    @Test public void test10HashCodeConsistentWithEquals() {
        // TODO: merchants con mismo key deben tener el mismo hashCode
    }

    @Test public void test11ToStringContainsKeyNameAndActive() {
        // TODO: verificar que toString contiene key, name y estado activo/inactivo
    }
}

