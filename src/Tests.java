
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;

public class Tests {
    private Train train;
    private Locomotive locomotive;
    private Wagon wagon;

    @BeforeEach
    public void setUp() {
        locomotive = new Locomotive(100, 100, 100, 100, "Locomotive-1", UUID.randomUUID(), new Date(), "test", DriveType.DIESEL, 100);
        wagon = new Wagon(100, 100, 100, 100, "Wagon-1", UUID.randomUUID(), new Date(), "test", WagonType.PASSENGER);
        train = new Train(locomotive);
    }

    @Test
    public void testAddTrainElementAlreadyInTrain() {
        assertThrows(IllegalArgumentException.class, () -> train.addTrainElement(locomotive));
    }

    @Test
    public void testAddTrainElement() {
        assertAll(
            () -> assertDoesNotThrow(() -> train.addTrainElement(wagon)),
            () -> assertEquals(2, train.getTrain().size()),
            () -> assertEquals(locomotive, train.getTrain().get(0)),
            () -> assertEquals(wagon, train.getTrain().get(1))
        );
    }

    @Test
    public void testAddTrainElementAtPositionOutOfBounds(){
        assertThrows(IllegalArgumentException.class, () -> train.addTrainElement(wagon, 3));
    }

    @Test
    public void testAddTrainElementAtPositionTrainFirst(){
        assertThrows(IllegalArgumentException.class, () -> train.addTrainElement(wagon, 0));
    }

    @Test
    public void testAddTrainElementAtPosition(){
        assertAll(
                () -> assertDoesNotThrow(() -> train.addTrainElement(wagon, 1)),
                () -> assertEquals(2, train.getTrain().size()),
                () -> assertEquals(locomotive, train.getTrain().get(0)),
                () -> assertEquals(wagon, train.getTrain().get(1))
        );
    }

    @Test
    public void testAddTrainElementAfter(){
        assertAll(
                // TrainElement already in train
                () -> assertThrows(IllegalArgumentException.class, () -> train.addTrainElementAfter(locomotive, locomotive)),
                () -> assertDoesNotThrow(() -> train.addTrainElementAfter(locomotive, wagon)),
                () -> assertEquals(2, train.getTrain().size()),
                () -> assertEquals(locomotive, train.getTrain().get(0)),
                () -> assertEquals(wagon, train.getTrain().get(1))
        );
    }

    @Test
    public void testAddTrainElementBeforeTrainFirst(){

        assertThrows(InvalidTrainException.class, () -> train.addTrainElementBefore(locomotive, wagon));
    }

    @Test
    public void testAddTrainElementBefore(){
        train.addTrainElement(wagon);
        TrainElement wagon2 = new Wagon (100, 100, 100, 100, "Wagon-2", UUID.randomUUID(), new Date(), "test", WagonType.GOODS);
        assertAll(
                () -> assertDoesNotThrow(() -> train.addTrainElementBefore(wagon, wagon2)),
                () -> assertEquals(3, train.getTrain().size()),
                () -> assertEquals(locomotive, train.getTrain().get(0)),
                () -> assertEquals(wagon2, train.getTrain().get(1)),
                () -> assertEquals(wagon, train.getTrain().get(2))
        );
    }

    @Test
    public void removeTrainElement(){
        train.addTrainElement(wagon);
        assertAll(
                () -> assertDoesNotThrow(() -> train.removeTrainElement(wagon)),
                () -> assertEquals(1, train.getTrain().size()),
                () -> assertEquals(locomotive, train.getTrain().get(0))
        );
    }

    @Test
    public void trainOrder() {
        TrainElement wagon2 = new Wagon (100, 100, 100, 100, "Wagon-2", UUID.randomUUID(), new Date(), "test", WagonType.GOODS);
        TrainElement locomotive2 = new Locomotive(100, 100, 100, 100, "Locomotive-2", UUID.randomUUID(), new Date(), "test", DriveType.DIESEL, 100);
        train.addTrainElement(wagon);
        train.addTrainElement(wagon2);
        train.addTrainElementBefore(wagon, locomotive2);

        assertAll(
                () -> assertEquals(4, train.getTrain().size()),
                () -> assertEquals(locomotive, train.getTrain().get(0)),
                () -> assertEquals(locomotive2, train.getTrain().get(1)),
                () -> assertEquals(wagon, train.getTrain().get(2)),
                () -> assertEquals(wagon2, train.getTrain().get(3))
        );
    }

    @Test
    public void getEmptyWeight(){
        train.addTrainElement(wagon);
        assertEquals(locomotive.getEmptyWeight() + wagon.getEmptyWeight(), train.getEmptyWeight());
    }

    @Test
    public void getMaxPassengerCount(){
        train.addTrainElement(wagon);
        assertEquals(locomotive.getMaxPassengerCount() + wagon.getMaxPassengerCount(), train.getMaxPassengerCount());
    }

    @Test
    public void getMaxGoodsWeight(){
        train.addTrainElement(wagon);
        assertEquals(locomotive.getMaxGoodsWeight() + wagon.getMaxGoodsWeight(), train.getMaxGoodsWeight());
    }

    @Test
    public void getMaxLoadWeight(){
        train.addTrainElement(wagon);
        int maxPassengerCount = locomotive.getMaxPassengerCount() + wagon.getMaxPassengerCount();
        int maxGoodsWeight = locomotive.getMaxGoodsWeight() + wagon.getMaxGoodsWeight();
        assertEquals(maxPassengerCount*75+maxGoodsWeight, train.getMaxLoadWeight());
    }

    @Test
    public void getMaxWeight(){
        train.addTrainElement(wagon);
        int maxPassengerCount = locomotive.getMaxPassengerCount() + wagon.getMaxPassengerCount();
        int maxGoodsWeight = locomotive.getMaxGoodsWeight() + wagon.getMaxGoodsWeight();
        int maxLoadWeight = maxPassengerCount*75+maxGoodsWeight;
        int maxEmptyWeight = locomotive.getEmptyWeight() + wagon.getEmptyWeight();
        assertEquals(maxLoadWeight + maxEmptyWeight, train.getMaxWeight());
    }

    @Test
    public void getTrainLength(){
        train.addTrainElement(wagon);
        assertEquals(locomotive.getLength() + wagon.getLength(), train.getLength());
    }

    @Test
    public void isTrainOperational(){
        train.addTrainElement(wagon);
        int maxPassengerCount = locomotive.getMaxPassengerCount() + wagon.getMaxPassengerCount();
        int maxGoodsWeight = locomotive.getMaxGoodsWeight() + wagon.getMaxGoodsWeight();
        int maxLoadWeight = maxPassengerCount*75+maxGoodsWeight;
        int maxEmptyWeight = locomotive.getEmptyWeight() + wagon.getEmptyWeight();
        int maxWeight = maxLoadWeight + maxEmptyWeight;
        int power = locomotive.getPower();

        assertEquals(power >= maxWeight, train.isOperational());
        assertFalse(train.isOperational());

        Locomotive locomotive2 = new Locomotive(100, 100, 100, 100, "Locomotive-2", UUID.randomUUID(), new Date(), "test", DriveType.DIESEL, 100000);
        train.addTrainElementBefore(wagon, locomotive2);
        assertTrue(train.isOperational());
    }

    @Test
    public void getRequiredNumberOfConductors(){
        Locomotive locomotive2 = new Locomotive(100, 100, 1, 100, "Locomotive-2", UUID.randomUUID(), new Date(), "test", DriveType.DIESEL, 100000);
        Train train2 = new Train(locomotive2);
        assertEquals(1, train2.getRequiredNumberOfConductors());

        train2.addTrainElement(wagon);
        assertEquals(2, train2.getRequiredNumberOfConductors());
    }

}

