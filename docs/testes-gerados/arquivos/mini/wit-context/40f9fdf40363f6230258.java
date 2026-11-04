/*
 * @test
 * @summary Test MethodHandles.filterArguments behavior including arity check and filter application
 * @run main MethodHandlesWitupTest
 */

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

public class MethodHandlesWitupTest {

    public static void main(String[] args) throws Throwable {
        testFilterArgumentsWithValidFilters();
        testFilterArgumentsWithNullFilters();
        testFilterArgumentsWithTooManyFilters();
    }

    static void testFilterArgumentsWithValidFilters() throws Throwable {
        MethodHandles.Lookup lookup = MethodHandles.lookup();
        MethodHandle target = lookup.findStatic(MethodHandlesWitupTest.class, "targetMethod", MethodType.methodType(String.class, String.class, String.class));
        MethodHandle filter1 = lookup.findStatic(MethodHandlesWitupTest.class, "filterMethod", MethodType.methodType(String.class, String.class));
        MethodHandle filter2 = lookup.findStatic(MethodHandlesWitupTest.class, "filterMethod", MethodType.methodType(String.class, String.class));

        MethodHandle filtered = MethodHandles.filterArguments(target, 0, filter1, filter2);

        // The resulting method handle should apply filters to arguments before target
        String result = (String) filtered.invokeExact("a", "b");
        if (!"target:filter:filter:a:b".equals(result)) {
            throw new AssertionError("Unexpected result: " + result);
        }
    }

    static void testFilterArgumentsWithNullFilters() throws Throwable {
        MethodHandles.Lookup lookup = MethodHandles.lookup();
        MethodHandle target = lookup.findStatic(MethodHandlesWitupTest.class, "targetMethod", MethodType.methodType(String.class, String.class, String.class));

        // Include null filters, which should be ignored
        MethodHandle filtered = MethodHandles.filterArguments(target, 0, null, null);

        String result = (String) filtered.invokeExact("x", "y");
        if (!"target:x:y".equals(result)) {
            throw new AssertionError("Unexpected result with null filters: " + result);
        }
    }

    static void testFilterArgumentsWithTooManyFilters() {
        MethodHandles.Lookup lookup = MethodHandles.lookup();
        try {
            MethodHandle target = lookup.findStatic(MethodHandlesWitupTest.class, "targetMethod", MethodType.methodType(String.class, String.class));
            MethodHandle filter1 = lookup.findStatic(MethodHandlesWitupTest.class, "filterMethod", MethodType.methodType(String.class, String.class));
            MethodHandle filter2 = lookup.findStatic(MethodHandlesWitupTest.class, "filterMethod", MethodType.methodType(String.class, String.class));

            // pos=0, target has 1 parameter, filters length=2 -> too many filters
            MethodHandles.filterArguments(target, 0, filter1, filter2);
            throw new AssertionError("Expected IllegalArgumentException for too many filters");
        } catch (IllegalArgumentException e) {
            // Expected exception
        } catch (Throwable t) {
            throw new AssertionError("Unexpected exception type", t);
        }
    }

    // Target method with two String parameters
    public static String targetMethod(String a, String b) {
        return "target:" + a + ":" + b;
    }

    // Filter method with one String parameter
    public static String filterMethod(String s) {
        return "filter:" + s;
    }
}
