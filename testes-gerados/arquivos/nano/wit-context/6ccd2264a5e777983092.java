/* @test    @summary Testa o construtor CyclicBarrier(int parties) com entradas válidas e inválidas    @run main CyclicBarrierWitupTest */

import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierWitupTest {

    public static void main(String[] args) {
        testValidParties();
        testZeroParties();
        testNegativeParties();
    }

    static void testValidParties() {
        try {
            CyclicBarrier cb = new CyclicBarrier(3);
            System.out.println("testValidParties: PASSED");
        } catch (Exception e) {
            System.out.println("testValidParties: FAILED - " + e);
        }
    }

    static void testZeroParties() {
        try {
            CyclicBarrier cb = new CyclicBarrier(0);
            System.out.println("testZeroParties: FAILED - Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            System.out.println("testZeroParties: PASSED");
        } catch (Exception e) {
            System.out.println("testZeroParties: FAILED - Unexpected exception: " + e);
        }
    }

    static void testNegativeParties() {
        try {
            CyclicBarrier cb = new CyclicBarrier(-1);
            System.out.println("testNegativeParties: FAILED - Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            System.out.println("testNegativeParties: PASSED");
        } catch (Exception e) {
            System.out.println("testNegativeParties: FAILED - Unexpected exception: " + e);
        }
    }
}
