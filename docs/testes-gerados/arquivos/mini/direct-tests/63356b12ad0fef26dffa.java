/*
 * @test
 * @summary Test jdk.internal.logger.DefaultLoggerFinder.getLogger method
 * @run main DefaultLoggerFinderWitupTest
 */

import java.lang.System.Logger;
import java.lang.Module;
import java.util.Objects;
import jdk.internal.logger.DefaultLoggerFinder;

public class DefaultLoggerFinderWitupTest {

    public static void main(String[] args) {
        DefaultLoggerFinder finder = new DefaultLoggerFinder();

        // Test normal behavior with valid name and module
        String loggerName = "TestLogger";
        Module module = DefaultLoggerFinderWitupTest.class.getModule();
        Logger logger = finder.getLogger(loggerName, module);
        if (logger == null) {
            throw new AssertionError("getLogger returned null for valid inputs");
        }

        // Test that the returned logger has the correct name
        // The Logger interface does not have a getName method, so we cannot assert on name directly
        // But we can check that getLogger does not return null and does not throw

        // Test that passing null as name throws NullPointerException
        try {
            finder.getLogger(null, module);
            throw new AssertionError("Expected NullPointerException for null name");
        } catch (NullPointerException npe) {
            if (!"name".equals(npe.getMessage())) {
                throw new AssertionError("NullPointerException message mismatch for null name");
            }
        }

        // Test that passing null as module throws NullPointerException
        try {
            finder.getLogger(loggerName, null);
            throw new AssertionError("Expected NullPointerException for null module");
        } catch (NullPointerException npe) {
            if (!"module".equals(npe.getMessage())) {
                throw new AssertionError("NullPointerException message mismatch for null module");
            }
        }

        // Test that SecurityException is thrown if permission check fails
        // This is not testable here without security manager setup, so omitted

        System.out.println("All tests passed.");
    }
}
