/* @test    @summary Test for SortedOps.begin method handling large size values    @run main SortedOpsWitupTest */

import java.util.stream.SortedOps;

public class SortedOpsWitupTest {

    public static void main(String[] args) {
        testBeginWithLargeSize();
        testBeginWithZeroSize();
        testBeginWithNegativeSize();
    }

    private static void testBeginWithLargeSize() {
        SortedOps sortedOps = new SortedOps();
        long largeSize = (long) Integer.MAX_VALUE + 1L;
        try {
            // Invoca o método begin com tamanho maior que MAX_ARRAY_SIZE
            // Espera-se uma IllegalArgumentException
            sortedOps.getClass().getMethod("begin", long.class).invoke(sortedOps, largeSize);
            System.out.println("FAIL: Expected IllegalArgumentException for large size");
        } catch (java.lang.reflect.InvocationTargetException e) {
            if (e.getCause() instanceof IllegalArgumentException) {
                System.out.println("PASS: IllegalArgumentException thrown for large size");
            } else {
                System.out.println("FAIL: Unexpected exception: " + e.getCause());
            }
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception: " + e);
        }
    }

    private static void testBeginWithZeroSize() {
        SortedOps sortedOps = new SortedOps();
        try {
            // Invoca o método begin com tamanho zero
            sortedOps.getClass().getMethod("begin", long.class).invoke(sortedOps, 0L);
            System.out.println("PASS: No exception for zero size");
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception for zero size: " + e);
        }
    }

    private static void testBeginWithNegativeSize() {
        SortedOps sortedOps = new SortedOps();
        try {
            // Invoca o método begin com tamanho negativo
            sortedOps.getClass().getMethod("begin", long.class).invoke(sortedOps, -10L);
            System.out.println("PASS: No exception for negative size");
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception for negative size: " + e);
        }
    }
}
