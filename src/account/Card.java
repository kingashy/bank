package account;

public class Card
{
    protected long cardNumber;

    public long getCardNumber()
    {
        return cardNumber;
    }

    public void showInfo()
    {
        System.out.println("Card: " + cardNumber);
    }
}
