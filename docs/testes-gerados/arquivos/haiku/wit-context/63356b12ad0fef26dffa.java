/*
 * @test
 * @summary WITUP-derived checks for DefaultLoggerFinder.getLogger null-argument NullPointerException paths
 * @modules java.base/jdk.internal.logger
 * @run main DefaultLoggerFinderWitupTest
 */

import jdk.internal.logger.DefaultLoggerFinder;

public class DefaultLoggerFinderWitupTest {

    static final class Finder extends DefaultLoggerFinder {
    }

    interface Action {
        void run();
    }

    public static void main(String[] args) {
        Finder finder = new Finder();
        Module module = DefaultLoggerFinderWitupTest.class.getModule();

        expectNpe(() -> finder.getLogger(null, module), "name == null");
        expectNpe(() -> finder.getLogger("witup.test", null), "module == null with name != null");
        expectNpe(() -> finder.getLogger(null, null), "name == null and module == null");

        System.Logger logger = finder.getLogger("witup.test", module);
        if (logger == null) {
            throw new RuntimeException("getLogger returned null for valid arguments");
        }
    }

    static void expectNpe(Action action, String description) {
        try {
            action.run();
        } catch (NullPointerException e) {
            return;
        }
        throw new RuntimeException("Expected NullPointerException for: " + description);
    }
}
