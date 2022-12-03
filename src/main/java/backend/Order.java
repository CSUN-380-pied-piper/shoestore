package backend;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Order implements Queryable {

    private SimpleIntegerProperty id;
    private SimpleDoubleProperty total;
    private SimpleBooleanProperty shipped;

    public Order(Integer id, Double total, Boolean shipped) {
        this.id = new SimpleIntegerProperty(id);
        this.total = new SimpleDoubleProperty(total);
        this.shipped = new SimpleBooleanProperty(shipped);
    }

    public int getId() {
        return id.get();
    }

    public SimpleIntegerProperty idProperty() {
        return id;
    }

    public double getTotal() {
        return total.get();
    }

    public SimpleDoubleProperty totalProperty() {
        return total;
    }

    public boolean isShipped() {
        return shipped.get();
    }

    public SimpleBooleanProperty shippedProperty() {
        return shipped;
    }

}
