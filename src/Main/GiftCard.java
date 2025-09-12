package Main;

import org.junit.jupiter.api.function.Executable;

import java.util.ArrayList;

public class GiftCard {
    public String NotClaimed;
    public String AlreadyClaimed;
    public String InvalidMerchantKey;
    public String CannotChargeNegativeAmount;
    public String CannotChargeMoreThanWhatItHas;

    public GiftCard(String s, int i, String validMerchantKey) {

    }

    public void claim(Token validToken) {
    }

    public GiftCard charge(int i, String validMerchantKey) {

        return this;
    }

    public ArrayList<Object> getLog(Token validToken) {
        return null;
    }


    public short getBalance() {
    }
}
