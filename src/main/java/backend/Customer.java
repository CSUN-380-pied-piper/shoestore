package backend;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Customer implements Queryable {

    private SimpleStringProperty lastName;
    private SimpleStringProperty firstName;
    private SimpleStringProperty email;
    private SimpleStringProperty phoneNum;
    private SimpleStringProperty street;
    private SimpleStringProperty unit;
    private SimpleStringProperty city;
    private SimpleStringProperty state;
    private SimpleIntegerProperty zip;

    public Customer(String fn, String ln, String pn, String e, String st, String u, String c, String s, int z) {
        this.lastName = new SimpleStringProperty(ln);
        this.firstName = new SimpleStringProperty(fn);
        this.phoneNum = new SimpleStringProperty(pn);
        this.email = new SimpleStringProperty(e);
        this.street = new SimpleStringProperty(st);
        this.unit = new SimpleStringProperty(u);
        this.city = new SimpleStringProperty(c);
        this.state = new SimpleStringProperty(s);
        this.zip = new SimpleIntegerProperty(z);
    }

   public String orderConfirmationCustomerInfo() {
         return "Order Confirmation\n\n" +
        		"Hi "
                + firstName.get() + " " + lastName.get() + "!\n"
                + "Here are the details of your order: \n\n"
                + "Shipping Address: \n"
                + street.get() + " " + unit.get() + "\n"
                + city.get() + " " + state.get() + " " + zip.get() + "\n\n"
                +"Email: "
                + email.get() + "\n" +
                "Phone Number: " + phoneNum.get();
    }

    public SimpleStringProperty lastNameProperty() {
        return lastName;
    }

    public SimpleStringProperty firstNameProperty() {
        return firstName;
    }

    public SimpleStringProperty emailProperty() {
        return email;
    }

    public SimpleStringProperty phoneNumProperty() {
        return phoneNum;
    }

    public SimpleStringProperty streetProperty() {
        return street;
    }

    public SimpleStringProperty unitProperty() {
        return unit;
    }

    public SimpleStringProperty cityProperty() {
        return city;
    }

    public SimpleStringProperty stateProperty() {
        return state;
    }

    public SimpleIntegerProperty zipProperty() {
        return zip;
    }
    public String getLastName() {
        return lastName.get();
    }

    public String getFirstName() {
        return firstName.get();
    }

    public String getEmail() {
        return email.get();
    }

    public String getPhoneNum() {
        return phoneNum.get();
    }

    public String getStreet() {
        return street.get();
    }

    public String getUnit() {
        return unit.get();
    }

    public String getCity() {
        return city.get();
    }

    public String getState() {
        return state.get();
    }

    public int getZip() {
        return zip.get();
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Name: ");
        builder.append(firstName.get());
        builder.append(", ");
        builder.append(lastName.get());
        builder.append(", email: ");
        builder.append(email.get());
        builder.append(", phone: ");
        builder.append(phoneNum.get());
        return builder.toString();
    }
}
