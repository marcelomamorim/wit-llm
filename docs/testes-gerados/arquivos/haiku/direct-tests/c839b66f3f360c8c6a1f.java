/*
 * @test
 * @summary Verifies LinkedBlockingDeque.remainingCapacity() across construction, insertion, removal and clear
 * @run main LinkedBlockingDequeWitupTest
 */

import java.util.Arrays;
import java.util.concurrent.LinkedBlockingDeque;

public class LinkedBlockingDequeWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) throws Exception {
        LinkedBlockingDeque<String> bounded = new LinkedBlockingDeque<>(3);
        check(bounded.remainingCapacity() == 3, "initial remainingCapacity should equal capacity");

        check(bounded.offer("a"), "offer should succeed when space is available");
        check(bounded.remainingCapacity() == 2, "remainingCapacity should decrease after offer");

        bounded.putLast("b");
        check(bounded.remainingCapacity() == 1, "remainingCapacity should decrease after putLast");

        bounded.addFirst("c");
        check(bounded.remainingCapacity() == 0, "remainingCapacity should be zero when full");

        check(!bounded.offerLast("x"), "offerLast should return false when full");
        check(bounded.remainingCapacity() == 0, "remainingCapacity should stay zero after rejected offer");

        bounded.pollFirst();
        check(bounded.remainingCapacity() == 1, "remainingCapacity should increase after pollFirst");

        bounded.takeFirst();
        check(bounded.remainingCapacity() == 2, "remainingCapacity should increase after takeFirst");

        bounded.clear();
        check(bounded.remainingCapacity() == 3, "remainingCapacity should be restored after clear");

        LinkedBlockingDeque<String> unbounded = new LinkedBlockingDeque<>();
        check(unbounded.remainingCapacity() == Integer.MAX_VALUE,
              "default constructor should report Integer.MAX_VALUE remaining capacity");
        unbounded.offer("z");
        check(unbounded.remainingCapacity() == Integer.MAX_VALUE - 1,
              "remainingCapacity should decrease by one after one insertion");

        LinkedBlockingDeque<String> fromCollection =
            new LinkedBlockingDeque<>(Arrays.asList("x", "y"));
        check(fromCollection.remainingCapacity() == Integer.MAX_VALUE - 2,
              "collection constructor should count existing elements against capacity");
    }
}
