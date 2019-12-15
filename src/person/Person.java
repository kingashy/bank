package person;

public class Person {
    protected String name;
    protected int ssn;

    public Person(String name, int ssn) {
        this.name = name;
        this.ssn = ssn;
    }

    public String getName() {
        return name;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(name).append(",").append(ssn);
        return sb.toString();
    }

    public void showInfo() {
        System.out.println("Name: " + name + "\nSSN: " + ssn);
    }
}
