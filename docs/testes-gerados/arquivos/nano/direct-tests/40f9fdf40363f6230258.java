/*
 * @test
 * @summary Tests the filterArguments method in MethodHandles.
 * @run main MethodHandlesWitupTest
 */

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

public class MethodHandlesWitupTest {

    public static void main(String[] args) {
        testFilterArgumentsWithValidFilters();
        testFilterArgumentsWithNullFilter();
        testFilterArgumentsWithEmptyFilters();
        testFilterArgumentsWithNullTarget();
        testFilterArgumentsWithNegativePos();
    }

    static void testFilterArgumentsWithValidFilters() {
        try {
            MethodHandle mh = MethodHandles.lookup().findStatic(TestMethods.class, "add", MethodType.methodType(int.class, int.class, int.class));
            MethodHandle filter = MethodHandles.lookup().findStatic(TestMethods.class, "doubleInt", MethodType.methodType(int.class, int.class));
            MethodHandle filtered = MethodHandles.filterArguments(mh, 0, filter);
            int result = (int) filtered.invokeExact(3, 4);
            assert result == 7 : "Expected 7, got " + result;
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    static void testFilterArgumentsWithNullFilter() {
        try {
            MethodHandle mh = MethodHandles.lookup().findStatic(TestMethods.class, "add", MethodType.methodType(int.class, int.class, int.class));
            MethodHandle filtered = MethodHandles.filterArguments(mh, 0, (MethodHandle) null);
            int result = (int) filtered.invokeExact(2, 3);
            assert result == 5 : "Expected 5, got " + result;
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    static void testFilterArgumentsWithEmptyFilters() {
        try {
            MethodHandle mh = MethodHandles.lookup().findStatic(TestMethods.class, "add", MethodType.methodType(int.class, int.class, int.class));
            MethodHandle filtered = MethodHandles.filterArguments(mh, 0);
            int result = (int) filtered.invokeExact(5, 6);
            assert result == 11 : "Expected 11, got " + result;
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    static void testFilterArgumentsWithNullTarget() {
        try {
            MethodHandle filtered = MethodHandles.filterArguments(null, 0, (MethodHandle) null);
            throw new AssertionError("Expected NullPointerException");
        } catch (NullPointerException e) {
            // expected
        }
    }

    static void testFilterArgumentsWithNegativePos() {
        try {
            MethodHandle mh = MethodHandles.lookup().findStatic(TestMethods.class, "add", MethodType.methodType(int.class, int.class, int.class));
            MethodHandle filter = MethodHandles.lookup().findStatic(TestMethods.class, "doubleInt", MethodType.methodType(int.class, int.class));
            MethodHandle filtered = MethodHandles.filterArguments(mh, -1, filter);
            throw new AssertionError("Expected IndexOutOfBoundsException");
        } catch (IndexOutOfBoundsException e) {
            // expected
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    static class TestMethods {
        static int add(int a, int b) {
            return a + b;
        }
        static int doubleInt(int a) {
            return a * 2;
        }
    }
}
