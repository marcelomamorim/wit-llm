/*
 * @test
 * @summary Checks ZipUtils.fileTimeToUnixTime for valid and null FileTime inputs
 * @run main/othervm --add-opens java.base/java.util.zip=ALL-UNNAMED ZipUtilsWitupTest
 */

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.attribute.FileTime;
import java.util.concurrent.TimeUnit;

public class ZipUtilsWitupTest {

    public static void main(String[] args) throws Exception {
        Class<?> c = Class.forName("java.util.zip.ZipUtils");
        Method m = c.getDeclaredMethod("fileTimeToUnixTime", FileTime.class);
        m.setAccessible(true);

        long v1 = (long) m.invoke(null, FileTime.from(1000L, TimeUnit.SECONDS));
        if (v1 != 1000L) {
            throw new RuntimeException("expected 1000 but was " + v1);
        }

        long v2 = (long) m.invoke(null, FileTime.from(-5L, TimeUnit.SECONDS));
        if (v2 != -5L) {
            throw new RuntimeException("expected -5 but was " + v2);
        }

        long v3 = (long) m.invoke(null, FileTime.from(0L, TimeUnit.SECONDS));
        if (v3 != 0L) {
            throw new RuntimeException("expected 0 but was " + v3);
        }

        try {
            m.invoke(null, (Object) null);
            throw new RuntimeException("expected InvocationTargetException for null argument");
        } catch (InvocationTargetException e) {
            if (!(e.getCause() instanceof NullPointerException)) {
                throw new RuntimeException("expected NullPointerException cause but was " + e.getCause());
            }
        }
    }
}
