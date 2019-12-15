package person;

import java.util.Iterator;
import java.util.LinkedList;

public class PersonListManager {
    private LinkedList<Person> accountOwnerList;

    public PersonListManager(LinkedList<Person> accountOwnerList) {
        this.accountOwnerList = accountOwnerList;
    }

    //find an account in the linked list based off a pin
    public Person findPerson(String name) {
        Person tempPerson;

        for (Iterator<Person> i = accountOwnerList.iterator(); i.hasNext(); ) {
            tempPerson = i.next();

            if (name.equals(tempPerson.name)) {
                return tempPerson;
            }
        }

        System.out.println("Person Not Found");
        return null;
    }
}
