import org.json.JSONArray;
import org.json.JSONObject;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Locale;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;


public class vendingChall extends JFrame{

    int configRow;
    int configColumn;

    ArrayList<Item> items = new ArrayList<>();

    ArrayList<JLabel> itemsList = new ArrayList<>();
    
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

        setLayout(new BorderLayout(5, 5));

        try{
            formatted = new JFormattedTextField(new MaskFormatter("?#"));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        formatted.setPreferredSize(new Dimension(200, 25));

        buttonCheckout = new JButton("Checkout");

        //TODO add checkoutItem() method
        buttonCheckout.addActionListener(e -> checkoutItem());

    }

    private void checkoutItem() {
        
        if(items.isEmpty())
        return;

        if(formatted.getText().isEmpty() || formatted.getText().isBlank()){
            JOptionPane.showMessageDialog(this, "Please enter a position to purchase item");
            return;
        }

        Item item = hasPosition(formatted.getText());
        if(item == null) {
            JOptionPane.showMessageDialog(this, "No item availale at this position");
        }

        if(!itemIsAvailable(item)) {
            JOptionPane.showMessageDialog(this, "Currently out of Stock");
        }

        JFrame checkoutPanel = new JFrame();
        JLabel label1 = new JLabel("Selected item: " + item.toString());
        JLabel label2 = new JLabel("Enter Money USD");
        JTextField textAmount = new JTextField(10);
        textAmount.addKeyListener(new keyAdapter() {

            @Override
            public void keyTyped(KeyEvent e){
                if(e.getKeyChar() == '.')
                return;

                if(!Character.isDigit(e.getKeyChar())) {
                    e.consume();
                }

            }
            
        });
        JButton buttonPurchase = new JButton("Complete Purchase");
        buttonPurchase.addActionListener(e -> {
            if(textAmount.getText().isEmpty() || textAmount.getText().isBlank()){
                JOptionPane.showMessageDialog(checkoutPanel, "Please enter funds to purchase item");
                return;
            }

            double enteredAmount = Double.parseDouble(textAmount.getText());
            double itemAmount = Double.parseDouble(item.getPrice().replaceAll("\\$", ""));

            if(enteredAmount < itemAmount){
                JOptionPane.showMessageDialog(checkoutPanel, "Insufficient funds to purchase item");
                return;
            }

            checkoutPanel.setVisible(false);

            item.updateQuantity(item.getquantity() - 1);

            double remainder = enteredAmount - itemAmount;
            JOptionPane.showMessageDialog(this, 
                item.getName() + " was purchased!" +
                "\n your change is : $ " + String.format("%.2f", remainder));

                loadToUI();

        });

        checkoutPanel.add(label1, BorderLayout.PAGE_START);
        checkoutPanel.add(label2, BorderLayout.LINE_START);
        checkoutPanel.add(textAmount, BorderLayout.LINE_END);
      
    }

    private Item hasPosition(String position){
        for(Item item : items){
            if(item.getPosition().equalsIgnoreCase(position))
            return item;
        }
        return null;
    }

    private boolean itemIsAvailable(Item item) {
        return item.getquantity() != 0;
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

        String filename;

        int ret  = fileChooser.showOpenDialog(null);
        if(ret == JFileChooser.APPROVE_OPTION){
            filename = fileChooser.getSelectedFile().getName();
        } else {
            return;
        }
    

        // Create this method 
        loadJSONFile(filename);
    }

    private void loadJSONFile(String filename){
        try {
            BufferedReader br = new BufferedReader(new FileReader(filename));

            StringBuilder json = new StringBuilder();
            String line;

            while((line = br.readLine()) != null){
                json.append(line);
            }

            JSONObject jsonObject = new JSONObject(json.toString());

            int rows = jsonObject.getJSONObject("config").getInt("rows");
            int columns = jsonObject.getJSONObject("config").getInt("columns");

            ArrayList<Item> items = new ArrayList<>();

            JSONArray itemsArray = jsonObject.getJSONArray("items");

            for(int i = 0; i < itemsArray.length(); i++){
                JSONObject itemObject = itemsArray.getJSONObject(i);

                Item item = new Item(
                    itemObject.getString("name"),
                    itemObject.getInt("quantity"),
                    itemObject.getString("price")
                );

                items.add(item);
            }

            if(rows * columns < items.size()){
                System.err.println( items.size() + " items do not fit inside " + rows + " rows, and " + columns + " columns ");
                return;
            }

            configRow = rows;
            configColumn = columns;
            this.items.clear();
            this.items.addAll(items);

            //TODO- create this method tha twill load items to the ui.
            loadToUI();

            System.out.println("Items: " + items.size() + " Row x Column: " + configRow + " x " + configColumn);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    private void loadToUI() {
        String[] columnLabel = new String[configColumn];
        String[] rowLabel = new String[configRow];

        //Columns
        for(int i; i < configColumn; i++){
            columnLabel[i] = (i + 1) + "";
        }

        //Rows
        for(int i = 0; i < configRow; i++){
            rowLabel[i] = (i + 1) + "";
        }

        for (JLabel label : itemsList) {
            itemsPanel.remove(label);
        }

        itemsList.clear();

        itemsPanel.setLayout(new GridBagLayout());

        itemsPanel.validate();

        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new Insets(2, 2, 2, 2);

        int itemIndex = 0;
        for(int row = 0; row < configRow; row++){
            for(int col = 0; row < configColumn;  col++){
                gridBagConstraints.gridy = row;
                gridBagConstraints.gridx = col;

                if(itemIndex < items.size()){

                    items.get(itemIndex).setPosition(rowLabel[row] + columnLabel[col]);

                    itemsList.add(items.get(itemIndex));

                    itemsPanel.add(items.get(itemIndex), gridBagConstraints);
                    itemIndex++;
                }

            }
        }
        itemsPanel.revalidate();
        itemsPanel.repaint();
    }


}