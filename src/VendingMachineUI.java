package src;

import org.json.JSONArray;
import org.json.JSONObject;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Locale;


public class VendingMachineUI extends JFrame {
    int configRow, configColumn;
    ArrayList<Item> items = new ArrayList<>();
    ArrayList<JLabel> listOfItems = new ArrayList<>();
    JPanel itemsPanel = new JPanel();
    JFormattedTextField formatted;
    JButton btnCheckout;
    public VendingMachineUI() {
        setTitle("MS3 Vending Machine");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        initializeUI();
        setVisible(true);
    }
    private void initializeUI() {
        JButton btnLoad = new JButton("Load new items");
        btnLoad.addActionListener(e -> loadNewItems());
        setLayout(new BorderLayout(5, 5));
        try {
            formatted = new JFormattedTextField(new MaskFormatter("?#"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        formatted.setPreferredSize(new Dimension(200, 25));
        btnCheckout = new JButton("Checkout");
        btnCheckout.addActionListener(e -> checkoutItem());
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new BorderLayout());
        controlPanel.add(btnLoad, BorderLayout.PAGE_START);
        controlPanel.add(new JLabel("Enter the  position of the item you want to buy (e.g. <Row><Column>): "), BorderLayout.LINE_START);
        controlPanel.add(formatted, BorderLayout.LINE_END);
        controlPanel.add(btnCheckout, BorderLayout.PAGE_END);
        add(itemsPanel, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.PAGE_END);
    }
    private void checkoutItem() {
        if (items.size() == 0)
            return;
        if (formatted.getText().isEmpty() || formatted.getText().isBlank()) {
            JOptionPane.showMessageDialog(this, " Please enter a position to checkout an item");
            return;
        }
        Item item = hasPosition(formatted.getText());
        if (item == null) {
            JOptionPane.showMessageDialog(this, "There is no item at the given position");
            return;
        }
        if (!isItemAvailable(item)) {
            JOptionPane.showMessageDialog(this, "The selected item is out of stock.");
            return;
        }
        JFrame checkoutPanel = new JFrame();
        JLabel label1 = new JLabel("Selected item: " + item.toString());
        JLabel label2 = new JLabel("Enter an amount of USD: ");
        JTextField txtAmount = new JTextField(10);
        txtAmount.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (e.getKeyChar() == '.')
                    return;
                if (!Character.isDigit(e.getKeyChar())) {
                    e.consume();
                }
            }
        });
        JButton btnPurchase = new JButton("Purchase Item");
        btnPurchase.addActionListener(e -> {
            if (txtAmount.getText().isEmpty() || txtAmount.getText().isBlank()) {
                JOptionPane.showMessageDialog(checkoutPanel, "Please enter an money to purchase the item");
                return;
            }
            double enteredAmount = Double.parseDouble(txtAmount.getText());
            double itemAmount = Double.parseDouble(item.getPrice().replaceAll("\\$", ""));
            if (enteredAmount < itemAmount) {
                JOptionPane.showMessageDialog(checkoutPanel, "There is not enough money to purchase the selected item");
                return;
            }
            checkoutPanel.setVisible(false);
            item.updateAmount(item.getAmount() - 1);
            double remaining = enteredAmount - itemAmount;
            JOptionPane.showMessageDialog(this,
                    "You purchased " + item.getName() + "\nYour change $" +
                            String.format("%.2f", remaining));
            loadToUI();
        });
        checkoutPanel.add(label1, BorderLayout.PAGE_START);
        checkoutPanel.add(label2, BorderLayout.LINE_START);
        checkoutPanel.add(txtAmount, BorderLayout.LINE_END);
        checkoutPanel.add(btnPurchase, BorderLayout.PAGE_END);
        checkoutPanel.setTitle("Checkout item");
        checkoutPanel.setSize(400, 100);
        checkoutPanel.setLocationRelativeTo(null);
        checkoutPanel.setVisible(true);
    }
    private Item hasPosition(String position) {
        for (Item item : items) {
            if (item.getPosition().equalsIgnoreCase(position))
                return item;
        }
        return null;
    }
    private boolean isItemAvailable(Item item) {
        return item.getAmount() != 0;
    }
    private void loadNewItems() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File("/Users/jason/IdeaProjects/vendingChall"));
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.addChoosableFileFilter(new FileFilter() {
            @Override
            public boolean accept(File file) {
                if (file.isDirectory())
                    return true;
                return file.getName().toLowerCase(Locale.ROOT).endsWith(".json");
            }
            @Override
            public String getDescription() {
                return "JSON file (*.json)";
            }
        });
        String filename;
        int ret = fileChooser.showOpenDialog(null);
        if (ret == JFileChooser.APPROVE_OPTION) {
            filename = fileChooser.getSelectedFile().getName();
        } else {
            return;
        }
        loadJSON(filename);
    }
    private void loadJSON(String filename) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(filename));
            StringBuilder json = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                json.append(line);
            }
            JSONObject jsonObject = new JSONObject(json.toString());
            int rows = jsonObject.getJSONObject("config").getInt("rows");
            int columns = jsonObject.getJSONObject("config").getInt("columns");
            ArrayList<Item> items = new ArrayList<>();
            JSONArray itemsArray = jsonObject.getJSONArray("items");
            for (int i = 0; i < itemsArray.length(); i++) {
                JSONObject itemObject = itemsArray.getJSONObject(i);
                Item item = new Item(
                        itemObject.getString("name"),
                        itemObject.getInt("amount"),
                        itemObject.getString("price")
                );
                items.add(item);
            }
            if (rows * columns < items.size()) {
                System.err.println("Can't fit " + items.size() + " items within " + rows + " rows " + columns + " columns");
                return;
            }
            configRow = rows;
            configColumn = columns;
            this.items.clear();
            this.items.addAll(items);
            loadToUI();
            System.out.println("Items: " + items.size() + " Row x Col: " + configRow + "x" + configColumn);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void loadToUI() {
        String[] colLabel = new String[configColumn];
        String[] rowLabel = new String[configRow];
        for (int i = 0; i < configColumn; i++) {
            colLabel[i] = (i + 1) + "";
        }
        for (int i = 0; i < configRow; i++) {
            rowLabel[i] = Character.toString(65 + i);
        }
        for (JLabel label : listOfItems) {
            itemsPanel.remove(label);
        }
        listOfItems.clear();
        itemsPanel.setLayout(new GridBagLayout());
        itemsPanel.validate();
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new Insets(6, 6, 6, 6);
        int itemIndex = 0;
        for (int row = 0; row < configRow; row++) {
            for (int col = 0; col < configColumn; col++) {
                gridBagConstraints.gridy = row;
                gridBagConstraints.gridx = col;
                if (itemIndex < items.size()) {
                    items.get(itemIndex).setPosition(rowLabel[row] + colLabel[col]);
                    listOfItems.add(items.get(itemIndex));
                    itemsPanel.add(items.get(itemIndex), gridBagConstraints);
                    itemIndex++;
                }
            }
        }
        itemsPanel.revalidate();
        itemsPanel.repaint();
    }
}