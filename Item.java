import javax.swing.JLabel;

public class Item extends JLabel{
    private final String name;
    private final String price;
    private String position;
    private int amount;

    public Item(String name, int amount, String price) {
        this.name = name;
        this.amount = amount;
        this.price = price;

        

    }

    public String getName(){
        return name;
    };

    public String getPrice(){
        return price;
    };

    public int getAmount(){
        return amount;
    };


}
