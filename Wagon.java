import java.util.*;


/**
 * A wagon is a part of a train.
 *
 * @params weight empty weight of the wagon in kg
 * @params length
 * @params type (e.g. passenger, goods, ...) from enum WagonType
 * @params maxPassengerCount
 * @params maxPayloadCount
 * @params typeDesignation
 * @params manufacturer
 * @params constructionDate
 * @params serialNumber
 * @params nextWagon
 * @params train
 */
public class Wagon {
    private final int weight;
    private final int length;
    private final WagonType type;
    private final int passengerCount;
    private final int maxPayload;
    private final String typeDesignation;
    private final String manufacturer;
    private final Date constructionDate;
    private final UUID serialNumber;
    private Wagon nextWagon;
    private Train train;


    public Wagon(
            int weight,
            int length,
            WagonType type,
            int maxPassengerCount,
            int maxPayloadCount,
            String typeDesignation,
            String manufacturer,
            Date constructionDate
    ) {
        this.weight = weight;
        this.length = length;
        this.type = type;
        this.passengerCount = maxPassengerCount;
        this.maxPayload = maxPayloadCount;
        this.typeDesignation = typeDesignation;
        this.manufacturer = manufacturer;
        this.constructionDate = constructionDate;
        this.serialNumber = UUID.randomUUID();
        this.nextWagon = null;
    }

    // getter and setter could automatically be created by lombok

    /**
     * Sets the next wagon of the wagon
     * To prevent circular references
     *
     * @param wagon
     */
    public void setNextWagon(Wagon wagon) {
        this.nextWagon = wagon;
    }

    /**
     * Sets the train of the wagon and throws an exception if the wagon is already assigned to a train
     *
     * @param train
     * @throws IllegalStateException
     */
    public void setTrain(Train train) throws IllegalStateException {
        if (this.train != null)
            throw new IllegalStateException("Wagon is already assigned to a train");
        this.train = train;
    }

    public void removeTrain() {
        this.train = null;
    }

    public int getWeight() {
        return weight;
    }

    public int getPassengerCount() {
        return passengerCount;
    }

    public int getMaxGoodsWeight() {
        return maxPayload;
    }

    public int getLength() {
        return length;
    }

    public Wagon getNextWagon() {
        return this.nextWagon;
    }

    public Train getTrain() {
        return this.train;
    }


    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Wagon wagon)) {
            return false;
        }
        return this.serialNumber.equals(wagon.serialNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.serialNumber);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Wagon: ");
        sb.append("weight: " + this.weight + " ");
        sb.append("length: " + this.length + " ");
        sb.append("type: " + this.type + " ");
        sb.append("maxPassengerCount: " + this.passengerCount + " ");
        sb.append("maxPayloadCount: " + this.maxPayload + " ");
        sb.append("typeDesignation: " + this.typeDesignation + " ");
        sb.append("manufacturer: " + this.manufacturer + " ");
        sb.append("constructionDate: " + this.constructionDate + " ");
        sb.append("serialNumber: " + this.serialNumber + " ");

        return sb.toString();


    }
}
