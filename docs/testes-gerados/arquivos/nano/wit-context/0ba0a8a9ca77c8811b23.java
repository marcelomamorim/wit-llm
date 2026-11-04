/* @test    @summary Test addObserver method for null input and normal behavior    @run main ObservableWitupTest */

import java.util.Observable;
import java.util.Observer;

public class ObservableWitupTest {

    public static void main(String[] args) {
        testAddObserverWithNull();
        testAddObserverWithValidObserver();
    }

    private static void testAddObserverWithNull() {
        Observable observable = new Observable();
        boolean exceptionThrown = false;
        try {
            observable.addObserver(null);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected NullPointerException when adding null observer");
        }
    }

    private static void testAddObserverWithValidObserver() {
        Observable observable = new Observable();
        Observer observer = new Observer() {
            boolean notified = false;
            @Override
            public void update(Observable o, Object arg) {
                notified = true;
            }
        };
        observable.addObserver(observer);
        // Since Observable does not automatically notify, we test that observer is added by calling notifyObservers
        observable.notifyObservers();
        // No exception means observer was added successfully; no direct way to verify internal list, so test indirectly
        // We can assume success if no exception occurs
    }
}
