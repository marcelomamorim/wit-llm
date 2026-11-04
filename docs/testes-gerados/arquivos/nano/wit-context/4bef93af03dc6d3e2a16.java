/*
 * @test
 * @summary Tests for java.lang.ThreadLocal
 * @run main ThreadLocalWitupTest
 */

import java.lang.ThreadLocal;
import java.util.function.Supplier;

public class ThreadLocalWitupTest {

    public static void main(String[] args) {
        testWithInitialNullSupplier();
        testWithInitialValidSupplier();
    }

    public static void testWithInitialNullSupplier() {
        try {
            ThreadLocal.withInitial(null);
            // If no exception, test fails
            System.err.println("Expected NullPointerException not thrown");
            System.exit(1);
        } catch (NullPointerException e) {
            // Expected exception
        }
    }

    public static void testWithInitialValidSupplier() {
        Supplier<Integer> supplier = () -> 42;
        ThreadLocal<Integer> tl = ThreadLocal.withInitial(supplier);
        Integer value = tl.get();
        if (value == null || value != 42) {
            System.err.println("Unexpected value from ThreadLocal.withInitial");
            System.exit(1);
        }
    }
}
