import java.util.ArrayList;

import jdk.internal.joptsimple.internal.Rows;

public class vendingChall {

    static char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();

    static int rows = 4;

    static int columns = 8;

    static ArrayList<Object> machineBoard;


    
    public static void rowSetter(int x){

        for(int i = -1; i <= x; i++){

            if(i == -1) {
            System.out.print("+");
            } else {
            System.out.print(alphabet[i]);
        }
        }
        System.out.println();
    }

    public static void columnSetter(int x){
        for(int i = 1; i <= x; i++){
            System.out.println(i);
        }
        System.out.println();
    }

    public static void rowColumnPrinter(int x, int y){
        rowSetter(x);
        columnSetter(y);
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