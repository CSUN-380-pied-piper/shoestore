package backend;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.text.NumberFormat;

public class ShoppingCart {

    private NumberFormat df;
    private ObservableList<Product> contents;
    private SimpleDoubleProperty subTotal;
    private SimpleDoubleProperty tax;
    private SimpleDoubleProperty finalTotal;
    private SimpleListProperty<Product> isEmpty;
    private SimpleObjectProperty<ObservableList<Product>> contentsProp;
    private Double subtotal = 0.0;

    public ShoppingCart(NumberFormat df) {
        this.df = df;
        this.contents = FXCollections.observableArrayList();
        this.subTotal = new SimpleDoubleProperty(0.0);
        this.tax = new SimpleDoubleProperty();
        this.finalTotal = new SimpleDoubleProperty();
        this.tax.bind(subTotal.multiply(0.0725));
        this.contentsProp = new SimpleObjectProperty<>(contents);
        this.isEmpty = new SimpleListProperty<>();
        this.isEmpty.bind(contentsProp);
        this.finalTotal.bind(subTotal.add(tax));
    }

    public ObservableList<Product> getContents() {
        return contents;
    }

    public SimpleDoubleProperty subTotalProperty() {
        return subTotal;
    }

    public SimpleDoubleProperty taxProperty() {
        return tax;
    }

    public SimpleDoubleProperty finalTotalProperty() {
        return finalTotal;
    }

    public void addItem(Product prod) {
        Product copy = new Product(prod);
        this.contents.add(copy);
        String shoe = copy.getName();
        subtotal = subtotal + copy.getPrice();
        subTotal.set(subtotal);
    }

    public void removeItem(Product prod) {
        subtotal = subtotal - prod.getPrice();
        subTotal.set(subtotal);
        contents.remove(prod);
    }

    public Double getSubTotal() {
    	return subtotal;
    }
    
    public Double getTax() {
    	double tax = Math.round(subTotal.get() * 7.25);
    	tax = tax / 100;
    	return tax;
    }

    public ObservableList<Product> getIsEmpty() {
        return isEmpty.get();
    }

    public SimpleListProperty<Product> isEmptyProperty() {
        return isEmpty;
    }
    
    public Double getFinalTotal() {
        return finalTotalProperty().getValue();
    }
    
    public String ReceiptInTextArea() {
        StringBuilder builder = new StringBuilder();
    	
    	for (Product p: contents) {
            builder.append(p.getName());
            builder.append(", Size: ");
            builder.append(p.getLastSize());
            builder.append("\n");
    	}
        // now add the subtotal, tax, and shipping
        builder.append("\n");
        builder.append("Subtotal: ");
        builder.append(df.format(subTotal.get()));
        builder.append("\n");
        builder.append("Tax: ");
        builder.append(df.format(tax.get()));
        builder.append("\n");
        builder.append("Total: ");
        builder.append(df.format(finalTotal.get()));
        return builder.toString();
    }
}
