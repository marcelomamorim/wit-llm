/*
 * @test
 * @summary Test DefaultLoggerFinder.getLogger(String, Module) for null argument checks and normal behavior
 * @run main DefaultLoggerFinderWitupTest
 */

import jdk.internal.logger.DefaultLoggerFinder;
import java.lang.System.Logger;
import java.lang.Module;
import java.util.Objects;

public class DefaultLoggerFinderWitupTest {

    public static void main(String[] args) {
        testGetLoggerNullName();
        testGetLoggerNullModule();
        testGetLoggerValidArgs();
    }

    private static void testGetLoggerNullName() {
        DefaultLoggerFinder finder = new DefaultLoggerFinder();
        try {
            finder.getLogger(null, DefaultLoggerFinderWitupTest.class.getModule());
            throw new RuntimeException("Expected NullPointerException for null name");
        } catch (NullPointerException npe) {
            if (!"name".equals(npe.getMessage())) {
                throw new RuntimeException("NullPointerException message mismatch for null name");
            }
        }
    }

    private static void testGetLoggerNullModule() {
        DefaultLoggerFinder finder = new DefaultLoggerFinder();
        try {
            finder.getLogger("testLogger", null);
            throw new RuntimeException("Expected NullPointerException for null module");
        } catch (NullPointerException npe) {
            if (!"module".equals(npe.getMessage())) {
                throw new RuntimeException("NullPointerException message mismatch for null module");
            }
        }
    }

    private static void testGetLoggerValidArgs() {
        DefaultLoggerFinder finder = new DefaultLoggerFinder();
        String loggerName = "testLogger";
        Module module = DefaultLoggerFinderWitupTest.class.getModule();
        Logger logger = finder.getLogger(loggerName, module);
        if (logger == null) {
            throw new RuntimeException("getLogger returned null for valid arguments");
        }
        // The returned Logger should have the requested name
        if (!loggerName.equals(logger.getName())) {
            throw new RuntimeException("Logger name mismatch");
        }
    }
}
