/* @test    @summary Test for Random.tryAdvance(LongConsumer) method to verify behavior with null consumer and normal execution    @run main RandomWitupTest */

import java.util.Random;
import java.util.function.LongConsumer;

public class RandomWitupTest {

    public static void main(String[] args) {
        testTryAdvanceWithNullConsumer();
        testTryAdvanceWithValidConsumer();
    }

    private static void testTryAdvanceWithNullConsumer() {
        Random rand = new Random();
        try {
            rand.tryAdvance(null);
            System.out.println("FAIL: Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown as expected");
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception: " + t);
        }
    }

    private static void testTryAdvanceWithValidConsumer() {
        Random rand = new Random(12345L);
        LongConsumer consumer = new LongConsumer() {
            boolean called = false;
            long value = 0;
            @Override
            public void accept(long t) {
                called = true;
                value = t;
            }
        };
        boolean result = rand.tryAdvance(consumer);
        if (result) {
            System.out.println("PASS: tryAdvance returned true");
        } else {
            System.out.println("FAIL: tryAdvance returned false unexpectedly");
        }
    }
}
