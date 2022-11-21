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
    		
    		finalText = finalText + "\n\nTotal: $" + total;
    		
    	}
    	return finalText;
    	
    }
}
