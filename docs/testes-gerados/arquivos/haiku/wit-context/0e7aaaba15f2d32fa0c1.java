/*
 * @test
 * @summary Witup-derived checks for the default System.Logger.log(Level, Supplier) method
 * @run main SystemWitupTest
 */

import java.util.ResourceBundle;
import java.util.function.Supplier;

public class SystemWitupTest {

    static final class RecordingLogger implements System.Logger {
        final boolean enabled;
        int isLoggableCalls;
        Level lastLoggableLevel;
        int logCalls;
        String lastMessage;
        Object[] lastParams;
        boolean lastParamsWasNull;

        RecordingLogger(boolean enabled) {
            this.enabled = enabled;
        }

        @Override
        public String getName() {
            return "recording";
        }

        @Override
        public boolean isLoggable(Level level) {
            isLoggableCalls++;
            lastLoggableLevel = level;
            return enabled;
        }

        @Override
        public void log(Level level, ResourceBundle bundle, String msg, Throwable thrown) {
        }

        @Override
        public void log(Level level, ResourceBundle bundle, String format, Object... params) {
            logCalls++;
            lastMessage = format;
            lastParams = params;
            lastParamsWasNull = (params == null);
        }
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNullSupplierThrowsNPE() {
        RecordingLogger logger = new RecordingLogger(true);
        boolean thrown = false;
        try {
            logger.log(System.Logger.Level.INFO, (Supplier<String>) null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "expected NullPointerException for null msgSupplier");
        check(logger.isLoggableCalls == 0, "isLoggable must not be called when supplier is null");
        check(logger.logCalls == 0, "no log call expected when supplier is null");
    }

    static void testNullLevelThrowsNPEWithoutSupplying() {
        RecordingLogger logger = new RecordingLogger(true);
        int[] supplierCalls = new int[1];
        Supplier<String> supplier = () -> {
            supplierCalls[0]++;
            return "msg";
        };
        boolean thrown = false;
        try {
            logger.log(null, supplier);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "expected NullPointerException for null level");
        check(supplierCalls[0] == 0, "supplier must not be invoked when level is null");
        check(logger.logCalls == 0, "no log call expected when level is null");
    }

    static void testNotLoggableSkipsSupplier() {
        RecordingLogger logger = new RecordingLogger(false);
        int[] supplierCalls = new int[1];
        Supplier<String> supplier = () -> {
            supplierCalls[0]++;
            return "msg";
        };
        logger.log(System.Logger.Level.DEBUG, supplier);
        check(logger.isLoggableCalls == 1, "isLoggable should be called once");
        check(logger.lastLoggableLevel == System.Logger.Level.DEBUG, "isLoggable received wrong level");
        check(supplierCalls[0] == 0, "supplier must not be invoked when not loggable");
        check(logger.logCalls == 0, "no log call expected when not loggable");
    }

    static void testLoggableInvokesSupplierAndLogs() {
        RecordingLogger logger = new RecordingLogger(true);
        logger.log(System.Logger.Level.WARNING, () -> "hello");
        check(logger.isLoggableCalls == 1, "isLoggable should be called once");
        check(logger.lastLoggableLevel == System.Logger.Level.WARNING, "isLoggable received wrong level");
        check(logger.logCalls == 1, "expected exactly one log call");
        check("hello".equals(logger.lastMessage), "log message should come from supplier");
        check(logger.lastParamsWasNull, "varargs params should be null for this overload");
    }

    public static void main(String[] args) {
        testNullSupplierThrowsNPE();
        testNullLevelThrowsNPEWithoutSupplying();
        testNotLoggableSkipsSupplier();
        testLoggableInvokesSupplierAndLogs();
    }
}
