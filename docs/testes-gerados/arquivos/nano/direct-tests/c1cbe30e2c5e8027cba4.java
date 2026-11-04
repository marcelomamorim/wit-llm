/* @test    @summary Test for SplittableRandom.forEachRemaining(IntConsumer) method    @run main SplittableRandomWitupTest */

import java.util.SplittableRandom;
import java.util.function.IntConsumer;

public class SplittableRandomWitupTest {

    public static void main(String[] args) {
        testForEachRemainingWithValidConsumer();
        testForEachRemainingWithNullConsumer();
    }

    static void testForEachRemainingWithValidConsumer() {
        SplittableRandom sr = new SplittableRandom(12345L);
        final int[] collected = new int[5];
        IntConsumer consumer = new IntConsumer() {
            int count = 0;
            @Override
            public void accept(int value) {
                if (count < collected.length) {
                    collected[count] = value;
                    count++;
                }
            }
        };
        sr.forEachRemaining(consumer);
        boolean allNonZero = true;
        for (int v : collected) {
            if (v == 0) {
                allNonZero = false;
                break;
            }
        }
        if (allNonZero) {
            System.out.println("PASS: forEachRemaining with valid consumer");
        } else {
            System.out.println("FAIL: forEachRemaining with valid consumer - some values are zero");
        }
    }

    static void testForEachRemainingWithNullConsumer() {
        SplittableRandom sr = new SplittableRandom();
        boolean exceptionThrown = false;
        try {
            sr.forEachRemaining(null);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        if (exceptionThrown) {
            System.out.println("PASS: forEachRemaining with null consumer throws NullPointerException");
        } else {
            System.out.println("FAIL: forEachRemaining with null consumer did not throw exception");
        }
    }
}
