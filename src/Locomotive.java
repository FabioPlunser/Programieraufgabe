import java.util.*;

public class Locomotive extends TrainElement {
    private final DriveType driveType;
    private final int power;

    public Locomotive(int emptyWeight, int length, int maxPassengerCount, int maxGoodsWeight, String typeDesignation, UUID serialNumber, Date constructionDate, String manufacturer, DriveType driveType, int power) {
        super(emptyWeight, length, maxPassengerCount, maxGoodsWeight, typeDesignation, serialNumber, constructionDate, manufacturer);
        this.driveType = driveType;
        this.power = power;
    }

    public DriveType getDriveType() {
        return driveType;
    }


    public int getPower() {
        return power;
    }

}
