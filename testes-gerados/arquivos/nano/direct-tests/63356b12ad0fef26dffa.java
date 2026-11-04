/* @test    @summary Test DefaultLoggerFinder.getLogger for valid inputs    @run main DefaultLoggerFinderWitupTest */

import jdk.internal.logger.DefaultLoggerFinder;
import java.lang.System.Logger;
import java.lang.Module;

public class DefaultLoggerFinderWitupTest {

    public static void main(String[] args) {
        testGetLoggerWithValidNameAndModule();
        testGetLoggerWithNullName();
        testGetLoggerWithNullModule();
    }

    static void testGetLoggerWithValidNameAndModule() {
        String name = "TestLogger";
        Module module = ModuleLayer.boot().findModule("java.base").orElse(null);
        Logger logger = null;
        try {
            logger = DefaultLoggerFinder.getLogger(name, module);
        } catch (Throwable t) {
            throw new AssertionError("Unexpected exception", t);
        }
        if (logger == null) {
            throw new AssertionError("Logger should not be null for valid inputs");
        }
    }

    static void testGetLoggerWithNullName() {
        try {
            DefaultLoggerFinder.getLogger(null, ModuleLayer.boot());
            throw new AssertionError("Expected NullPointerException for null name");
        } catch (NullPointerException expected) {
            // expected
        } catch (Throwable t) {
            throw new AssertionError("Unexpected exception", t);
        }
    }

    static void testGetLoggerWithNullModule() {
        try {
            DefaultLoggerFinder.getLogger("Test", null);
            throw new AssertionError("Expected NullPointerException for null module");
        } catch (NullPointerException expected) {
            // expected
        } catch (Throwable t) {
            throw new AssertionError("Unexpected exception", t);
        }
    }
}
