package backend;

import javafx.beans.property.SimpleDoubleProperty;

import java.util.ArrayList;

public class ShoppingCart {

    private ArrayList<Product> contents;
    private SimpleDoubleProperty total;

    public ShoppingCart() {
        this.contents = new ArrayList<>();
        this.total = new SimpleDoubleProperty();
    }

    public ArrayList<Product> getContents() {
        return contents;
    }

    public void addItem(Product prod) {
        this.contents.add(prod);
        this.total.add(prod.getPrice());
    }

    public SimpleDoubleProperty getTotal() {
        return this.total;
    }
}
