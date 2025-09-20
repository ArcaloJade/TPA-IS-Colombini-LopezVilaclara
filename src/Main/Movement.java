package Main;

import java.util.Objects;

public class Movement {
    private final long amount;

    public Movement(long amount) {
        this.amount = amount;
    }

    public long getAmount() {
        return amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Movement)) return false;
        Movement movement = (Movement) o;
        return amount == movement.amount;
    }

    @Override
    public String toString() {
        return "Movement{amount=" + amount + '}';
    }
}

