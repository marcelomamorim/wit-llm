/*
 * @test
 * @summary Test System.log(Level, Supplier<String>) for null argument and normal behavior
 * @run main SystemWitupTest
 */

import java.lang.System.Logger.Level;
import java.lang.System.Logger;
import java.util.function.Supplier;
import java.util.Objects;

public class SystemWitupTest {
    public static void main(String[] args) {
        testNullMsgSupplierThrowsNPE();
        testNullLevelThrowsNPE();
        testLogWithNonLoggableLevelDoesNotThrow();
        testLogWithLoggableLevelInvokesLog();
    }

    private static void testNullMsgSupplierThrowsNPE() {
        Logger logger = new Logger() {
            @Override
            public boolean isLoggable(Level level) {
                return true;
            }

            @Override
            public void log(Level level, ResourceBundle bundle, String msg, Object... params) {
                // no-op
            }
        };

        try {
            logger.log(Level.INFO, null);
            throw new RuntimeException("Expected NullPointerException for null msgSupplier");
        } catch (NullPointerException npe) {
            // expected
        }
    }

    private static void testNullLevelThrowsNPE() {
        Logger logger = new Logger() {
            @Override
            public boolean isLoggable(Level level) {
                return true;
            }

            @Override
            public void log(Level level, ResourceBundle bundle, String msg, Object... params) {
                // no-op
            }
        };

        try {
            logger.log(null, new Supplier<String>() {
                public String get() {
                    return "message";
                }
            });
            throw new RuntimeException("Expected NullPointerException for null level");
        } catch (NullPointerException npe) {
            // expected
        }
    }

    private static void testLogWithNonLoggableLevelDoesNotThrow() {
        Logger logger = new Logger() {
            @Override
            public boolean isLoggable(Level level) {
                return false;
            }

            @Override
            public void log(Level level, ResourceBundle bundle, String msg, Object... params) {
                throw new RuntimeException("log() should not be called when isLoggable returns false");
            }
        };

        logger.log(Level.INFO, new Supplier<String>() {
            public String get() {
                throw new RuntimeException("Supplier.get() should not be called when isLoggable returns false");
            }
        });
    }

    private static void testLogWithLoggableLevelInvokesLog() {
        final boolean[] logCalled = new boolean[1];
        Logger logger = new Logger() {
            @Override
            public boolean isLoggable(Level level) {
                return true;
            }

            @Override
            public void log(Level level, ResourceBundle bundle, String msg, Object... params) {
                if (level == null || msg == null) {
                    throw new RuntimeException("level and msg must not be null");
                }
                if (!"test message".equals(msg)) {
                    throw new RuntimeException("Unexpected message: " + msg);
                }
                logCalled[0] = true;
            }
        };

        logger.log(Level.INFO, new Supplier<String>() {
            public String get() {
                return "test message";
            }
        });

        if (!logCalled[0]) {
            throw new RuntimeException("log() was not called when isLoggable returned true");
        }
    }
}
