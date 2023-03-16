import java.util.*;


/**
 * Train class
 * A train is a list of locomotives and wagons
 * A train must have at least one locomotive
 */
public class Train {

    private final List<Locomotive> locomotives = new ArrayList<>();
    private final List<Wagon> wagons = new ArrayList<>();
    private int conductorCount;

    public Train(Locomotive locomotive) {
        this.locomotives.add(locomotive);
        locomotive.setTrain(this);
    }

    /**
     * Add a locomotive to the front of the train
     *
     * @param locomotive
     */
    public void addLocomotive(Locomotive locomotive) throws RuntimeException {
        // check if train is already in list
        if (this.locomotives.contains(locomotive)) {
            throw new RuntimeException("Locomotive already in train");
        }
        // add locomotive to front of train
        this.locomotives.add(0, locomotive);
        locomotive.setTrain(this);

        // check if conductor is needed
        checkConductor();
    }

    /**
     * Remove a locomotive from the train
     *
     * @param locomotive
     */
    public void removeLocomotive(Locomotive locomotive) {
        // check if locomotive is in train
        if (!this.locomotives.contains(locomotive)) {
            throw new RuntimeException("Locomotive not in train");
        }
        if (this.locomotives.size() == 1) {
            throw new RuntimeException("Cannot remove last locomotive");
        }
        this.locomotives.remove(locomotive);
        locomotive.removeTrain();

        // check if conductor is still needed
        checkConductor();
    }

    /**
     * Add a wagon to the end of the train
     * Sets the next wagon of the last wagon in the train to the new wagon
     *
     * @param wagon
     * @throws RuntimeException
     */
    public void addWagon(Wagon wagon) throws RuntimeException {
        //check if train is already in list and if wagon is already assigned to train
        if (this.wagons.contains(wagon) || wagon.getTrain() != null) {
            throw new RuntimeException("Wagon already in train");
        }
        // new wagon is added at end of the train so the wagon before must know the new wagon
        // the new wagon has no next wagon
        if (this.wagons.size() > 0) {
            this.wagons.get(this.wagons.size() - 1).setNextWagon(wagon);
        }
        wagon.setNextWagon(null);
        this.wagons.add(wagon);
        wagon.setTrain(this);

        // check if conductor is needed
        checkConductor();
    }


    /**
     * Remove a wagon from the train
     * Sets the next wagon of the wagon before the removed wagon to the wagon after the removed wagon
     *
     * @param wagon
     * @throws RuntimeException
     */
    public void removeWagon(Wagon wagon) throws RuntimeException {
        // check if wagon is in train
        if (!this.wagons.contains(wagon)) {
            throw new RuntimeException("Wagon not in train");
        }
        if (this.wagons.size() == 1) {
            throw new RuntimeException("Cannot remove last wagon");
        }

        // set next wagon of wagon before removed wagon
        // if wagon is first wagon in train, set next wagon of first wagon to null
        if (this.wagons.indexOf(wagon) > 0) {
            this.wagons.get(this.wagons.indexOf(wagon) - 1).setNextWagon(this.wagons.get(this.wagons.indexOf(wagon) + 1));
        } else {
            this.wagons.get(0).setNextWagon(null);
        }

        // remove wagon from train and train from wagon
        this.wagons.remove(wagon);
        wagon.removeTrain();

        // check if conductor is still needed
        checkConductor();
    }


    /**
     * Weight is the sum of all empty weights of the locomotives and wagons
     *
     * @return int weight
     */
    public int getWeight() {

        int weight = 0;
        for (Locomotive locomotive : this.locomotives) {
            weight += locomotive.getWeight();
        }
        for (Wagon wagon : this.wagons) {
            weight += wagon.getWeight();
        }
        return weight;
    }

    /**
     * Max passenger count is the sum of all passenger counts of the locomotives and wagons
     *
     * @return int max passenger count
     */
    public int getMaxPassengerCount() {
        int maxPassengerCount = 0;
        for (Locomotive locomotive : this.locomotives) {
            maxPassengerCount += locomotive.getPassengerCount();
        }
        for (Wagon wagon : this.wagons) {
            maxPassengerCount += wagon.getPassengerCount();
        }
        return maxPassengerCount;
    }

    /**
     * Max goods weight is the sum of all max goods weights of the locomotives and wagons
     *
     * @return int max goods weight
     */
    public int getMaxGoodsWeight() {
        int maxGoodsWeight = 0;
        for (Locomotive locomotive : this.locomotives) {
            maxGoodsWeight += locomotive.getMaxGoodsWeight();
        }
        for (Wagon wagon : this.wagons) {
            maxGoodsWeight += wagon.getMaxGoodsWeight();
        }
        return maxGoodsWeight;
    }

    /**
     * Max payload is the sum of all max passenger counts * 75kg and max goods weights
     *
     * @return int max payload
     */
    public int getMaxPayload() {
        return this.getMaxPassengerCount() * 75 + this.getMaxGoodsWeight();
    }


    /**
     * Max weight the train could possibly have is the max payload + the empty weight of the train
     *
     * @return int max weight
     */
    public int getMaxWeight() {
        return this.getMaxPayload() + this.getWeight();
    }

    /**
     * Length is the sum of all lengths of the locomotives and wagons
     *
     * @return int length
     */
    public int getLength() {
        int length = 0;
        for (Locomotive locomotive : this.locomotives) {
            length += locomotive.getLength();
        }
        for (Wagon wagon : this.wagons) {
            length += wagon.getLength();
        }
        return length;
    }


    /**
     * Check if the train is drivable
     * The sum of all locomotive powers must be greater than the max payload
     *
     * @return boolean is drivable
     */
    public boolean checkTrainDrivable() {
        int maxPower = 0;
        for (Locomotive locomotive : this.locomotives) {
            maxPower += locomotive.getPower();
        }
        return maxPower > this.getMaxPayload();
    }


    /**
     * Check if the train needs a conductor
     * If the max passenger count is greater than 0 and a multiple of 50, the train needs a conductor
     */
    private void checkConductor() {
        if (this.getMaxPassengerCount() > 0 && this.getMaxPassengerCount() % 50 == 0) {
            this.conductorCount = this.getMaxPassengerCount() / 50;
        } else {
            this.conductorCount = 0;
        }
    }

    /**
     * Get the number of conductors needed
     *
     * @return the number of conductors needed
     */
    public int getConductorCount() {
        return this.conductorCount;
    }

    /**
     * Get the maximum number of conductors needed
     *
     * @return the maximum number of conductors needed
     */
    public int getMaxNeedOfConductor() {
        return this.getMaxPassengerCount() / 50;
    }


    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Train train)) {
            return false;
        }
        return this.locomotives.equals(train.locomotives) && this.wagons.equals(train.wagons);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.locomotives, this.wagons);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Train: \n");
        sb.append("Locomotives: \n");
        for (Locomotive locomotive : this.locomotives) {
            sb.append(locomotive.toString());
            sb.append("\n");
        }
        sb.append("Wagons: \n");
        for (Wagon wagon : this.wagons) {
            sb.append(wagon.toString());
            sb.append("\n");
        }
        return sb.toString();
    }

    public List<Locomotive> getLocomotives() {
        return this.locomotives;
    }

    public List<Wagon> getWagons() {
        return this.wagons;
    }
}
