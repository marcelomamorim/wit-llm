/*
 * @test
 * @summary Test java.lang.System.log(Level, String, Throwable) default method behavior
 * @run main SystemWitupTest
 */

import java.lang.System.Logger.Level;

public class SystemWitupTest {
    public static void main(String[] args) {
        // Create a Logger instance from System
        System.Logger logger = System.getLogger("TestLogger");

        // Test log(Level, String, Throwable) calls log(Level, String, String, Throwable) with null marker
        // We cannot observe internal state, so test that no exception is thrown and method completes

        Level level = Level.INFO;
        String message = "Test message";
        Throwable thrown = new Throwable("Test throwable");

        // Call the default log method
        logger.log(level, message, thrown);

        // Also test with null message and null throwable
        logger.log(level, null, null);

        // Test with null level - should throw NullPointerException as per Logger interface contract
        boolean npeThrown = false;
        try {
            logger.log(null, "msg", null);
        } catch (NullPointerException e) {
            npeThrown = true;
        }
        if (!npeThrown) {
            throw new AssertionError("Expected NullPointerException when level is null");
        }

        // Test with null logger name to ensure getLogger does not fail
        System.Logger unnamedLogger = System.getLogger(null);
        unnamedLogger.log(Level.WARNING, "Warning message", null);

        // If reached here, test passed
        System.out.println("SystemWitupTest passed");
    }
}
