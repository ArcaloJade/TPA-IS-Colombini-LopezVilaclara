package Test;

import Main.User;
import Main.Token;
import Main.GiftCard;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
public class UserTest {

    @Test public void test01CannotCreateUserWithEmptyUsername() {
        assertThrows(IllegalArgumentException.class, () -> new User("", "pass"));
    }

    @Test public void test02CannotCreateUserWithEmptyPassword() {
        assertThrows(IllegalArgumentException.class, () -> new User("nico", ""));
    }

    @Test public void test03UserMatchesCorrectPassword() {
        assertTrue(new User("nico", "1234").matchesPassword("1234"));
    }

    @Test public void test04UserDoesNotMatchIncorrectPassword() {
        assertFalse(new User("nico", "1234").matchesPassword("abcd"));
    }

    @Test public void test05AssignTokenStoresItCorrectly() {
        User user = new User("nico", "1234");
        Token token = new Token(LocalDateTime.now());
        user.assignToken(token);
        assertEquals(token, user.getToken());
    }

    @Test public void test06ClaimGiftCardAddsItToMap() {
        User user = new User("nico", "1234");
        GiftCard gc = new GiftCard("gc-1", 100, "mkey-1");
        user.claimGiftCard(gc);
        assertTrue(user.getClaimedGiftCards().containsKey("gc-1"));
        assertEquals(gc, user.getClaimedGiftCards().get("gc-1"));
    }

    @Test public void test07ClaimedGiftCardsMapIsUnmodifiable() {
        User user = new User("nico", "1234");
        user.claimGiftCard(new GiftCard("gc-1", 100, "mkey-2"));
        assertThrows(UnsupportedOperationException.class, () ->
                user.getClaimedGiftCards().put("gc-2", new GiftCard("gc-2", 50, "mkey-3"))
        );
    }
}
