/* @test    @summary Test addObserver method of java.util.Observable    @run main ObservableWitupTest */

import java.util.Observable;
import java.util.Observer;

public class ObservableWitupTest {

    public static void main(String[] args) {
        testAddObserverWithNull();
        testAddObserverWithValidObserver();
        testAddObserverMultipleTimes();
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
        TestObserver observer = new TestObserver();
        observable.addObserver(observer);
        if (!observable.observersContain(observer)) {
            throw new AssertionError("Observer was not added successfully");
        }
    }

    private static void testAddObserverMultipleTimes() {
        Observable observable = new Observable();
        TestObserver observer = new TestObserver();
        observable.addObserver(observer);
        observable.addObserver(observer); // Should not add duplicate
        int count = observable.countObserversOf(observer);
        if (count != 1) {
            throw new AssertionError("Observer added multiple times, count: " + count);
        }
    }

    private static class TestObserver implements Observer {
        @Override
        public void update(Observable o, Object arg) {
            // no-op
        }
    }
}

// Extending Observable to add helper methods for testing
class ExtendedObservable extends Observable {
    boolean observersContain(Observer o) {
        return super.obs.contains(o);
    }
    int countObserversOf(Observer o) {
        int count = 0;
        for (Observer observer : super.obs) {
            if (observer.equals(o)) {
                count++;
            }
        }
        return count;
    }
}
