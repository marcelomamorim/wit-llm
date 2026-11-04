/*
 * @test
 * @summary Verifies that the default System.Logger.log(Level, String, Throwable) delegates level, message and throwable to the four-argument overload with a null ResourceBundle
 * @run main SystemWitupTest
 */
import java.util.ResourceBundle;

public class SystemWitupTest {

    static final class RecordingLogger implements System.Logger {
        int calls;
        System.Logger.Level lastLevel;
        ResourceBundle lastBundle;
        String lastMsg;
        Throwable lastThrown;
        boolean bundleWasNull;

        public String getName() {
            return "recording";
        }

        public boolean isLoggable(System.Logger.Level level) {
            return true;
        }

        public void log(System.Logger.Level level, ResourceBundle bundle, String msg, Throwable thrown) {
            calls++;
            lastLevel = level;
            lastBundle = bundle;
            bundleWasNull = (bundle == null);
            lastMsg = msg;
            lastThrown = thrown;
        }

        public void log(System.Logger.Level level, ResourceBundle bundle, String format, Object... params) {
            calls++;
        }
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) {
        RecordingLogger logger = new RecordingLogger();
        Throwable t = new IllegalStateException("boom");

        logger.log(System.Logger.Level.WARNING, "hello", t);
        check(logger.calls == 1, "expected one delegated call, got " + logger.calls);
        check(logger.lastLevel == System.Logger.Level.WARNING, "level not forwarded");
        check(logger.bundleWasNull, "bundle should be null in delegated call");
        check("hello".equals(logger.lastMsg), "message not forwarded");
        check(logger.lastThrown == t, "throwable not forwarded");

        String nullMsg = null;
        Throwable nullThrown = null;
        logger.log(System.Logger.Level.ERROR, nullMsg, nullThrown);
        check(logger.calls == 2, "expected two delegated calls, got " + logger.calls);
        check(logger.lastLevel == System.Logger.Level.ERROR, "level not forwarded for null message");
        check(logger.lastMsg == null, "null message should be forwarded as null");
        check(logger.lastThrown == null, "null throwable should be forwarded as null");
        check(logger.bundleWasNull, "bundle should remain null for null message case");
    }
}
