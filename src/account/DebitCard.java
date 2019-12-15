package account;

import random.Generate;

public class DebitCard extends Card {
    public DebitCard(long cardNumber) {
        this.cardNumber = cardNumber;
    }

    public DebitCard() {
        this.cardNumber = generateDebitCardNum();
    }

    //generate a random 12-digit account number
    private long generateDebitCardNum() {
        return new Generate().random(12);
    }
}
