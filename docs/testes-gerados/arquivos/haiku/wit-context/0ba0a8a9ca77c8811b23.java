/*
 * @test
 * @summary Verify Observable.addObserver rejects null and does not register duplicate observers
 * @run main ObservableWitupTest
 */

import java.util.Observable;
import java.util.Observer;

public class ObservableWitupTest {

    static class Subject extends Observable {
        void change() {
            setChanged();
        }
    }

    static class CountingObserver implements Observer {
        int count = 0;
        public void update(Observable o, Object arg) {
            count++;
        }
    }

    public static void main(String[] args) {
        testNullObserverThrowsNPE();
        testDuplicateObserverNotifiedOnce();
    }

    static void testNullObserverThrowsNPE() {
        Subject s = new Subject();
        boolean thrown = false;
        try {
            s.addObserver(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("addObserver(null) did not throw NullPointerException");
        }
    }

    static void testDuplicateObserverNotifiedOnce() {
        Subject s = new Subject();
        CountingObserver a = new CountingObserver();
        CountingObserver b = new CountingObserver();
        s.addObserver(a);
        s.addObserver(a);
        s.addObserver(b);
        s.change();
        s.notifyObservers("x");
        if (a.count != 1) {
            throw new RuntimeException("duplicate observer notified " + a.count + " times, expected 1");
        }
        if (b.count != 1) {
            throw new RuntimeException("second observer notified " + b.count + " times, expected 1");
        }
    }
}
