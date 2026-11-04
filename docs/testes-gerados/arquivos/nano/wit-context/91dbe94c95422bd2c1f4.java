/*
 * @test
 * @summary Testa o método forEachRemaining de ThreadLocalRandom para comportamento esperado.
 * @run main ThreadLocalRandomWitupTest
 */

import java.util.concurrent.ThreadLocalRandom;
import java.util.function.IntConsumer;

public class ThreadLocalRandomWitupTest {

    public static void main(String[] args) {
        testForEachRemainingWithNullConsumer();
        testForEachRemainingWithValidConsumer();
    }

    private static void testForEachRemainingWithNullConsumer() {
        try {
            ThreadLocalRandom.current().forEachRemaining(null);
            System.out.println("FAIL: Expected NullPointerException not thrown.");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown as expected.");
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception: " + t);
        }
    }

    private static void testForEachRemainingWithValidConsumer() {
        final int[] sum = {0};
        IntConsumer consumer = new IntConsumer() {
            @Override
            public void accept(int value) {
                sum[0] += value;
            }
        };
        try {
            ThreadLocalRandom.current().forEachRemaining(consumer);
            System.out.println("PASS: forEachRemaining executed without exceptions. Sum: " + sum[0]);
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception during valid consumer execution: " + t);
        }
    }
}
