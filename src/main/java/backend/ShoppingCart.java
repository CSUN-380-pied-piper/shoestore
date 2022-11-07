package backend;

import java.util.ArrayList;

public class ShoppingCart {

    private ArrayList<Product> contents;

    public ShoppingCart() {
        this.contents = new ArrayList<>();
    }

    public ArrayList<Product> getContents() {
        return contents;
    }
}
