/*
 * @test
 * @summary Verifies ZipUtils.fileTimeToWinTime conversion from FileTime to Windows time
 * @modules java.base/java.util.zip
 * @run main/othervm --add-opens java.base/java.util.zip=ALL-UNNAMED ZipUtilsWitupTest
 */

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.attribute.FileTime;
import java.util.concurrent.TimeUnit;

public class ZipUtilsWitupTest {

    private static Method fileTimeToWinTime;
    private static Method winTimeToFileTime;

    public static void main(String[] args) throws Exception {
        Class<?> c = Class.forName("java.util.zip.ZipUtils");
        fileTimeToWinTime = c.getDeclaredMethod("fileTimeToWinTime", FileTime.class);
        fileTimeToWinTime.setAccessible(true);
        winTimeToFileTime = c.getDeclaredMethod("winTimeToFileTime", long.class);
        winTimeToFileTime.setAccessible(true);

        check(FileTime.from(0, TimeUnit.MICROSECONDS), 116444736000000000L);
        check(FileTime.from(1, TimeUnit.SECONDS), 116444746000000000L);
        check(FileTime.from(-1, TimeUnit.SECONDS), 116444726000000000L);
        check(FileTime.from(1500, TimeUnit.NANOSECONDS), 116444736000000010L);

        long w = 116444736000000000L;
        FileTime ft = (FileTime) winTimeToFileTime.invoke(null, w);
        check(ft, w);
    }

    private static long call(FileTime ft) throws Exception {
        try {
            return (Long) fileTimeToWinTime.invoke(null, ft);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e.getCause());
        }
    }

    private static void check(FileTime ft, long expected) throws Exception {
        long actual = call(ft);
        if (actual != expected) {
            throw new RuntimeException("expected " + expected + " but was " + actual);
        }
    }
}
