/*
 * @test
 * @summary Test System.log(Level, Supplier<String>) method for correct behavior.
 * @run main SystemWitupTest
 */
import java.lang.System;
import java.util.Objects;

public class SystemWitupTest {
    public static void main(String[] args) {
        testLogWithNonNullSupplier();
        testLogWithNullSupplier();
        testLogWithLoggableLevel();
        testLogWithNonLoggableLevel();
    }

    static void testLogWithNonNullSupplier() {
        final boolean[] called = {false};
        System.log(Level.INFO, () -> {
            called[0] = true;
            return "Test message";
        });
        assert called[0] : "Supplier should be invoked for non-null supplier";
    }

    static void testLogWithNullSupplier() {
        try {
            System.log(Level.INFO, null);
            assert false : "NullPointerException expected when supplier is null";
        } catch (NullPointerException e) {
            // expected
        }
    }

    static void testLogWithLoggableLevel() {
        final boolean[] invoked = {false};
        System.log(Level.WARNING, () -> {
            invoked[0] = true;
            return "Warning message";
        });
        assert invoked[0] : "Supplier should be invoked when level is loggable";
    }

    static void testLogWithNonLoggableLevel() {
        final boolean[] invoked = {false};
        // Assuming Level.OFF is not loggable
        System.log(Level.OFF, () -> {
            invoked[0] = true;
            return "This should not be logged";
        });
        // Since the method does not return a value, we check that supplier is not invoked
        assert !invoked[0] : "Supplier should not be invoked when level is not loggable";
    }

    // Minimal stub for Level class to compile and run tests
    public static class Level {
        public static final Level INFO = new Level("INFO", 1);
        public static final Level WARNING = new Level("WARNING", 2);
        public static final Level OFF = new Level("OFF", Integer.MAX_VALUE);
        private final String name;
        private final int value;
        private Level(String name, int value) {
            this.name = name;
            this.value = value;
        }
        public boolean intValue() {
            return this.value;
        }
    }
}
