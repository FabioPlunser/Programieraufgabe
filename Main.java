import java.util.*;

public class Main {

    public static void main(String[] args) {
        Locomotive l1 = new Locomotive(100, 100, 100, DriveType.DIESEL, 100, 100, "type", "manufacturer", new Date());
        Train train = new Train(l1);
        System.out.println(train);
    }
}
