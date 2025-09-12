package Main;

import java.time.LocalDateTime;

public class Token {

    private String user;
    private LocalDateTime creationTime;
    private LocalDateTime expirationTime;

    public Token(String user, LocalDateTime creationTime) {
        this.user = user;
        this.creationTime = creationTime;
        this.expirationTime = creationTime.plusMinutes(5); // 5 minutos de duración
    }

    public boolean isExpired() {
        return expirationTime.isBefore(LocalDateTime.now());
    }

    public boolean isFromUser(String userQuery) {
        return userQuery != null && userQuery.equals(this.user);
    }
}
