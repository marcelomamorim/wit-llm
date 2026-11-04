/*
 * @test
 * @summary Verifies that PlatformLogger.isLoggable(null) throws NullPointerException and accepts a non-null level
 * @modules java.base/sun.util.logging
 * @run main PlatformLoggerWitupTest
 */

import sun.util.logging.PlatformLogger;
import sun.util.logging.PlatformLogger.Level;

public class PlatformLoggerWitupTest {

    public static void main(String[] args) {
        PlatformLogger logger = PlatformLogger.getLogger("witup.generated.test");

        boolean npeThrown = false;
        try {
            logger.isLoggable(null);
        } catch (NullPointerException e) {
            npeThrown = true;
        }
        if (!npeThrown) {
            throw new AssertionError("Expected NullPointerException for null level");
        }

        Level level = Level.valueOf(Integer.MAX_VALUE);
        boolean result = logger.isLoggable(level);
        if (result != logger.isLoggable(level)) {
            throw new AssertionError("isLoggable must be deterministic for the same level");
        }
    }
}
