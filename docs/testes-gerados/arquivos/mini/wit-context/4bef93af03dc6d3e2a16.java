/*
 * @test
 * @summary Test ThreadLocal.withInitial(Supplier) for null supplier and normal usage
 * @run main ThreadLocalWitupTest
 */

import java.lang.ThreadLocal;
import java.util.function.Supplier;

public class ThreadLocalWitupTest {
    public static void main(String[] args) {
        testWithInitialNullSupplier();
        testWithInitialNormalSupplier();
    }

    private static void testWithInitialNullSupplier() {
        Supplier<Object> nullSupplier = null;
        try {
            ThreadLocal.withInitial(nullSupplier);
            throw new Error("Expected NullPointerException for null supplier");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }

    private static void testWithInitialNormalSupplier() {
        Supplier<String> supplier = new Supplier<String>() {
            public String get() {
                return "testValue";
            }
        };
        ThreadLocal<String> tl = ThreadLocal.withInitial(supplier);
        String value = tl.get();
        if (!"testValue".equals(value)) {
            throw new Error("Expected value 'testValue' but got: " + value);
        }
    }
}
