package Test;

import Main.Token;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDateTime;

public class TokenTest {

    @Test public void test01TokenIsExpiredAfterLimit() {
        assertTrue( new Token("ArcaloJade", LocalDateTime.now().minusMinutes(10)).isExpired() );
    }

    @Test public void test02TokenIsNotExpiredBeforeLimit() {
        assertFalse( new Token("ArcaloJade", LocalDateTime.now() ).isExpired() );
    }

    @Test public void test03TokenBelongsToCreatorUser() {
        assertTrue( new Token( "ArcaloJade", LocalDateTime.now() ).isFromUser( "ArcaloJade" ) );
    }

    @Test public void test04TokenDoesNotBelongToOtherUser() {
        assertFalse( new Token( "ArcaloJade", LocalDateTime.now() ).isFromUser( "MojoJojo" ) );
    }

    @Test public void test05TokenExpiresExactlyAfterLimit() {
        assertTrue(new Token("ArcaloJade", LocalDateTime.now().minusMinutes(5)).isExpired());
    }

    @Test public void test06TokenCannotBelongToNullUser() {
        assertFalse(new Token("ArcaloJade", LocalDateTime.now()).isFromUser(null));
    }

}

