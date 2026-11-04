/*
 * @test
 * @summary Verify PlatformLogger.isLoggable(Level) rejects null with NullPointerException and is deterministic for a valid level
 * @modules java.base/sun.util.logging
 * @run main PlatformLoggerWitupTest
 */

import sun.util.logging.PlatformLogger;
import sun.util.logging.PlatformLogger.Level;

public class PlatformLoggerWitupTest {

    public static void main(String[] args) {
        PlatformLogger logger = PlatformLogger.getLogger("sun.util.logging.witup");

        boolean npeThrown = false;
        try {
            logger.isLoggable(null);
        } catch (NullPointerException e) {
            npeThrown = true;
        }
        if (!npeThrown) {
            throw new AssertionError("Expected NullPointerException for null level");
        }

        Level info = Level.valueOf(800);
        if (info == null) {
            throw new AssertionError("Level.valueOf(800) returned null");
        }

        boolean first = logger.isLoggable(info);
        boolean second = logger.isLoggable(info);
        if (first != second) {
            throw new AssertionError("isLoggable not deterministic for same level");
        }
    }
}
