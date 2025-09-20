package Test;

import Main.Token;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDateTime;

public class TokenTest {

    @Test public void test01TokenIsExpiredAfterLimit() {
        assertTrue( new Token( LocalDateTime.now().minusMinutes(10)).isExpired() );
    }

    @Test public void test02TokenIsNotExpiredBeforeLimit() {
        assertFalse( new Token( LocalDateTime.now() ).isExpired() );
    }

    @Test public void test03TokenExpiresExactlyAfterLimit() {
        assertTrue(new Token( LocalDateTime.now().minusMinutes(5)).isExpired());
    }

}

