/*
 * @test
 * @summary Verifies DefaultLoggerFinder.getLogger(String, Module) null checks and basic observable logger creation
 * @modules java.base/jdk.internal.logger
 * @run main DefaultLoggerFinderWitupTest
 */

import java.lang.System.Logger;
import jdk.internal.logger.DefaultLoggerFinder;

public class DefaultLoggerFinderWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) {
        DefaultLoggerFinder finder = new DefaultLoggerFinder() {};
        Module module = DefaultLoggerFinderWitupTest.class.getModule();

        boolean npeName = false;
        try {
            finder.getLogger(null, module);
        } catch (NullPointerException e) {
            npeName = true;
        }
        check(npeName, "null name must throw NullPointerException");

        boolean npeModule = false;
        try {
            finder.getLogger("witup.test", null);
        } catch (NullPointerException e) {
            npeModule = true;
        }
        check(npeModule, "null module must throw NullPointerException");

        Logger logger = finder.getLogger("witup.test.logger", module);
        check(logger != null, "getLogger must return a non-null logger");
        check("witup.test.logger".equals(logger.getName()),
              "returned logger must have the requested name");

        Logger other = finder.getLogger("witup.other", module);
        check(other != null, "getLogger must return a non-null logger for another name");
        check("witup.other".equals(other.getName()),
              "returned logger must have the second requested name");
    }
}
