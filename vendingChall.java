import java.util.ArrayList;

import jdk.internal.joptsimple.internal.Rows;

public class vendingChall {

    static char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    
    public static void rowSetter(int x){
        for(int i = 0; i <= x; i++){
            System.out.print(alphabet[i]);
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

        rowColumnPrinter(6, 5);

        System.out.println("Please enter Row (Letter) for desired candy.");
        System.out.println("Please enter Column (Number) for desired candy.");
    }
}