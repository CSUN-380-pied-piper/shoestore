import backend.Product;
import backend.ShoppingCart;
import org.junit.jupiter.api.Test;

import java.text.NumberFormat;

import static org.junit.jupiter.api.Assertions.*;

class ShoppingCartTest {

    private NumberFormat df = NumberFormat.getCurrencyInstance();
    private ShoppingCart cart = new ShoppingCart(df);

    @Test
    void addItem() {
        Product heels = new Product("Heels",45.0, true, 5, 11);
        heels.lastSizeProp().set(7.5);
        heels.lastQtyProp().set(1);
        cart.addItem(heels);
        assertFalse(cart.getContents().contains(heels));
        assertTrue(cart.getTax() == 3.26);
        assertEquals(48.26, cart.getFinalTotal(), .01);
    }

    @Test
    void removeItem() {
        Product sneakers = new Product("Sneakers",120.0, true, 5, 13);
        sneakers.lastSizeProp().set(6.5);
        sneakers.lastQtyProp().set(1);
        cart.addItem(sneakers);
        cart.removeItem(sneakers);
        assertFalse(cart.getContents().contains(sneakers));
        assertTrue(cart.getTax() == 0.00);
        assertEquals(0.00, cart.getFinalTotal());
    }

    @Test
    void getSubTotal() {
        Product boots = new Product("Boots",135.0, true, 5, 13);
        boots.lastSizeProp().set(7.0);
        boots.lastQtyProp().set(1);
        cart.addItem(boots);
        assertEquals(135, cart.getSubTotal());
    }

    @Test
    void getTax() {
        cart.subTotalProperty().set(45.0);
        assertEquals(3.26, cart.getTax());
    }

    @Test
    void getFinalTotal() {
        cart.subTotalProperty().set(125.0);
        assertEquals(134.06, cart.getFinalTotal(), .01);
    }
    
    @Test
    void ReceiptInTextArea() {
    	Product heels1 = new Product("Heels",45.0, true, 5, 11);
    	Product heels2 = new Product("Heels",45.0, true, 5, 11);
    	Product sneakers1 = new Product("Sneakers",120.0, true, 5, 11);
    	Product boots1 = new Product("Boots",135.0, true, 5, 11);
    	Product boots2 = new Product("Boots",135.0, true, 5, 11);
    	
    	cart.addItem(heels1);
    	cart.addItem(heels2);
    	cart.addItem(sneakers1);
    	cart.addItem(boots1);
    	cart.addItem(boots2);
    	
    	assertEquals("Number of Heels: 2\n"
    			+ "Number of Sneakers: 1\n"
    			+ "Number of Sandals: 2\n"
    			+ "\nSubTotal: $480.0", cart.ReceiptInTextArea());
    	
    }
}
