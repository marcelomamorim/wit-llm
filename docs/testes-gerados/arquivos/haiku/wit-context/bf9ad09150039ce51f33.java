/*
 * @test
 * @summary Verify Spliterators.spliterator(double[], int) tryAdvance(DoubleConsumer) null check and element traversal
 * @run main SpliteratorsWitupTest
 */

import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.DoubleConsumer;

public class SpliteratorsWitupTest {

    public static void main(String[] args) {
        testNullActionThrowsNullPointerException();
        testAdvancesElementsInOrder();
        testEmptyArrayReturnsFalseWithoutInvokingAction();
    }

    static void testNullActionThrowsNullPointerException() {
        Spliterator.OfDouble s = Spliterators.spliterator(new double[] {1.0}, 0);
        boolean thrown = false;
        try {
            s.tryAdvance((DoubleConsumer) null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "expected NullPointerException for null action");

        double[] out = new double[1];
        int[] n = new int[1];
        DoubleConsumer c = v -> out[n[0]++] = v;
        check(s.tryAdvance(c), "element should remain available after rejected null action");
        check(n[0] == 1 && out[0] == 1.0, "unexpected element after null action rejection");
    }

    static void testAdvancesElementsInOrder() {
        Spliterator.OfDouble s = Spliterators.spliterator(new double[] {1.5, 2.5}, 0);
        double[] out = new double[2];
        int[] n = new int[1];
        DoubleConsumer c = v -> out[n[0]++] = v;
        check(s.tryAdvance(c), "first tryAdvance should return true");
        check(s.tryAdvance(c), "second tryAdvance should return true");
        check(!s.tryAdvance(c), "third tryAdvance should return false");
        check(n[0] == 2, "action should be invoked exactly twice");
        check(out[0] == 1.5, "first element mismatch");
        check(out[1] == 2.5, "second element mismatch");
    }

    static void testEmptyArrayReturnsFalseWithoutInvokingAction() {
        Spliterator.OfDouble s = Spliterators.spliterator(new double[0], 0);
        int[] n = new int[1];
        DoubleConsumer c = v -> n[0]++;
        check(!s.tryAdvance(c), "tryAdvance on empty array should return false");
        check(n[0] == 0, "action must not be invoked for empty array");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
