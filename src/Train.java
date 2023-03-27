import java.util.*;

public class Train {
    /**
     * the train is made up of a doubly linked list of train elements
     * a doubly linked list has the advantage that a node knows its predecessor and successor
     * because the build in LinkedList does not allow to use the predecessor and successor
     * nodes it's easier to prevent a train from having a cycle
     */
    private LinkedList<TrainElement> train = new LinkedList<>();

    /**
     * create a new train with a locomotive
     * @param locomotive the locomotive which should be added to the train
     */
    public Train(Locomotive locomotive){
        train.add(locomotive);
    }

    /**
     * adds a train element to the end of the train
     * @param trainElement the train element which should be added to the train
     */
    public void addTrainElement(TrainElement trainElement) throws IllegalArgumentException{
        if(train.contains(trainElement))
            throw new IllegalArgumentException("Train already contains this train element");
        trainElement.setTrain(this);
        train.add(trainElement);
    }

    /**
     * adds a train element to the train at a specific position
     * @param trainElement the train element which should be added to the train
     * @param position the position where the train element should be added
     */
    public void addTrainElement(TrainElement trainElement, int position) throws IllegalArgumentException {
        if(train.contains(trainElement))
            throw new IllegalArgumentException("Train already contains this train element");
        if(position < 0 || position > train.size())
            throw new IllegalArgumentException("Position is out of bounds");
        if(position == 0 && trainElement instanceof Wagon)
            throw new IllegalArgumentException("A locomotive must be at the beginning of the train");
        train.add(position, trainElement);
        trainElement.setTrain(this);
    }

    /**
     * adds a train element to the train after a specific train element
     * @param element1 the current element
     * @param element2 the element which should be added after the current element
     */
    public void addTrainElementAfter(TrainElement element1, TrainElement element2) throws IllegalArgumentException {
        if(train.contains(element2))
            throw new IllegalArgumentException("Train already contains this train element");
        train.add(train.indexOf(element1) + 1, element2);
        element2.setTrain(this);
    }

    /**
     * adds a train element to the train before a specific train element
     * @param element1 the current element
     * @param element2 the element which should be added before the current element
     */
    public void addTrainElementBefore(TrainElement element1, TrainElement element2) throws IllegalArgumentException, InvalidTrainException {
        if(train.contains(element2))
            throw new IllegalArgumentException("Train already contains this train element");
        if(train.indexOf(element1) == 0 &&  !(element2 instanceof Locomotive))
            throw new InvalidTrainException("A locomotive must be at the beginning of the train");
        train.add(train.indexOf(element1), element2);
        element2.setTrain(this);
    }

    /**
     * removes a train element from the train
     * @param trainElement the train element which should be removed from the train
     */
    public void removeTrainElement(TrainElement trainElement) throws IllegalArgumentException, InvalidTrainException {
        if(!train.contains(trainElement))
            throw new IllegalArgumentException("Train does not contain this train element");
        if(train.size() == 1 && trainElement instanceof Locomotive)
            throw new InvalidTrainException("A train must have at least one locomotive");
        train.remove(trainElement);
        trainElement.setTrain(null);
    }

    /**
     * Calculates the total empty weight of the train
     * @return int
     */
    public int getEmptyWeight() { return train.stream().mapToInt(TrainElement::getEmptyWeight).sum(); }

    /**
     * Calculates the total empty weight of the train
     * @return int
     */
    public int getMaxPassengerCount() { return train.stream().mapToInt(TrainElement::getMaxPassengerCount).sum(); }

    /**
     * Calculates the maximum payload weight of the train
     * @return int
     */
    public int getMaxGoodsWeight(){
        return train.stream().mapToInt(TrainElement::getMaxGoodsWeight).sum();
    }

    /**
     * Calculates the maximum weight of the train
     * @return int
     */
    public int getMaxLoadWeight(){
        return getMaxPassengerCount() * 75 + getMaxGoodsWeight();
    }

    /**
     * Calculates the maximum weight of the train
     * @return int
     */
    public int getMaxWeight(){
        return getEmptyWeight() + getMaxLoadWeight();
    }

    /**
     * Calculates the total length of the train
     * @return int
     */
    public int getLength(){
        return train.stream().mapToInt(TrainElement::getLength).sum();
    }

    /**
     * Checks if the train is operable
     * A train is operable if the sum of the power of all locomotives is greater than or equal to the maximum weight of the train
     * @return boolean
     */
    public boolean isOperational(){
        int locomotivesPower = train.stream()
                .filter(trainElement -> trainElement instanceof Locomotive)
                .mapToInt(trainElement -> ((Locomotive) trainElement).getPower())
                .sum();
        return locomotivesPower >= getMaxWeight();
    }

    /**
     * Calculates the maximum number of conductors
     * Every 50 passengers need one conductor
     * @return int
     */
    public int getRequiredNumberOfConductors(){
        if(getMaxPassengerCount() > 0) {
            return Math.max(getMaxPassengerCount() / 50, 1);
        }
        else
            return 0;
    }

    /**
     * Returns the train
     * Mainly used for testing
     * @return LinkedList<TrainElement>
     */
    public LinkedList<TrainElement> getTrain() {
        return train;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Train: \n");
        for (TrainElement trainElement : train) {
            sb.append(train.indexOf(trainElement) + " : " + trainElement.toString());
            sb.append("\n");
        }
        return sb.toString();
    }
}
