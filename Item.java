import java.awt.*;

import javax.swing.*;

public class Item extends JLabel{
    private final String name;
    private final String price;
    private String position;
    private int quantity;

    public Item(String name, int quantity, String price) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;

        setText(toHtmlString());
        
        setHorizontalAlignment(JLabel.CENTER);

        setBorder(BorderFactory.createLineBorder(Color.CYAN, 1));
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

    public String toHtmlString(){
        //Double check for formatting issues. If there are errors
        return ("<html><p style='text-align:center'>" + name + " (" + quantity + ")<br>Price: " + price + "<br>Position: " + position + "</p></html>");
    }

    public void setPosition(String position){
        this.position = position;
        setText(toHtmlString());
    }

    public String getPosition(){
        return position;
    }

}
