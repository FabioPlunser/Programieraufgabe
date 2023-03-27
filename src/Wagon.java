import java.util.Date;
import java.util.UUID;

public class Wagon extends TrainElement{
    private final WagonType wagonType;


    public Wagon(int emptyWeight, int length, int maxPassengerCount, int maxGoodsWeight, String typeDesignation, UUID serialNumber, Date constructionDate, String manufacturer, WagonType wagonType) {
        super(emptyWeight, length, maxPassengerCount, maxGoodsWeight, typeDesignation, serialNumber, constructionDate, manufacturer);
        this.wagonType = wagonType;
    }

    public WagonType getWagonType() {
        return wagonType;
    }
}
