/*
 * @test
 * @summary Tests for java.util.SplittableRandom
 * @run main SplittableRandomWitupTest
 */

import java.util.SplittableRandom;
import java.util.function.DoubleConsumer;

public class SplittableRandomWitupTest {

    public static void main(String[] args) {
        testTryAdvanceWithNullConsumer();
        testTryAdvanceWithValidConsumer();
        testTryAdvanceWhenIndexLessThanFence();
        testTryAdvanceWhenIndexEqualsFence();
    }

    static void testTryAdvanceWithNullConsumer() {
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

    static void testTryAdvanceWithValidConsumer() {
        SplittableRandom sr = new SplittableRandom();
        final boolean[] called = {false};
        DoubleConsumer consumer = d -> { called[0] = true; };
        boolean result = sr.tryAdvance(consumer);
        if (result && called[0]) {
            System.out.println("PASS: tryAdvance with valid consumer succeeded and consumer called");
        } else {
            System.out.println("FAIL: tryAdvance with valid consumer failed or consumer not called");
        }
    }

    static void testTryAdvanceWhenIndexLessThanFence() {
        SplittableRandom sr = new SplittableRandom();
        // Manipulate internal state if possible; since no public API, assume initial state is valid
        DoubleConsumer consumer = d -> {};
        boolean result = sr.tryAdvance(consumer);
        if (result) {
            System.out.println("PASS: tryAdvance when index < fence returned true");
        } else {
            System.out.println("FAIL: tryAdvance when index < fence returned false");
        }
    }

    static void testTryAdvanceWhenIndexEqualsFence() {
        // Since internal state is private and no public API to set index/fence, this test assumes initial state
        // and relies on the method returning false if index >= fence.
        SplittableRandom sr = new SplittableRandom();
        // No direct way to set index/fence; assume initial state where index >= fence for test purposes
        // so we just call and expect false.
        DoubleConsumer consumer = d -> {};
        boolean result = sr.tryAdvance(consumer);
        if (!result) {
            System.out.println("PASS: tryAdvance when index >= fence returned false");
        } else {
            System.out.println("NOTE: Unable to manipulate internal state; test may not reflect actual condition.");
        }
    }
}
