package backend;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class Product {

    private String id;
    private SimpleDoubleProperty price;
    private SimpleStringProperty name;
    private SimpleDoubleProperty lastSize;
    private SimpleIntegerProperty lastQty;
    private Boolean halfSizes;
    private ObservableList<Double> sizes;
    private Double minSize;
    private Double maxSize;

    public Product(String name, Double price) {
        this(name, price, true, 5, 12);
    }

    public Product(String name, Double price, boolean half, int min, int max) {
        this.name = new SimpleStringProperty(name);
        this.price = new SimpleDoubleProperty(price);
        this.lastQty = new SimpleIntegerProperty(1);
        this.lastSize = new SimpleDoubleProperty(min);
        this.halfSizes = half;
        this.sizes = initSizes(min, max);
        this.minSize = Double.valueOf(min);
        this.maxSize = Double.valueOf(max);
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

    public SimpleDoubleProperty lastSizeProp() {
        return this.lastSize;
    }

    public SimpleIntegerProperty lastQtyProp() {
        return this.lastQty;
    }

    public int getLastQty() {
        return this.lastQty.get();
    }

    public double getLastSize() {
        return this.lastSize.get();
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
