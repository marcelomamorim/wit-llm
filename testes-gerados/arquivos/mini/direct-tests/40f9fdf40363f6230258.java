/*
 * @test
 * @summary Test MethodHandles.filterArguments behavior with various inputs
 * @run main MethodHandlesWitupTest
 */

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

public class MethodHandlesWitupTest {
    public static void main(String[] args) throws Throwable {
        testFilterArgumentsWithNoFilters();
        testFilterArgumentsWithNullFilters();
        testFilterArgumentsWithSingleFilter();
        testFilterArgumentsWithMultipleFilters();
    }

    static void testFilterArgumentsWithNoFilters() throws Throwable {
        MethodHandle target = MethodHandles.identity(String.class);
        MethodHandle result = MethodHandles.filterArguments(target, 0);
        // Should return the original target when no filters
        if (result == null) throw new Error("filterArguments returned null with no filters");
        String input = "test";
        Object output = result.invokeExact(input);
        if (!input.equals(output)) throw new Error("filterArguments with no filters failed");
    }

    static void testFilterArgumentsWithNullFilters() throws Throwable {
        MethodHandle target = MethodHandles.identity(String.class);
        MethodHandle result = MethodHandles.filterArguments(target, 0, (MethodHandle) null, null);
        // Null filters are ignored, so result should behave like target
        String input = "hello";
        Object output = result.invokeExact(input);
        if (!input.equals(output)) throw new Error("filterArguments with null filters failed");
    }

    static void testFilterArgumentsWithSingleFilter() throws Throwable {
        MethodHandle target = MethodHandles.identity(String.class);
        MethodHandle filter = MethodHandles.lookup().findStatic(MethodHandlesWitupTest.class, "toUpperCase", MethodType.methodType(String.class, String.class));
        MethodHandle result = MethodHandles.filterArguments(target, 0, filter);
        String input = "abc";
        Object output = result.invokeExact(input);
        if (!"ABC".equals(output)) throw new Error("filterArguments with single filter failed");
    }

    static void testFilterArgumentsWithMultipleFilters() throws Throwable {
        MethodHandle target = MethodHandles.lookup().findStatic(MethodHandlesWitupTest.class, "concat", MethodType.methodType(String.class, String.class, String.class));
        MethodHandle filter1 = MethodHandles.lookup().findStatic(MethodHandlesWitupTest.class, "toUpperCase", MethodType.methodType(String.class, String.class));
        MethodHandle filter2 = MethodHandles.lookup().findStatic(MethodHandlesWitupTest.class, "toLowerCase", MethodType.methodType(String.class, String.class));
        MethodHandle result = MethodHandles.filterArguments(target, 0, filter1, filter2);
        String output = (String) result.invokeExact("abc", "DEF");
        if (!"ABCdef".equals(output)) throw new Error("filterArguments with multiple filters failed");
    }

    // Helper static methods used as filters and targets
    public static String toUpperCase(String s) {
        return s.toUpperCase();
    }

    public static String toLowerCase(String s) {
        return s.toLowerCase();
    }

    public static String concat(String a, String b) {
        return a + b;
    }
}