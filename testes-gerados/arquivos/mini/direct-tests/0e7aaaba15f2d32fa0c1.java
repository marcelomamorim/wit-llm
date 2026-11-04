/*
 * @test
 * @summary Test java.lang.System.log(Level, Supplier<String>) method behavior
 * @run main SystemWitupTest
 */

import java.util.Objects;
import java.util.function.Supplier;
import java.util.logging.Level;
import java.lang.System.Logger;
import java.lang.System.Logger.Level as SysLevel;

public class SystemWitupTest {
    public static void main(String[] args) {
        testNullMsgSupplier();
        testNullLevel();
        testNotLoggableDoesNotCallSupplier();
        testLoggableCallsSupplier();
    }

    static void testNullMsgSupplier() {
        Logger logger = System.getLogger("TestLogger");
        try {
            logger.log(Level.INFO, null);
            throw new RuntimeException("Expected NullPointerException for null msgSupplier");
        } catch (NullPointerException npe) {
            // expected
        }
    }

    static void testNullLevel() {
        Logger logger = System.getLogger("TestLogger");
        try {
            logger.log(null, () -> "message");
            throw new RuntimeException("Expected NullPointerException for null level");
        } catch (NullPointerException npe) {
            // expected
        }
    }

    static void testNotLoggableDoesNotCallSupplier() {
        Logger logger = System.getLogger("TestLogger");
        final boolean[] called = {false};
        Supplier<String> supplier = new Supplier<String>() {
            public String get() {
                called[0] = true;
                return "should not be called";
            }
        };
        // Use a level that is not loggable (e.g. Level.OFF)
        logger.log(Level.OFF, supplier);
        if (called[0]) {
            throw new RuntimeException("Supplier.get() should not be called when level is not loggable");
        }
    }

    static void testLoggableCallsSupplier() {
        Logger logger = System.getLogger("TestLogger");
        final boolean[] called = {false};
        Supplier<String> supplier = new Supplier<String>() {
            public String get() {
                called[0] = true;
                return "log message";
            }
        };
        // Use a level that is loggable (e.g. Level.INFO)
        logger.log(Level.INFO, supplier);
        if (!called[0]) {
            throw new RuntimeException("Supplier.get() should be called when level is loggable");
        }
    }
}
