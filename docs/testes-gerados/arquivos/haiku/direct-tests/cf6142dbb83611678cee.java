/*
 * @test
 * @summary Verifies ZipUtils.unixTimeToFileTime converts Unix seconds to FileTime
 * @modules java.base/java.util.zip
 * @run main/othervm --add-opens java.base/java.util.zip=ALL-UNNAMED ZipUtilsWitupTest
 */

import java.lang.reflect.Method;
import java.nio.file.attribute.FileTime;
import java.util.concurrent.TimeUnit;

public class ZipUtilsWitupTest {

    public static void main(String[] args) throws Exception {
        Class<?> c = Class.forName("java.util.zip.ZipUtils");
        Method m = c.getDeclaredMethod("unixTimeToFileTime", long.class);
        m.setAccessible(true);

        check(m, 0L);
        check(m, 1L);
        check(m, 1234567890L);
        check(m, 0x7fffffffL);
        check(m, -1L);
        check(m, -86400L);

        FileTime ft = (FileTime) m.invoke(null, 1000L);
        if (ft.to(TimeUnit.MILLISECONDS) != 1000000L) {
            throw new RuntimeException("Expected 1000000 ms, got " + ft.to(TimeUnit.MILLISECONDS));
        }
    }

    private static void check(Method m, long utime) throws Exception {
        FileTime actual = (FileTime) m.invoke(null, utime);
        FileTime expected = FileTime.from(utime, TimeUnit.SECONDS);
        if (!expected.equals(actual)) {
            throw new RuntimeException("Mismatch for " + utime + ": expected " + expected + " got " + actual);
        }
        if (actual.to(TimeUnit.SECONDS) != utime) {
            throw new RuntimeException("Round trip failed for " + utime);
        }
    }
}
