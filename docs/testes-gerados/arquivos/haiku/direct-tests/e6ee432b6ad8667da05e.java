/*
 * @test
 * @summary Verifies ZipUtils.fileTimeToUnixTime converts FileTime to Unix seconds with truncation
 * @modules java.base/java.util.zip
 * @run main/othervm --add-opens java.base/java.util.zip=ALL-UNNAMED ZipUtilsWitupTest
 */

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.attribute.FileTime;
import java.util.concurrent.TimeUnit;

public class ZipUtilsWitupTest {
    private static final Method M;
    static {
        try {
            Class<?> c = Class.forName("java.util.zip.ZipUtils");
            M = c.getDeclaredMethod("fileTimeToUnixTime", FileTime.class);
            M.setAccessible(true);
        } catch (ReflectiveOperationException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    static long call(FileTime ft) throws Exception {
        try {
            return (Long) M.invoke(null, ft);
        } catch (InvocationTargetException e) {
            Throwable c = e.getCause();
            if (c instanceof Exception) {
                throw (Exception) c;
            }
            throw (Error) c;
        }
    }

    static void check(long expected, long actual, String msg) {
        if (expected != actual) {
            throw new RuntimeException(msg + " expected=" + expected + " actual=" + actual);
        }
    }

    public static void main(String[] args) throws Exception {
        check(0L, call(FileTime.from(0L, TimeUnit.SECONDS)), "zero");
        check(1234567890L, call(FileTime.from(1234567890L, TimeUnit.SECONDS)), "seconds");
        check(1L, call(FileTime.from(1500L, TimeUnit.MILLISECONDS)), "truncate millis");
        check(1L, call(FileTime.from(1999999L, TimeUnit.MICROSECONDS)), "truncate micros");
        check(0x7fffffffL, call(FileTime.from(0x7fffffffL, TimeUnit.SECONDS)), "upper bound");
    }
}
