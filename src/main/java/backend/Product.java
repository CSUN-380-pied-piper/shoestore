package backend;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class Product {

    private String id;

    private SimpleDoubleProperty price;
    private SimpleStringProperty name;
    private Boolean halfSizes;
    private ObservableList<Double> sizes;

    public Product(String name, Double price) {
        this(name, price, true, 5, 12);
    }

    public Product(String name, Double price, boolean half, int min, int max) {
        this.name = new SimpleStringProperty(name);
        this.price = new SimpleDoubleProperty(price);
        this.halfSizes = half;
        this.sizes = initSizes(min, max);
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProp() {
        return this.name;
    }

    public SimpleDoubleProperty priceProp() {
        return this.price;
    }

    public Double getPrice() {
        return price.get();
    }

    public ObservableList<Double> getSizes() {
        return sizes;
    }

    private ObservableList<Double> initSizes(int min, int max) {
        ObservableList<Double> sizes = FXCollections.observableArrayList();
        for (int i = min; i<=max; i++){
            sizes.add(Double.valueOf(i));
            if (halfSizes && i < max) {
                sizes.add(Double.valueOf(i + 0.5));
            }
        }
        return sizes;
    }
}
