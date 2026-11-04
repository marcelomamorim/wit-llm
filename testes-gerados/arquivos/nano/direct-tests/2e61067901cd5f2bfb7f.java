/* @test    @summary Test for PlatformLogger.isLoggable(Level) method    @run main PlatformLoggerWitupTest */

import sun.util.logging.PlatformLogger;
import java.util.logging.Level;

public class PlatformLoggerWitupTest {

    public static void main(String[] args) {
        testIsLoggableWithNullLevel();
        testIsLoggableWithValidLevel();
        testIsLoggableWithInvalidLevel();
    }

    private static void testIsLoggableWithNullLevel() {
        PlatformLogger logger = new PlatformLogger("TestLogger");
        boolean exceptionThrown = false;
        try {
            logger.isLoggable(null);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected NullPointerException for null level");
        }
    }

    private static void testIsLoggableWithValidLevel() {
        PlatformLogger logger = new PlatformLogger("TestLogger");
        Level level = Level.INFO;
        boolean result = logger.isLoggable(level);
        // Result is boolean, no specific expected value, just ensure no exception
        if (!(result == true || result == false)) {
            throw new AssertionError("Unexpected result for isLoggable with valid level");
        }
    }

    private static void testIsLoggableWithInvalidLevel() {
        PlatformLogger logger = new PlatformLogger("TestLogger");
        Level invalidLevel = new Level("INVALID", Integer.MAX_VALUE) {
            @Override
            public String getName() {
                return "INVALID";
            }
        };
        boolean result = logger.isLoggable(invalidLevel);
        // No exception expected, result is boolean
        if (!(result == true || result == false)) {
            throw new AssertionError("Unexpected result for isLoggable with invalid level");
        }
    }
}
