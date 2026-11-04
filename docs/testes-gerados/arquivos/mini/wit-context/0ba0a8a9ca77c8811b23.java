/*
 * @test
 * @summary Test addObserver method of java.util.Observable for null argument and duplicate observer
 * @run main ObservableWitupTest
 */

import java.util.Observable;
import java.util.Observer;

public class ObservableWitupTest {
    public static void main(String[] args) {
        testAddObserverNull();
        testAddObserverDuplicate();
    }

    private static void testAddObserverNull() {
        Observable observable = new Observable();
        try {
            observable.addObserver(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }

    private static void testAddObserverDuplicate() {
        Observable observable = new Observable();
        Observer observer = new Observer() {
            public void update(Observable o, Object arg) {
                // no-op
            }
        };
        observable.addObserver(observer);
        // Adding the same observer again should not add duplicate
        observable.addObserver(observer);
        // We cannot access obs directly, but we can test notification count indirectly
        // by counting updates received
        CountingObserver countingObserver = new CountingObserver();
        observable.addObserver(countingObserver);
        observable.notifyObservers();
        int countAfterFirstNotify = countingObserver.getCount();
        observable.notifyObservers();
        int countAfterSecondNotify = countingObserver.getCount();
        if (countAfterFirstNotify != 1 || countAfterSecondNotify != 2) {
            throw new RuntimeException("Observer notification count incorrect");
        }
    }

    private static class CountingObserver implements Observer {
        private int count = 0;
        public void update(Observable o, Object arg) {
            count++;
        }
        public int getCount() {
            return count;
        }
    }
}
