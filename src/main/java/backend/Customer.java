package backend;

public class Customer {
    private String lastName;
    private String firstName;
    private String email;
    private String phoneNum;
    private String street;
    private String unit;
    private String city;
    private String state;
    private int zip;

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

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public String getStreet() {
        return street;
    }

    public String getUnit() {
        return unit;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public int getZip() {
        return zip;
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
