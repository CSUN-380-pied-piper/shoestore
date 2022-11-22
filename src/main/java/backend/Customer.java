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
         return "Order Confirmation\n\n" +
        		"Hi "
                + firstName + " " + lastName + "!\n"
                + "Here are the details of your order: \n\n"
                + "Shipping Address: \n"
                + street + " " + unit + "\n"
                + city + " " + state + " " + zip + "\n\n"
                +"Email: "
                + email + "\n" + 
                "Phone Number: " + phoneNum;
    }
}
