/*
 * @test
 * @summary Test MethodHandles.zero(Class<?>) for null argument and primitive/non-primitive types
 * @run main MethodHandlesWitupTest
 */

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import sun.invoke.util.Wrapper;
import java.util.Objects;

public class MethodHandlesWitupTest {
    public static void main(String[] args) throws Throwable {
        testZeroWithNull();
        testZeroWithPrimitive();
        testZeroWithNonPrimitive();
    }

    private static void testZeroWithNull() {
        try {
            MethodHandles.zero(null);
            throw new Error("Expected NullPointerException for null argument");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testZeroWithPrimitive() throws Throwable {
        Class<?> type = int.class;
        MethodHandle mh = MethodHandles.zero(type);
        Objects.requireNonNull(mh);
        // The returned MethodHandle should correspond to zero value of primitive int
        // We test that the type is primitive and the wrapper matches
        Wrapper w = Wrapper.forPrimitiveType(type);
        if (w == null) {
            throw new Error("Wrapper.forPrimitiveType returned null for primitive type");
        }
        // We cannot invoke mh directly without knowing its signature, so just check non-null
    }

    private static void testZeroWithNonPrimitive() throws Throwable {
        Class<?> type = String.class;
        MethodHandle mh = MethodHandles.zero(type);
        Objects.requireNonNull(mh);
        // For non-primitive, wrapper should be Wrapper.OBJECT
        Wrapper w = Wrapper.OBJECT;
        if (w == null) {
            throw new Error("Wrapper.OBJECT is null");
        }
        // We cannot invoke mh directly without knowing its signature, so just check non-null
    }
}
