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
    }

    @org.junit.jupiter.api.Test
    void getSubTotal() {
    }

    @org.junit.jupiter.api.Test
    void getTax() {
        cart.subTotalProperty().set(45.0);
        assertEquals(3.26, cart.getTax());
    }

    @org.junit.jupiter.api.Test
    void getFinalTotal() {
    }
}