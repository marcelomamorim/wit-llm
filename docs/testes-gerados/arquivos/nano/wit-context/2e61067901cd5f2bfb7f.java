/* @test    @summary Test for sun.util.logging.PlatformLogger.isLoggable(Level) method    @run main PlatformLoggerWitupTest */

import sun.util.logging.PlatformLogger;
import java.util.logging.Level;

public class PlatformLoggerWitupTest {

    public static void main(String[] args) {
        testIsLoggableWithNullLevel();
        testIsLoggableWithValidLevel();
    }

    private static void testIsLoggableWithNullLevel() {
        PlatformLogger logger = PlatformLogger.getLogger("TestLogger");
        try {
            logger.isLoggable(null);
            System.out.println("FAIL: Expected NullPointerException for null level");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown as expected for null level");
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception type: " + t);
        }
    }

    private static void testIsLoggableWithValidLevel() {
        PlatformLogger logger = PlatformLogger.getLogger("TestLogger");
        Level level = Level.INFO;
        boolean result = false;
        try {
            result = logger.isLoggable(level);
            System.out.println("isLoggable with Level.INFO returned: " + result);
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception during isLoggable with valid level: " + t);
        }
    }
}
