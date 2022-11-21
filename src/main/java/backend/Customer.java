package backend;

public class Customer {
    private String lastName, firstName, email, street, unit, city, state, cardholder;
    private int phoneNum, zip, creditCardNum, expDate, cvcCode;

    public Customer(String fn, String ln, int pn, String e, String st, String u, String c, String s, int z) {
        firstName = fn;
        lastName = ln;
        phoneNum = pn;
        email = e;
        street = st;
        unit = u;
        city = c;
        state = s;
        zip = z;
    }

    @Override
    public String toString() {
        return "\nCustomer Information\n"
                + firstName + " " + lastName + "\n"
                + street + " " + unit + "\n"
                + city + " " + state + " " + zip + "\n"
                + email + "\n" + phoneNum;
    }
}
