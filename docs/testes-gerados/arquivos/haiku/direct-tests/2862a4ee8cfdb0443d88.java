/*
 * @test
 * @summary Verifies DoubleStream.generate spliterator tryAdvance behavior: supplies a value, advances in order, and rejects a null action before invoking the supplier.
 * @run main StreamSpliteratorsWitupTest
 */

import java.util.Spliterator;
import java.util.stream.DoubleStream;

public class StreamSpliteratorsWitupTest {

    public static void main(String[] args) {
        testSingleAdvanceDeliversSuppliedValue();
        testSequentialAdvancesFollowSupplierOrder();
        testNullActionThrowsAndDoesNotInvokeSupplier();
        System.out.println("All StreamSpliteratorsWitupTest checks passed");
    }

    private static void testSingleAdvanceDeliversSuppliedValue() {
        int[] calls = {0};
        Spliterator.OfDouble sp = DoubleStream.generate(() -> {
            calls[0]++;
            return 7.25;
        }).spliterator();

        double[] got = {Double.NaN};
        boolean result = sp.tryAdvance(d -> got[0] = d);

        if (!result) {
            throw new RuntimeException("tryAdvance should return true");
        }
        if (got[0] != 7.25) {
            throw new RuntimeException("Expected 7.25 but got " + got[0]);
        }
        if (calls[0] != 1) {
            throw new RuntimeException("Supplier should be invoked once, was " + calls[0]);
        }
    }

    private static void testSequentialAdvancesFollowSupplierOrder() {
        double[] next = {1.0};
        Spliterator.OfDouble sp = DoubleStream.generate(() -> next[0]++).spliterator();

        double[] seen = new double[3];
        for (int i = 0; i < 3; i++) {
            final int idx = i;
            boolean result = sp.tryAdvance(d -> seen[idx] = d);
            if (!result) {
                throw new RuntimeException("tryAdvance should return true on call " + i);
            }
        }
        if (seen[0] != 1.0 || seen[1] != 2.0 || seen[2] != 3.0) {
            throw new RuntimeException("Unexpected order: " + seen[0] + "," + seen[1] + "," + seen[2]);
        }
    }

    private static void testNullActionThrowsAndDoesNotInvokeSupplier() {
        int[] calls = {0};
        Spliterator.OfDouble sp = DoubleStream.generate(() -> {
            calls[0]++;
            return 0.0;
        }).spliterator();

        try {
            sp.tryAdvance((java.util.function.DoubleConsumer) null);
            throw new RuntimeException("Expected NullPointerException for null action");
        } catch (NullPointerException expected) {
            // expected: Objects.requireNonNull(action)
        }
        if (calls[0] != 0) {
            throw new RuntimeException("Supplier must not be invoked when action is null, calls=" + calls[0]);
        }
    }
}
