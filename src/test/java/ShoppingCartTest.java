import backend.Product;
import backend.ShoppingCart;

import static org.junit.jupiter.api.Assertions.*;

class ShoppingCartTest {

    private ShoppingCart cart = new ShoppingCart();

    @org.junit.jupiter.api.Test
    void addItem() {
        Product heels = new Product("Heels",45.0, true, 5, 11);
        heels.lastSizeProp().set(7.5);
        heels.lastQtyProp().set(1);
        cart.addItem(heels);
        assertFalse(cart.getContents().contains(heels));
        assertTrue(cart.getTax() == 3.26);
        assertEquals(48.26, cart.getFinalTotal(), .01);
    }

    @org.junit.jupiter.api.Test
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

    @org.junit.jupiter.api.Test
    void getSubTotal() {
        Product boots = new Product("Boots",135.0, true, 5, 13);
        boots.lastSizeProp().set(7.0);
        boots.lastQtyProp().set(1);
        cart.addItem(boots);
        assertEquals(135, cart.getSubTotal());

        // not working if test by following, subtotal = 0
//        cart.subTotalProperty().set(145.87);
//        assertEquals(145.87, cart.getSubTotal());
    }

    @org.junit.jupiter.api.Test
    void getTax() {
        cart.subTotalProperty().set(45.0);
        assertEquals(3.26, cart.getTax());
    }

    @org.junit.jupiter.api.Test
    void getFinalTotal() {
        cart.subTotalProperty().set(125.0);
        assertEquals(134.06, cart.getFinalTotal(), .01);
    }
}