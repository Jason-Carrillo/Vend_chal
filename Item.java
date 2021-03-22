import javax.swing.JLabel;

public class Item extends JLabel{
    private final String name;
    private final String price;
    private String position;
    private int quantity;

    public Item(String name, int quantity, String price) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;

        

    }

    public String getName(){
        return name;
    }

    public String getPrice(){
        return price;
    }

    public int getquantity(){
        return quantity;
    }

    public void updateQuantity(int quantity) {
        this.quantity = quantity;
    }    

    public String toString() {
        return (name + "( " + quantity + ")\nPrice: " + price );
    }


}
