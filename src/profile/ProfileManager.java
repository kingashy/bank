package profile;

import account.Pin;
import person.Person;
import java.util.LinkedList;

public class ProfileManager
{
    public Profile createProfile(Person person, LinkedList<Long> accountNumberList, Pin pin)
    {
        return new Profile(person, accountNumberList, pin);
    }

    public Person createPerson(String name, int SSN)
    {
        return new Person(name, SSN);
    }

    public Profile createProfileFromFile(String[] splitLine)
    {
        Profile profile;
        Person person;
        Pin pin;
        LinkedList<Long> accountNumberList = new LinkedList<Long>();

        person = new Person(splitLine[0].trim(), Integer.parseInt(splitLine[1].trim()));

        for (int i = 2; i < splitLine.length - 2; i++)
        {
            accountNumberList.add(Long.parseLong(splitLine[i].trim()));
        }

        pin = new Pin(Integer.parseInt(splitLine[splitLine.length - 1].trim()));
        profile = new Profile(person, accountNumberList, pin);

        return profile;
    }
}