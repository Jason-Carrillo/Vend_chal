import java.io.File;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFileChooser;
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

    private void initializeUI(){
        JButton btnLoad = new JButton("Load new items");
        btnLoad.addActionListener(e -> loadNewitems());
    }

    private void loadNewitems(){
        JFileChooser fileChooser = new JFileChooser();

        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.addChoosableFileFilter(new FileFilter(){

            @Override
            public boolean accept(File file) {
                if (file.isDirectory())
                return true;

                return file.getName().toLowerCase()(Locale.ROOT).endsWith(".json");
            }

            @Override
            public String getDescription() {
                return "JSON file (*.json)";
            }
        });
    }

}