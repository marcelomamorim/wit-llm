/*
 * @test
 * @summary Checks ZipUtils.fileTimeToWinTime conversion and null handling via reflection
 * @run main/othervm --add-opens java.base/java.util.zip=ALL-UNNAMED ZipUtilsWitupTest
 */

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.attribute.FileTime;
import java.util.concurrent.TimeUnit;

public class ZipUtilsWitupTest {

    private static final long WINDOWS_EPOCH_100NS_AT_UNIX_ZERO = 116444736000000000L;

    private static Method target;

    static {
        try {
            Class<?> c = Class.forName("java.util.zip.ZipUtils");
            target = c.getDeclaredMethod("fileTimeToWinTime", FileTime.class);
            target.setAccessible(true);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }

    static long convert(FileTime ft) throws Throwable {
        try {
            return (Long) target.invoke(null, ft);
        } catch (InvocationTargetException e) {
            throw e.getCause();
        }
    }

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    public static void main(String[] args) throws Throwable {
        long epochWin = convert(FileTime.from(0L, TimeUnit.SECONDS));
        check(epochWin == WINDOWS_EPOCH_100NS_AT_UNIX_ZERO,
              "unix epoch mismatch: " + epochWin);

        long oneSecWin = convert(FileTime.from(1L, TimeUnit.SECONDS));
        check(oneSecWin == WINDOWS_EPOCH_100NS_AT_UNIX_ZERO + 10000000L,
              "one second mismatch: " + oneSecWin);

        long oneMicroWin = convert(FileTime.from(1L, TimeUnit.MICROSECONDS));
        check(oneMicroWin == WINDOWS_EPOCH_100NS_AT_UNIX_ZERO + 10L,
              "one microsecond mismatch: " + oneMicroWin);

        boolean npe = false;
        try {
            convert(null);
        } catch (NullPointerException e) {
            npe = true;
        }
        check(npe, "null FileTime must raise NullPointerException");
    }
}
