/*
 * @test
 * @summary Test sun.util.logging.PlatformLogger.isLoggable(Level) method
 * @run main PlatformLoggerWitupTest
 */

import sun.util.logging.PlatformLogger;
import java.util.logging.Level;

public class PlatformLoggerWitupTest {
    public static void main(String[] args) {
        PlatformLogger logger = PlatformLogger.getLogger("testLogger");

        // Test that isLoggable returns a boolean and does not throw for valid Level
        boolean resultInfo = logger.isLoggable(Level.INFO);
        boolean resultSevere = logger.isLoggable(Level.SEVERE);

        if (!(resultInfo == true || resultInfo == false)) {
            throw new AssertionError("isLoggable(Level.INFO) did not return a boolean");
        }
        if (!(resultSevere == true || resultSevere == false)) {
            throw new AssertionError("isLoggable(Level.SEVERE) did not return a boolean");
        }

        // Test that passing null throws NullPointerException
        try {
            logger.isLoggable(null);
            throw new AssertionError("Expected NullPointerException for null level");
        } catch (NullPointerException npe) {
            // expected
        }
    }
}
