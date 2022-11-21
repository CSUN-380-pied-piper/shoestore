package backend;

import javafx.beans.property.SimpleDoubleProperty;

import java.util.ArrayList;

public class ShoppingCart {

    private ArrayList<Product> contents;
    private SimpleDoubleProperty total;

    public ShoppingCart() {
        this.contents = new ArrayList<>();
    }

    public ArrayList<Product> getContents() {
        return contents;
    }

    public void addItem(Product prod) {
    	System.out.println("add Item Method");
        this.contents.add(prod);
        total = total + prod.getPrice();
        System.out.println("added " + prod.getName() );
        System.out.println("total: " + getTotal());
    }

    public double getTotal() {
        return total;
    }
}
