package backend;

import java.util.ArrayList;

public class ShoppingCart {

    private ArrayList<Product> contents;
    private Double subtotal = 0.0;

    public ShoppingCart() {
        this.contents = new ArrayList<>();
    }

    public ArrayList<Product> getContents() {
        return contents;
    }

    public void addItem(Product prod) {
    	System.out.println("add Item Method");
        this.contents.add(prod);
        subtotal = subtotal + prod.getPrice();
        System.out.println("added " + prod.getName() );
        System.out.println("total: " + getFinalTotal());
    }

    public Double getSubTotal() {
    	return subtotal;
    }
    
    public Double getTax() {
    	double tax = Math.round(subtotal *7.25);
    	tax = tax / 100;
    	return tax;
    }
    
    public Double getFinalTotal() {
    	return getSubTotal() + getTax();
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
