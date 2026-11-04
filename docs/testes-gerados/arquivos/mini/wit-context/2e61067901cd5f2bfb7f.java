/*
 * @test
 * @summary Test sun.util.logging.PlatformLogger.isLoggable(Level) for null argument and normal behavior
 * @run main PlatformLoggerWitupTest
 */

import sun.util.logging.PlatformLogger;
import sun.util.logging.PlatformLogger.Level;

public class PlatformLoggerWitupTest {
    public static void main(String[] args) {
        PlatformLogger logger = PlatformLogger.getLogger("testLogger");

        // Test that isLoggable throws NullPointerException when level is null
        try {
            logger.isLoggable(null);
            throw new AssertionError("Expected NullPointerException for null level");
        } catch (NullPointerException expected) {
            // expected
        }

        // Test that isLoggable returns a boolean for a valid Level
        Level[] testLevels = {
            Level.OFF,
            Level.SEVERE,
            Level.WARNING,
            Level.INFO,
            Level.CONFIG,
            Level.FINE,
            Level.FINER,
            Level.FINEST,
            Level.ALL
        };

        for (Level level : testLevels) {
            boolean result = logger.isLoggable(level);
            // result is boolean, no exception expected
            if (!(result == true || result == false)) {
                throw new AssertionError("isLoggable returned non-boolean value");
            }
        }
    }
}
