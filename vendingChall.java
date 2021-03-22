import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
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
            loadToUI()

            System.out.println("Items: " + items.size() + " Row x Column: " + configRow + " x " + configColumn);
        } catch(IOException e) {
            e.printStackTrace();
        }
        }


    }





}