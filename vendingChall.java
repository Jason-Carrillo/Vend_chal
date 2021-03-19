import jdk.internal.joptsimple.internal.Rows;

public class vendingChall {

    public static void rowSetter(int x){
        for(int i = 0; i <= x; i++){
            System.out.print(i);
        }
        System.out.println();
    }
    public static void main(String[] args) {

        rowSetter(5);

        System.out.println("Please enter Row (Letter) for desired candy.");
        System.out.println("Please enter Column (Number) for desired candy.");
    }
}