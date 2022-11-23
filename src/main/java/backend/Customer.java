package backend;

public class Customer {
    private String lastName, firstName, email, phoneNum, street, unit, city, state, cardholder;
    private int zip, creditCardNum, expDate, cvcCode;

    public Customer(String fn, String ln, String pn, String e, String st, String u, String c, String s, int z) {
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

    public String orderConfirmationCustomerInfo() {
        String oc;
        oc = "This email was sent to " + email + "\n\n"
                + "Order Confirmation\n\n" +
        		"Hi " + firstName + " " + lastName + "!\n"
                + "Here are the details of your order: \n\n"
                + "Shipping Address: \n"
                + street + " " + unit + "\n"
                + city + " " + state + " " + zip + "\n\n"
                + "Phone Number: " + phoneNum;

        return oc;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Name: ");
        builder.append(firstName);
        builder.append(", ");
        builder.append(lastName);
        builder.append(", email: ");
        builder.append(email);
        builder.append(", phone: ");
        builder.append(phoneNum);
        return builder.toString();
    }
}
