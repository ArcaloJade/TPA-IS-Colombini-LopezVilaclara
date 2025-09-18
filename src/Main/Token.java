package Main;

import java.time.LocalDateTime;

public class Token {

    private LocalDateTime creationTime;
    private LocalDateTime expirationTime;

    public Token(LocalDateTime creationTime) {
        this.creationTime = creationTime;
        this.expirationTime = creationTime.plusMinutes(5); // 5 minutos de duración
    }

    public boolean isExpired() {
        return expirationTime.isBefore(LocalDateTime.now());
    }

}
