/*
 * @test
 * @summary Verifies that MethodHandles.iteratedLoop rejects a null body with NullPointerException
 * @run main MethodHandlesWitupTest
 */

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;

public class MethodHandlesWitupTest {

    public static void main(String[] args) throws Throwable {
        testNullBodyWithNullIteratorAndInit();
        testNullBodyWithNullInitOnly();
    }

    static void testNullBodyWithNullIteratorAndInit() {
        boolean thrown = false;
        try {
            MethodHandle mh = MethodHandles.iteratedLoop(null, null, null);
            if (mh != null) {
                throw new AssertionError("expected NullPointerException, got handle");
            }
        } catch (NullPointerException expected) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("NullPointerException not thrown for null body");
        }
    }

    static void testNullBodyWithNullInitOnly() {
        MethodHandle iterator = MethodHandles.constant(java.util.Iterator.class, null);
        boolean thrown = false;
        try {
            MethodHandle mh = MethodHandles.iteratedLoop(iterator, null, null);
            if (mh != null) {
                throw new AssertionError("expected NullPointerException, got handle");
            }
        } catch (NullPointerException expected) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("NullPointerException not thrown for null body with non-null iterator");
        }
    }
}
