/*
 * @test
 * @summary CleanerImpl sentinel cleanable lists must throw UnsupportedOperationException from get()
 * @modules java.base/jdk.internal.ref
 * @run main/othervm --add-opens java.base/jdk.internal.ref=ALL-UNNAMED CleanerImplWitupTest
 */

import java.lang.ref.Reference;
import java.lang.reflect.Field;

public class CleanerImplWitupTest {

    public static void main(String[] args) throws Exception {
        CleanerImpl impl = new CleanerImpl();
        checkSentinelGet(impl, "phantomCleanableList");
        checkSentinelGet(impl, "weakCleanableList");
        checkSentinelGet(impl, "softCleanableList");
    }

    private static void checkSentinelGet(CleanerImpl impl, String fieldName) throws Exception {
        Field f = CleanerImpl.class.getDeclaredField(fieldName);
        f.setAccessible(true);
        Reference<?> ref = (Reference<?>) f.get(impl);
        if (ref == null) {
            throw new RuntimeException(fieldName + " is null");
        }
        try {
            Object o = ref.get();
            throw new RuntimeException(fieldName + ".get() returned " + o + " instead of throwing");
        } catch (UnsupportedOperationException e) {
            if (!"get".equals(e.getMessage())) {
                throw new RuntimeException(fieldName + " unexpected message: " + e.getMessage(), e);
            }
        }
    }
}
