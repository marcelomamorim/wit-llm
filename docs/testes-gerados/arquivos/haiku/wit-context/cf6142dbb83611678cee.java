/*
 * @test
 * @summary Verifies that ZipUtils.unixTimeToFileTime(long) converts Unix seconds to the equivalent FileTime
 * @run main/othervm --add-opens java.base/java.util.zip=ALL-UNNAMED ZipUtilsWitupTest
 */

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.attribute.FileTime;
import java.util.concurrent.TimeUnit;

public class ZipUtilsWitupTest {

    public static void main(String[] args) throws Exception {
        Class<?> c = Class.forName("java.util.zip.ZipUtils");
        Method m = c.getDeclaredMethod("unixTimeToFileTime", long.class);
        m.setAccessible(true);

        long[] values = {0L, 1L, -1L, 1700000000L, 0x7fffffffL, -2147483648L};
        for (long v : values) {
            FileTime actual = invoke(m, v);
            if (actual == null) {
                throw new RuntimeException("null result for " + v);
            }
            FileTime expected = FileTime.from(v, TimeUnit.SECONDS);
            if (!expected.equals(actual)) {
                throw new RuntimeException("mismatch for " + v + ": expected " + expected + " but was " + actual);
            }
            if (actual.to(TimeUnit.SECONDS) != v) {
                throw new RuntimeException("round trip failed for " + v);
            }
        }
    }

    static FileTime invoke(Method m, long v) throws Exception {
        try {
            return (FileTime) m.invoke(null, v);
        } catch (InvocationTargetException e) {
            throw new RuntimeException("unexpected exception for " + v, e.getCause());
        }
    }
}
