import java.util.*;

public class Locomotive {
    // weight is the empty weight of the locomotive
    private final int weight;
    private final int length;
    // how much weight the locomotive can pull in addition to its own weight
    private final int power;
    private final DriveType driveType;
    private final int passengerCount;
    private final int maxPayload;
    private final String typeDesignation;
    private final String manufacturer;
    private final Date constructionDate;
    private final UUID serialNumber;
    private Train train;

    public Locomotive(
            int weight,
            int length,
            int power,
            DriveType driveType,
            int maxPassengerCount,
            int maxPayloadCount,
            String typeDesignation,
            String manufacturer,
            Date constructionDate
    ) {
        this.weight = weight;
        this.length = length;
        this.power = power + weight;
        this.driveType = driveType;
        this.passengerCount = maxPassengerCount;
        this.maxPayload = maxPayloadCount;
        this.typeDesignation = typeDesignation;
        this.manufacturer = manufacturer;
        this.constructionDate = constructionDate;
        this.serialNumber = UUID.randomUUID();
    }

    /**
     * Sets the train of the locomotive and throws an exception if the locomotive is already assigned to a train
     *
     * @param train
     * @throws IllegalStateException
     */
    public void setTrain(Train train) throws IllegalStateException {
        if (this.train != null)
            throw new IllegalStateException("Locomotive is already assigned to a train");

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

    public int getPower() {
        return power;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Locomotive locomotive)) {
            return false;
        }
        return this.serialNumber.equals(locomotive.serialNumber);
    }

    @Override
    public int hashCode() {
        return this.serialNumber.hashCode();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Locomotive: ");
        sb.append("weight: " + this.weight + ", ");
        sb.append("length: " + this.length + ", ");
        sb.append("power: " + this.power + ", ");
        sb.append("driveType: " + this.driveType + ", ");
        sb.append("maxPassengerCount: " + this.passengerCount + ", ");
        sb.append("maxPayloadCount: " + this.maxPayload + ", ");
        sb.append("typeDesignation: " + this.typeDesignation + ", ");
        sb.append("manufacturer: " + this.manufacturer + ", ");
        sb.append("constructionDate: " + this.constructionDate + ", ");
        sb.append("serialNumber: " + this.serialNumber + ", ");
        return sb.toString();
    }


}
