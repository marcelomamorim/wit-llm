/*
 * @test
 * @summary Tests argument validation of ValueRange.of(long, long, long, long) using only public API
 * @run main ValueRangeWitupTest
 */

import java.time.temporal.ValueRange;

public class ValueRangeWitupTest {

    public static void main(String[] args) {
        // Valid configurations: equal bounds and ordered bounds must not throw
        ValueRange.of(1, 1, 5, 5);
        ValueRange.of(1, 2, 3, 4);
        ValueRange.of(1, 1, 1, 1);
        ValueRange.of(0, 0, 0, 0);
        ValueRange.of(-5, -3, -2, 7);

        // minSmallest > minLargest
        expectIllegalArgument(new Runnable() {
            public void run() {
                ValueRange.of(2, 1, 3, 4);
            }
        }, "Smallest minimum value must be less than largest minimum value");

        // maxSmallest > maxLargest
        expectIllegalArgument(new Runnable() {
            public void run() {
                ValueRange.of(1, 2, 5, 4);
            }
        }, "Smallest maximum value must be less than largest maximum value");

        // minLargest > maxLargest (first two checks pass)
        expectIllegalArgument(new Runnable() {
            public void run() {
                ValueRange.of(1, 5, 2, 4);
            }
        }, "Minimum value must be less than maximum value");

        // minLargest > maxLargest with the other bounds in order
        expectIllegalArgument(new Runnable() {
            public void run() {
                ValueRange.of(3, 4, 1, 2);
            }
        }, "Minimum value must be less than maximum value");
    }

    private static void expectIllegalArgument(Runnable action, String expectedMessage) {
        try {
            action.run();
        } catch (IllegalArgumentException e) {
            if (!expectedMessage.equals(e.getMessage())) {
                throw new RuntimeException("Unexpected message: " + e.getMessage(), e);
            }
            return;
        }
        throw new RuntimeException("Expected IllegalArgumentException was not thrown");
    }
}
