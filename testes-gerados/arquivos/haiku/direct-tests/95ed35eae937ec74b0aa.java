/*
 * @test
 * @summary Verify that System.Logger default log(Level, String, Throwable) delegates to log(Level, ResourceBundle, String, Throwable) with a null bundle
 * @run main SystemWitupTest
 */

import java.util.ResourceBundle;

public class SystemWitupTest {

    static class RecordingLogger implements System.Logger {
        int calls;
        String path;
        System.Logger.Level level;
        ResourceBundle bundle;
        boolean bundleWasSet;
        String msg;
        Throwable thrown;

        @Override
        public String getName() {
            return "recording";
        }

        @Override
        public boolean isLoggable(System.Logger.Level level) {
            return true;
        }

        @Override
        public void log(System.Logger.Level level, ResourceBundle bundle, String msg, Throwable thrown) {
            calls++;
            path = "throwable";
            this.level = level;
            this.bundle = bundle;
            this.bundleWasSet = true;
            this.msg = msg;
            this.thrown = thrown;
        }

        @Override
        public void log(System.Logger.Level level, ResourceBundle bundle, String format, Object... params) {
            calls++;
            path = "params";
            this.level = level;
            this.bundle = bundle;
            this.bundleWasSet = true;
            this.msg = format;
            this.thrown = null;
        }
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) {
        RecordingLogger r = new RecordingLogger();
        Throwable t = new IllegalStateException("boom");
        r.log(System.Logger.Level.WARNING, "hello", t);
        check(r.calls == 1, "expected exactly one delegated call, got " + r.calls);
        check("throwable".equals(r.path), "expected Throwable overload, got " + r.path);
        check(r.level == System.Logger.Level.WARNING, "level not propagated: " + r.level);
        check(r.bundleWasSet && r.bundle == null, "bundle must be null");
        check("hello".equals(r.msg), "msg not propagated: " + r.msg);
        check(r.thrown == t, "thrown not propagated");

        RecordingLogger n = new RecordingLogger();
        n.log(System.Logger.Level.ERROR, (String) null, (Throwable) null);
        check(n.calls == 1, "null arguments: expected one call, got " + n.calls);
        check("throwable".equals(n.path), "null arguments: wrong path " + n.path);
        check(n.level == System.Logger.Level.ERROR, "null arguments: level mismatch");
        check(n.bundle == null, "null arguments: bundle must be null");
        check(n.msg == null, "null msg must be passed through");
        check(n.thrown == null, "null thrown must be passed through");

        RecordingLogger d = new RecordingLogger();
        d.log(System.Logger.Level.DEBUG, "dbg", null);
        check(d.calls == 1 && d.level == System.Logger.Level.DEBUG, "debug level not delegated");
        check("dbg".equals(d.msg) && d.thrown == null, "debug msg/thrown mismatch");
    }
}
