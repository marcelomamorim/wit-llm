/*
 * @test
 * @summary Tests for java.util.SplittableRandom.tryAdvance(LongConsumer)
 * @run main SplittableRandomWitupTest
 */

import java.util.SplittableRandom;
import java.util.function.LongConsumer;

public class SplittableRandomWitupTest {

    public static void main(String[] args) {
        testTryAdvanceWithNullConsumer();
        testTryAdvanceWithValidConsumer();
        testTryAdvanceWhenIndexReachesFence();
    }

    private static void testTryAdvanceWithNullConsumer() {
        SplittableRandom sr = new SplittableRandom();
        try {
            sr.tryAdvance(null);
            System.out.println("FAIL: Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown as expected");
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception: " + t);
        }
    }

    private static void testTryAdvanceWithValidConsumer() {
        SplittableRandom sr = new SplittableRandom();
        final boolean[] called = {false};
        LongConsumer consumer = (long value) -> {
            called[0] = true;
            if (value == 0) {
                System.out.println("FAIL: Consumer received zero, unexpected in this test");
            }
        };
        boolean result = sr.tryAdvance(consumer);
        if (result && called[0]) {
            System.out.println("PASS: tryAdvance returned true and consumer was called");
        } else {
            System.out.println("FAIL: tryAdvance did not behave as expected");
        }
    }

    private static void testTryAdvanceWhenIndexReachesFence() {
        SplittableRandom sr = new SplittableRandom();
        // Manipulate internal state to simulate index >= fence
        // Since internal fields are not accessible, we simulate by calling tryAdvance until it returns false
        final boolean[] called = {false};
        LongConsumer consumer = (long value) -> {
            called[0] = true;
        };
        boolean result;
        do {
            result = sr.tryAdvance(consumer);
        } while (result);
        // Now, try once more; it should return false
        boolean finalResult = sr.tryAdvance(consumer);
        if (!finalResult) {
            System.out.println("PASS: tryAdvance returns false when index reaches fence");
        } else {
            System.out.println("FAIL: tryAdvance should return false when index reaches fence");
        }
    }
}
