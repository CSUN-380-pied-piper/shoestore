package backend;

public class Customer {
    private String lastName, firstName, phoneNum, email, street, unit, city, state, zip, cardholder;
    private int creditCardNum, expDate, cvcCode;

    public Customer(String fn, String ln, String pn, String e, String st, String u, String c, String s, String z) {
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
