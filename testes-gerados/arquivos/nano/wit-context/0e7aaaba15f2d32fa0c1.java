/*
 * @test
 * @summary Test for System.log(Level, Supplier<String>) method, focusing on null input handling.
 * @run main SystemWitupTest
 */

import java.lang.System;
import java.lang.System.Logger.Level;
import java.util.function.Supplier;

public class SystemWitupTest {

    public static void main(String[] args) {
        testLogWithNullSupplier();
        testLogWithValidLevelAndSupplier();
    }

    private static void testLogWithNullSupplier() {
        Level level = Level.INFO;
        Supplier<String> nullSupplier = null;
        boolean exceptionThrown = false;
        try {
            System.Logger.log(level, nullSupplier);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            System.out.println("FAIL: Expected NullPointerException when msgSupplier is null");
        } else {
            System.out.println("PASS: NullPointerException thrown as expected for null msgSupplier");
        }
    }

    private static void testLogWithValidLevelAndSupplier() {
        Level level = Level.WARNING;
        Supplier<String> supplier = () -> "Test message";
        try {
            System.Logger.log(level, supplier);
            System.out.println("PASS: log method executed without exception with valid supplier");
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception: " + e);
        }
    }
}
