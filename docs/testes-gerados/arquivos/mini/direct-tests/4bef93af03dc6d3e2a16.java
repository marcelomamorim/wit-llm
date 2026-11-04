/*
 * @test
 * @summary Test ThreadLocal.withInitial factory method
 * @run main ThreadLocalWitupTest
 */

import java.lang.ThreadLocal;
import java.util.function.Supplier;

public class ThreadLocalWitupTest {
    public static void main(String[] args) {
        testWithInitialReturnsNonNullThreadLocal();
        testWithInitialInitializesValueCorrectly();
    }

    private static void testWithInitialReturnsNonNullThreadLocal() {
        Supplier<String> supplier = new Supplier<String>() {
            public String get() {
                return "test";
            }
        };
        ThreadLocal<String> tl = ThreadLocal.withInitial(supplier);
        if (tl == null) {
            throw new AssertionError("ThreadLocal.withInitial returned null");
        }
    }

    private static void testWithInitialInitializesValueCorrectly() {
        final String expected = "hello";
        Supplier<String> supplier = new Supplier<String>() {
            public String get() {
                return expected;
            }
        };
        ThreadLocal<String> tl = ThreadLocal.withInitial(supplier);
        String value = tl.get();
        if (!expected.equals(value)) {
            throw new AssertionError("Expected value '" + expected + "' but got '" + value + "'");
        }
    }
}
