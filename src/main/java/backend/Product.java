package backend;

import java.util.ArrayList;

public class Product {

    private String id;

    private Double price;
    private String name;
    private Boolean halfSizes;
    private ArrayList<Double> sizes;

    public Product(String name, Double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public Double getPrice() {
        return price;
    }
}
