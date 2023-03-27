import java.util.*;

public abstract class TrainElement {
    private final int emptyWeight;
    private final int length;
    private final int maxPassengerCount;
    private final int maxGoodsWeight;
    private final String typeDesignation;
    private final UUID serialNumber;
    private final Date constructionDate;
    private final String manufacturer;
    private Train train;

    public TrainElement(int emptyWeight, int length, int maxPassengerCount, int maxGoodsWeight, String typeDesignation, UUID serialNumber, Date constructionDate, String manufacturer) {
        this.emptyWeight = emptyWeight;
        this.length = length;
        this.maxPassengerCount = maxPassengerCount;
        this.maxGoodsWeight = maxGoodsWeight;
        this.typeDesignation = typeDesignation;
        this.serialNumber = serialNumber;
        this.constructionDate = constructionDate;
        this.manufacturer = manufacturer;
    }

    public int getEmptyWeight() {
        return emptyWeight;
    }

    public int getLength() {
        return length;
    }

    public int getMaxPassengerCount() {
        return maxPassengerCount;
    }

    public int getMaxGoodsWeight() {
        return maxGoodsWeight;
    }

    public String getTypeDesignation() {
        return typeDesignation;
    }

    public UUID getSerialNumber() {
        return serialNumber;
    }

    public Date getConstructionDate() {
        return constructionDate;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public Train getTrain() {
        return train;
    }

    public void setTrain(Train train) {
        if (this.train != null && this.train.equals(train)) {
            throw new IllegalStateException("Train element is already assigned to a train");
        }
        this.train = train;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TrainElement that = (TrainElement) o;
        return serialNumber.equals(that.serialNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(serialNumber);
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.getClass().getSimpleName() + "; ")
                .append(maxPassengerCount + "; ")
                .append(maxGoodsWeight + "; ")
                .append(length + "; ")
                .append(emptyWeight + "; ")
                .append(typeDesignation + "; ")
                .append(serialNumber + "; ")
                .append(constructionDate + "; ")
                .append(manufacturer + "; ");
        return sb.toString();
    }

}
