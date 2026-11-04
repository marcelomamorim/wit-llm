/*
 * @test
 * @summary Tests for jdk.internal.ref.CleanerImpl class methods.
 * @run main CleanerImplWitupTest
 */

import java.lang.ref.Cleaner;
import java.lang.reflect.Method;
import java.util.function.Function;

public class CleanerImplWitupTest {

    public static void main(String[] args) throws Exception {
        testSetCleanerImplAccess();
    }

    public static void testSetCleanerImplAccess() throws Exception {
        // Reflection to access the method
        Method method = CleanerImpl.class.getDeclaredMethod("setCleanerImplAccess", Function.class);
        method.setAccessible(true);

        // Prepare a dummy Function to pass
        Function<Cleaner, Object> dummyFunction = new Function<Cleaner, Object>() {
            @Override
            public Object apply(Cleaner cleaner) {
                return null;
            }
        };

        // Invoke the method for the first time (should succeed)
        try {
            method.invoke(null, dummyFunction);
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception on first invocation", e);
        }

        // Invoke again (should throw InternalError)
        boolean exceptionThrown = false;
        try {
            method.invoke(null, dummyFunction);
        } catch (Exception e) {
            Throwable cause = e.getCause();
            if (cause instanceof InternalError) {
                exceptionThrown = true;
            } else {
                throw new RuntimeException("Unexpected exception type", e);
            }
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected InternalError was not thrown on second invocation");
        }
    }
}
