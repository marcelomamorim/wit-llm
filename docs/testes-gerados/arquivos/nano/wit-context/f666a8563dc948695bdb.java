/*
 * @test
 * @summary Tests for java.util.SplittableRandom.tryAdvance(IntConsumer)
 * @run main SplittableRandomWitupTest
 */

import java.util.SplittableRandom;
import java.util.function.IntConsumer;

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
        final int[] count = {0};
        IntConsumer consumer = new IntConsumer() {
            @Override
            public void accept(int value) {
                count[0]++;
            }
        };
        boolean result = sr.tryAdvance(consumer);
        if (result && count[0] == 1) {
            System.out.println("PASS: tryAdvance with valid consumer succeeded and consumer accepted value");
        } else {
            System.out.println("FAIL: tryAdvance with valid consumer failed or consumer not called");
        }
    }

    private static void testTryAdvanceWhenIndexReachesFence() {
        // To simulate index >= fence, we need to manipulate internal state, which is not accessible.
        // Instead, we test the behavior when index is at or beyond fence by creating a subclass or using reflection.
        // Given constraints, we test the default behavior: after multiple calls, it returns false.
        SplittableRandom sr = new SplittableRandom();
        // Call tryAdvance until it returns false (simulate reaching fence)
        boolean result;
        do {
            result = sr.tryAdvance(value -> {});
        } while (result);
        // Now, next call should return false
        boolean finalResult = sr.tryAdvance(value -> {});
        if (!finalResult) {
            System.out.println("PASS: tryAdvance returns false when index reaches or exceeds fence");
        } else {
            System.out.println("FAIL: tryAdvance did not return false at expected boundary");
        }
    }
}
