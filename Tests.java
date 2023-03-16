
// import junit 5

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

import java.util.*;


public class Tests {

    private static Locomotive l1;
    private static Locomotive l2;
    private static Locomotive l3;
    private static Wagon w1;
    private static Wagon w2;
    private static Wagon w3;

    // to make testing easier we create some objects before each test
    // also populate them with easy to use data and try to make them as realistic as possible
    @BeforeEach
    public void setup() {
        l1 = new Locomotive(80000, 20, 10000, DriveType.DIESEL, 100, 100, "type", "manufacturer", new Date());
        l2 = new Locomotive(80000, 20, 10000, DriveType.ELECTRIC, 100, 100, "type", "manufacturer", new Date());
        l3 = new Locomotive(80000, 20, 10000, DriveType.STEAM, 100, 100, "type", "manufacturer", new Date());
        w1 = new Wagon(40000, 16, WagonType.EATING, 50, 1000, "type", "manufacturer", new Date());
        w2 = new Wagon(40000, 16, WagonType.SLEEP, 50, 1000, "type", "manufacturer", new Date());
        w3 = new Wagon(40000, 16, WagonType.PASSENGER, 50, 1000, "type", "manufacturer", new Date());
    }

    @AfterEach
    public void tearDown() {
        l1 = null;
        l2 = null;
        l3 = null;
        w1 = null;
        w2 = null;
        w3 = null;
    }


    @Test
    public void minimalTrain() {
        Train train = new Train(l1);
        assertEquals(1, train.getLocomotives().size());
    }

    /**
     * Test if we can add locomotives and wagons to a train
     */
    @Test
    public void addWagonsAndLocomotives() {
        // create train with 1 locomotive
        Train train = new Train(l1);
        // add 2 locomotives
        train.addLocomotive(l2);
        train.addLocomotive(l3);
        // add 3 wagons
        train.addWagon(w1);
        train.addWagon(w2);
        train.addWagon(w3);

        // check if train has 3 locomotives and 3 wagons
        assertEquals(3, train.getLocomotives().size());
        assertEquals(3, train.getWagons().size());
    }


    /**
     * Test if we can add the same locomotive or wagon to a train twice
     */
    @Test
    public void addWagonLocomotiveTwice() {
        Train train1 = new Train(l1);
        train1.addWagon(w1);

        assertThrows(RuntimeException.class, () -> train1.addWagon(w1));
        assertThrows(RuntimeException.class, () -> train1.addLocomotive(l1));

    }

    /**
     * Test if we can add the same locomotive or wagon to two trains
     */
    @Test
    public void addWagonLocomotiveToTwoTrains() {
        Train train1 = new Train(l1);
        Train train2 = new Train(l2);

        train1.addWagon(w1);
        train2.addWagon(w2);

        assertThrows(RuntimeException.class, () -> train1.addWagon(w2));
        assertThrows(RuntimeException.class, () -> train1.addLocomotive(l2));
        assertThrows(RuntimeException.class, () -> train2.addWagon(w1));
        assertThrows(RuntimeException.class, () -> train2.addLocomotive(l1));
    }

    /**
     * Test if we can remove locomotives and wagons from a train correctly
     */
    @Test
    public void removeWagonLocomotive() {
        Train train = new Train(l1);
        train.addWagon(w1);
        train.addWagon(w2);
        train.addWagon(w3);
        train.addLocomotive(l2);

        System.out.println(train.getWagons());

        train.removeWagon(w1);
        train.removeLocomotive(l2);

        System.out.println(train.getWagons());


        assertEquals(2, train.getWagons().size());
        assertEquals(1, train.getLocomotives().size());
    }

    /**
     * Test if the order of locomotives is correct
     */
    @Test
    public void checkCorrectOrderOfLocomotives() {
        // create train with 1 locomotive
        Train train = new Train(l1);
        // add 2 locomotives
        train.addLocomotive(l2);
        train.addLocomotive(l3);

        // check if locomotives are in correct order
        assertEquals(l1, train.getLocomotives().get(2));
        assertEquals(l2, train.getLocomotives().get(1));
        assertEquals(l3, train.getLocomotives().get(0));
    }

    /**
     * Test if the order of wagons is correct
     */
    @Test
    public void checkCorrectOrderOfWagons() {
        // create train with 1 locomotive
        Train train = new Train(l1);
        // add 3 wagons
        train.addWagon(w1);
        train.addWagon(w2);
        train.addWagon(w3);

        // check if wagons are in correct order
        assertEquals(w1, train.getWagons().get(0));
        assertEquals(w2, train.getWagons().get(1));
        assertEquals(w3, train.getWagons().get(2));
    }

    /**
     * Test for cycles of wagons in the train
     */
    @Test
    public void checkForCycles() {
        // create train with 1 locomotive
        Train train = new Train(l1);
        // add 3 wagons
        train.addWagon(w1);
        train.addWagon(w2);
        train.addWagon(w3);

        // check if there are no cycles
        assertAll(
                () -> assertEquals(w2, train.getWagons().get(0).getNextWagon()),
                () -> assertEquals(w3, train.getWagons().get(1).getNextWagon()),
                () -> assertNull(train.getWagons().get(2).getNextWagon())
        );

        train.removeWagon(w2);

        assertAll(
                () -> assertEquals(w3, train.getWagons().get(0).getNextWagon()),
                () -> assertNull(train.getWagons().get(1).getNextWagon())
        );

        train.addWagon(w2);

        assertAll(
                () -> assertEquals(w3, train.getWagons().get(0).getNextWagon()),
                () -> assertEquals(w2, train.getWagons().get(1).getNextWagon()),
                () -> assertNull(train.getWagons().get(2).getNextWagon())
        );

    }

    /**
     * Test if the empty weight of a train is correct
     */
    @Test
    public void emptyWeightOfTrain() {
        // create train with 1 locomotive
        Train train = new Train(l1);
        // add 3 wagons
        train.addWagon(w1);
        train.addWagon(w2);
        train.addWagon(w3);

        int expected = l1.getWeight() + w1.getWeight() + w2.getWeight() + w3.getWeight();
        // check if empty weight is correct
        assertEquals(expected, train.getWeight());
    }

    /**
     * Test if the max Passenger count of a train is correct
     */
    @Test
    public void maxPassengerCountOfTrain() {
        // create train with 1 locomotive
        Train train = new Train(l1);
        // add 3 wagons
        train.addWagon(w1);
        train.addWagon(w2);
        train.addWagon(w3);

        int expected = l1.getPassengerCount() + w1.getPassengerCount() + w2.getPassengerCount() + w3.getPassengerCount();
        // check if max passenger count is correct
        assertEquals(expected, train.getMaxPassengerCount());
    }

    /**
     * Test if the max weight of goods, of a train is correct
     */
    @Test
    public void maxGoodsWeightOfTrain() {
        // create train with 1 locomotive
        Train train = new Train(l1);
        // add 3 wagons
        train.addWagon(w1);
        train.addWagon(w2);
        train.addWagon(w3);

        int expected = l1.getMaxGoodsWeight() + w1.getMaxGoodsWeight() + w2.getMaxGoodsWeight() + w3.getMaxGoodsWeight();
        // check if max goods weight is correct
        assertEquals(expected, train.getMaxGoodsWeight());
    }

    /**
     * Test if the max payload of a train is correct
     */
    @Test
    public void maxPayloadOfTrain() {
        // create train with 1 locomotive
        Train train = new Train(l1);
        // add 3 wagons
        train.addWagon(w1);
        train.addWagon(w2);
        train.addWagon(w3);

        int expected = (l1.getPassengerCount() + w1.getPassengerCount() + w2.getPassengerCount() + w3.getPassengerCount()) * 75;
        expected += l1.getMaxGoodsWeight() + w1.getMaxGoodsWeight() + w2.getMaxGoodsWeight() + w3.getMaxGoodsWeight();
        // check if max payload is correct
        assertEquals(expected, train.getMaxPayload());
    }

    /**
     * Test if the max weight of a train is correct
     */
    @Test
    public void maxWeightOfTrain() {
        // create train with 1 locomotive
        Train train = new Train(l1);
        // add 3 wagons
        train.addWagon(w1);
        train.addWagon(w2);
        train.addWagon(w3);


        int expected = (l1.getPassengerCount() + w1.getPassengerCount() + w2.getPassengerCount() + w3.getPassengerCount()) * 75;
        expected += l1.getMaxGoodsWeight() + w1.getMaxGoodsWeight() + w2.getMaxGoodsWeight() + w3.getMaxGoodsWeight();
        expected += l1.getWeight() + w1.getWeight() + w2.getWeight() + w3.getWeight();
        // check if max weight is correct
        assertEquals(expected, train.getMaxWeight());
    }

    /**
     * Test if the length of a train is correct
     */
    @Test
    public void lengthOfTrain() {
        Train train = new Train(l1);
        train.addLocomotive(l2);
        train.addLocomotive(l3);

        train.addWagon(w1);
        train.addWagon(w2);
        train.addWagon(w3);

        int expected = l1.getLength() + l2.getLength() + l3.getLength() + w1.getLength() + w2.getLength() + w3.getLength();
        // check if length is correct
        assertEquals(expected, train.getLength());
    }

    /**
     * Test if the train is drivable
     * The power of the locomotives must be greater than the max payload of the train
     */
    @Test
    public void trainDrivability() {
        Train train = new Train(l1);

        train.addWagon(w1);
        train.addWagon(w2);
        train.addWagon(w3);
        System.out.println(train.getMaxPayload());


        // check if train is drivable
        // one train can carry 3 wagons
        // train has 80t and can carry 10t additionally
        // because the max payload doesn't include the wagons weight it easily can carry 3 wagons
        assertTrue(train.checkTrainDrivable());

        // add 25 wagons to the train one locomotive can't drive this train
        for (int i = 0; i < 25; i++) {
            train.addWagon(new Wagon(40000, 16, WagonType.PASSENGER, 50, 1000, "type", "manufacturer", new Date()));
        }
        assertFalse(train.checkTrainDrivable());

        // add second locomotive
        train.addLocomotive(l2);
        assertTrue(train.checkTrainDrivable());
    }

    /**
     * Test if the cunductor count is correct
     */
    @Test
    public void addingConductorAutomatically() {
        Train train = new Train(l1);
        train.addLocomotive(l2);
        train.addLocomotive(l3);
        train.addWagon(w1);
        train.addWagon(w2);
        train.addWagon(w3);

        assertEquals(450 / 50, train.getConductorCount());
    }

    /**
     * Test if the max conductor count is correct
     */
    @Test
    public void maxConductorCount() {
        Train train = new Train(l1);
        train.addLocomotive(l2);
        train.addWagon(w1);
        train.addWagon(w2);
        assertEquals(300 / 50, train.getMaxNeedOfConductor());
    }
}
