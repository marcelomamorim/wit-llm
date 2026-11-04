/*
 * @test
 * @summary Behavioral tests for System.Logger.log(Level, Supplier<String>) default method
 * @run main SystemWitupTest
 */

import java.util.ResourceBundle;
import java.util.function.Supplier;

public class SystemWitupTest {

    static final class RecordingLogger implements System.Logger {
        boolean loggable;
        int logCalls;
        System.Logger.Level lastLevel;
        String lastMsg;
        ResourceBundle lastBundle;
        Object[] lastParams;
        boolean lastWasThrowableOverload;

        public String getName() {
            return "recording";
        }

        public boolean isLoggable(System.Logger.Level level) {
            return loggable;
        }

        public void log(System.Logger.Level level, ResourceBundle bundle,
                        String msg, Throwable thrown) {
            logCalls++;
            lastWasThrowableOverload = true;
            lastLevel = level;
            lastBundle = bundle;
            lastMsg = msg;
            lastParams = null;
        }

        public void log(System.Logger.Level level, ResourceBundle bundle,
                        String msg, Object... params) {
            logCalls++;
            lastWasThrowableOverload = false;
            lastLevel = level;
            lastBundle = bundle;
            lastMsg = msg;
            lastParams = params;
        }
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNullSupplierThrowsNPE() {
        RecordingLogger logger = new RecordingLogger();
        logger.loggable = true;
        boolean thrown = false;
        try {
            logger.log(System.Logger.Level.INFO, (Supplier<String>) null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "expected NullPointerException for null supplier");
        check(logger.logCalls == 0, "no log call expected when supplier is null");
    }

    static void testNullLevelThrowsNPE() {
        RecordingLogger logger = new RecordingLogger();
        logger.loggable = true;
        Supplier<String> s = () -> "msg";
        boolean thrown = false;
        try {
            logger.log((System.Logger.Level) null, s);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "expected NullPointerException for null level");
        check(logger.logCalls == 0, "no log call expected when level is null");
    }

    static void testNotLoggableDoesNotInvokeSupplier() {
        RecordingLogger logger = new RecordingLogger();
        logger.loggable = false;
        int[] invoked = {0};
        Supplier<String> s = () -> {
            invoked[0]++;
            return "msg";
        };
        logger.log(System.Logger.Level.DEBUG, s);
        check(invoked[0] == 0, "supplier must not be invoked when not loggable");
        check(logger.logCalls == 0, "no log call expected when not loggable");
    }

    static void testLoggableDelegatesMessage() {
        RecordingLogger logger = new RecordingLogger();
        logger.loggable = true;
        int[] invoked = {0};
        Supplier<String> s = () -> {
            invoked[0]++;
            return "hello";
        };
        logger.log(System.Logger.Level.WARNING, s);
        check(invoked[0] == 1, "supplier must be invoked exactly once when loggable");
        check(logger.logCalls == 1, "exactly one log call expected");
        check(logger.lastLevel == System.Logger.Level.WARNING, "level must be forwarded");
        check("hello".equals(logger.lastMsg), "message must be supplier result");
        check(logger.lastBundle == null, "bundle must be null");
        check(logger.lastParams == null, "params must be null");
        check(!logger.lastWasThrowableOverload, "Object... overload must be used");
    }

    static void testLoggableWithNullSupplierResultForwardsNull() {
        RecordingLogger logger = new RecordingLogger();
        logger.loggable = true;
        Supplier<String> s = () -> null;
        logger.log(System.Logger.Level.ERROR, s);
        check(logger.logCalls == 1, "exactly one log call expected");
        check(logger.lastMsg == null, "null supplier result must be forwarded as null message");
    }

    public static void main(String[] args) {
        testNullSupplierThrowsNPE();
        testNullLevelThrowsNPE();
        testNotLoggableDoesNotInvokeSupplier();
        testLoggableDelegatesMessage();
        testLoggableWithNullSupplierResultForwardsNull();
    }
}
