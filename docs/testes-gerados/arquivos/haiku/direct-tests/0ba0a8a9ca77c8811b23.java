/*
 * @test
 * @summary White-box-free behavioral tests for Observable.addObserver: null rejection, duplicate suppression via equals, and notification delivery
 * @run main ObservableWitupTest
 */

import java.util.Observable;
import java.util.Observer;

@SuppressWarnings("deprecation")
public class ObservableWitupTest {

    static final class CountingObserver implements Observer {
        int updates;
        Object lastArg;

        public void update(Observable o, Object arg) {
            updates++;
            lastArg = arg;
        }
    }

    static final class EqualObserver implements Observer {
        final String id;

        EqualObserver(String id) {
            this.id = id;
        }

        public void update(Observable o, Object arg) {
        }

        public boolean equals(Object x) {
            if (!(x instanceof EqualObserver)) {
                return false;
            }
            EqualObserver other = (EqualObserver) x;
            return other.id.equals(id);
        }

        public int hashCode() {
            return id.hashCode();
        }
    }

    static final class TestObservable extends Observable {
        void change() {
            setChanged();
        }
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNullRejected() {
        Observable obs = new Observable();
        boolean thrown = false;
        try {
            obs.addObserver(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "addObserver(null) must throw NullPointerException");
        check(obs.countObservers() == 0, "null must not be added");
    }

    static void testSameInstanceAddedOnce() {
        Observable obs = new Observable();
        CountingObserver c = new CountingObserver();
        obs.addObserver(c);
        obs.addObserver(c);
        check(obs.countObservers() == 1, "duplicate instance must be ignored");
    }

    static void testDistinctObserversAdded() {
        Observable obs = new Observable();
        obs.addObserver(new CountingObserver());
        obs.addObserver(new CountingObserver());
        check(obs.countObservers() == 2, "distinct observers must both be added");
    }

    static void testEqualObserverIgnored() {
        Observable obs = new Observable();
        obs.addObserver(new EqualObserver("a"));
        obs.addObserver(new EqualObserver("a"));
        check(obs.countObservers() == 1, "observer equal to an existing one must not be added");
        obs.addObserver(new EqualObserver("b"));
        check(obs.countObservers() == 2, "non-equal observer must be added");
    }

    static void testNotificationReachesAddedObserver() {
        TestObservable obs = new TestObservable();
        CountingObserver c = new CountingObserver();
        obs.addObserver(c);
        obs.change();
        obs.notifyObservers("payload");
        check(c.updates == 1, "observer must be updated once");
        check("payload".equals(c.lastArg), "observer must receive the notification argument");
    }

    static void testDuplicateNotifiedOnce() {
        TestObservable obs = new TestObservable();
        CountingObserver c = new CountingObserver();
        obs.addObserver(c);
        obs.addObserver(c);
        obs.change();
        obs.notifyObservers(null);
        check(c.updates == 1, "duplicate registration must not cause double notification");
    }

    public static void main(String[] args) {
        testNullRejected();
        testSameInstanceAddedOnce();
        testDistinctObserversAdded();
        testEqualObserverIgnored();
        testNotificationReachesAddedObserver();
        testDuplicateNotifiedOnce();
    }
}
