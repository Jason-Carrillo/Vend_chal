import javax.swing.*;
import java.awt.*;
public class Item extends JLabel {
    private final String name;
    private final String price;
    private String position;
    private int amount;
    public Item(String name, int amount, String price) {
        this.name = name;
        this.amount = amount;
        this.price = price;
        setText(toHtmlString());
        setHorizontalAlignment(JLabel.CENTER);
        setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 1));
    }
    public String getName() {
        return name;
    }
    public String getPrice() {
        return price;
    }
    public int getAmount() {
        return amount;
    }
    public void updateAmount(int amount) {
        this.amount = amount;
    }
    public String toString() {
        return (name + " (" + amount + ")\nPrice: " + price);
    }
     public String toHtmlString() {
         return ("<html><p style='text-align:center'>" + name + " (" + amount + ")<br>Price: " + price + "<br>Position: " + position + "</p></html>");
     }
    public void setPosition(String position) {
        this.position = position;
        setText(toHtmlString());
    }
    public String getPosition() {
        return position;
    }
}
