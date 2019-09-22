package account;

import random.Generate;

public class SecurityBox
{
    private int boxNum;

    public SecurityBox(int boxNum)
    {
        this.boxNum = boxNum;
    }

    public SecurityBox()
    {
        this.boxNum = generateBoxNum();
    }

    //generate a random security box number
    private int generateBoxNum()
    {
        return (int)(new Generate().random(3));
    }

    public int getBoxNumber()
    {
        return boxNum;
    }

    public void showInfo()
    {
        System.out.println("Box Number: " + boxNum);
    }
}
