package Main;

import java.time.LocalDateTime;

public class Token {

    private LocalDateTime expirationTime;

    public Token(LocalDateTime creationTime) {
        this.expirationTime = creationTime.plusMinutes(5); // 5 minutos de duración
    }

    public boolean isExpired() {
        return expirationTime.isBefore(LocalDateTime.now());
    }

}
