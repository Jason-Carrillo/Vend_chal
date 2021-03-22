import java.util.ArrayList;

import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class vendingChall extends JFrame{

    int configRow;
    int configColumn;

    ArrayList<Item> items = new ArrayList<>();

    ArrayList<Jlabel> itemsList = new ArrayList<>();

    JPanel itemsPanel = new JPanel();

    JFormattedTextField formatted;

    JButton buttonCheckout;
    
    public vendingChall() {
        setTitle("MS3 Vending Machine");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);


    }

    public static void main(String[] args) {

        for(int i = 0; i <= rows; i++){
            machineBoard.add({" ", " | ", " "});
        };

        rowColumnPrinter(6, 5);

        System.out.println("Please enter Row (Letter) for desired candy.");
        System.out.println("Please enter Column (Number) for desired candy.");
    }
}