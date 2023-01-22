public class Cart {
    private String name;
    private int quantity;
    private double price;

    public Cart(String name, int quantity,double price) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }

    public String getName() {
        return name;
    }
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quan){this.quantity = quan;}

    public void setPrice(double price) {
        this.price = price;
    }
    public double getPrice() {
        return price;
    }
}
