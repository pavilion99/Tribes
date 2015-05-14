package co.valdeon.Tribes.storage;

public class Order {

    private OrderType type;
    private String by;

    public Order(OrderType a, String by) {
        this.type = a;
        this.by = by;
    }

    public String getString() {
        return "ORDER BY " + this.by + " " + this.type.val;
    }

    public String getLimitedString() {
        return this.by + " " + this.type.val;
    }

}
