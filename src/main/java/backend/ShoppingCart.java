package backend;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.HashMap;

public class ShoppingCart {

    private ObservableList<Product> contents;
    private SimpleDoubleProperty subTotal;
    private SimpleDoubleProperty tax;
    private SimpleDoubleProperty finalTotal;
    private Double subtotal = 0.0;
    private HashMap<String, Integer> productQtys;

    public ShoppingCart() {
        this.contents = FXCollections.observableArrayList();
        this.subTotal = new SimpleDoubleProperty(0.0);
        this.tax = new SimpleDoubleProperty();
        this.finalTotal = new SimpleDoubleProperty();
        this.tax.bind(subTotal.multiply(0.0725));
        this.finalTotal.bind(subTotal.add(tax));
        this.initQtyMap();
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
        productQtys.put(shoe, productQtys.get(shoe) + 1);
        subtotal = subtotal + copy.getPrice();
        subTotal.set(subtotal);
    }

    public void removeItem(Product prod) {
        subtotal = subtotal - prod.getPrice();
        subTotal.set(subtotal);
        contents.remove(prod);
    }

    private void initQtyMap() {
        this.productQtys = new HashMap<>();
        String[] shoeTypes = {"Heels", "Sneakers", "Sandals", "Boots"};
        for (String s : shoeTypes) {
            this.productQtys.put(s, 0);
        }
    }

    public Integer getShoeQty(String shoe) {
        return this.productQtys.get(shoe);
    }

    public Double getSubTotal() {
    	return subtotal;
    }
    
    public Double getTax() {
    	double tax = Math.round(subTotal.get() * 7.25);
    	tax = tax / 100;
    	return tax;
    }
    
    public Double getFinalTotal() {
        return finalTotalProperty().getValue();
    }
    
    public String ReceiptInTextArea() {
    	int HeelNum = 0;
    	int SneakerNum = 0;
    	int SandalNum = 0;
    	int BootNum = 0;
    	
    	String finalText = "";
    	
    	for (int i = 0; i < contents.size(); i++) {
    		if (contents.get(i).getName().equals("Heels")) {
    			HeelNum++;
    		} else if (contents.get(i).getName().equals("Sneakers")) {
    			SneakerNum++;
    		} else if (contents.get(i).getName().equals("Sandals")) {
    			SandalNum++;
    		} else if (contents.get(i).getName().equals("Boots")) {
    			BootNum++;
    		}
    		
    		if (HeelNum > 0) {
    			finalText = "Number of Heels:  "+ HeelNum;
    		}
    		if (SneakerNum > 0) {
    			finalText = finalText + "\nNumber of Sneakers: " + SneakerNum;
    		}
    		if (SandalNum > 0) {
    			finalText = finalText + "\nNumber of Sandals: " + SandalNum;
    		}
    		if (BootNum > 0) {
    			finalText = finalText + "\nNumber of Sandals: " + BootNum;
    		}
    		
    		finalText = finalText + "\n\nSubTotal: $" + subtotal;
    		
    	}
    	return finalText;
    	
    }
}
