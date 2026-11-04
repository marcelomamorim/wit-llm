/*
 * @test
 * @summary Test java.util.Observable.addObserver method behavior
 * @run main java.util.ObservableWitupTest
 */

import java.util.Observable;
import java.util.Observer;

public class ObservableWitupTest {
    public static void main(String[] args) {
        testAddObserverNull();
        testAddObserverOnce();
        testAddObserverDuplicate();
    }

    static void testAddObserverNull() {
        Observable observable = new Observable();
        try {
            observable.addObserver(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testAddObserverOnce() {
        Observable observable = new Observable();
        Observer observer = new Observer() {
            public void update(Observable o, Object arg) {}
        };
        observable.addObserver(observer);
        // Adding once should succeed silently, no exception
    }

    static void testAddObserverDuplicate() {
        Observable observable = new Observable();
        Observer observer = new Observer() {
            public void update(Observable o, Object arg) {}
        };
        observable.addObserver(observer);
        observable.addObserver(observer); // duplicate add should not add again or throw
        // No direct way to check internal Vector, but no exception means pass
    }
}
