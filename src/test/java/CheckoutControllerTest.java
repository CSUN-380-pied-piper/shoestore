import backend.Customer;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CheckoutControllerTest {

    @Test
    void placeOrder() {
        Customer c = new Customer("John", "Doe", "1234567789",  "abc@gmail.com",
                "123 Main St", "Ste C", "Alhambra", "CA", 91755);
        assertTrue(c.getFirstName().equals("John"));
        assertFalse(c.getFirstName().equals("Joh"));
        assertTrue(c.getLastName().equals("Doe"));
        assertFalse(c.getLastName().equals("Do"));
        assertTrue(c.getPhoneNum().equals("1234567789"));
        assertFalse(c.getPhoneNum().equals("987654321"));
        assertTrue(c.getEmail().equals("abc@gmail.com"));
        assertFalse(c.getEmail().equals("efg@gmail.com"));
        assertTrue(c.getStreet().equals("123 Main St"));
        assertFalse(c.getStreet().equals("432 1th St"));
        assertTrue(c.getUnit().equals("Ste C"));
        assertFalse(c.getUnit().equals(""));
        assertTrue(c.getCity().equals("Alhambra"));
        assertFalse(c.getCity().equals("San Diego"));
        assertTrue(c.getState().equals("CA"));
        assertFalse(c.getState().equals("NY"));
        assertTrue(c.getZip() == 91755);
        assertFalse(c.getZip() == 92004);
    }
}