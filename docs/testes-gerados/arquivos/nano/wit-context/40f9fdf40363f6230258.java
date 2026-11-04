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
        testFilterArgumentsNormal();
        testFilterArgumentsNullFilter();
        testFilterArgumentsExceedFilters();
    }

    static void testFilterArgumentsNormal() {
        try {
            MethodHandle mh = MethodHandles.lookup().findStatic(TestClass.class, "sampleMethod", MethodType.methodType(void.class));
            MethodHandle filter = MethodHandles.lookup().findStatic(TestClass.class, "filterMethod", MethodType.methodType(void.class));
            MethodHandle result = MethodHandles.filterArguments(mh, 0, filter);
            if (result == null) {
                System.err.println("testFilterArgumentsNormal failed: result is null");
            }
        } catch (NoSuchMethodException | IllegalAccessException e) {
            System.err.println("testFilterArgumentsNormal failed: " + e);
        }
    }

    static void testFilterArgumentsNullFilter() {
        try {
            MethodHandle mh = MethodHandles.lookup().findStatic(TestClass.class, "sampleMethod", MethodType.methodType(void.class));
            MethodHandle result = MethodHandles.filterArguments(mh, 0);
            if (result == null) {
                System.err.println("testFilterArgumentsNullFilter failed: result is null");
            }
        } catch (NoSuchMethodException | IllegalAccessException e) {
            System.err.println("testFilterArgumentsNullFilter failed: " + e);
        }
    }

    static void testFilterArgumentsExceedFilters() {
        try {
            MethodHandle mh = MethodHandles.lookup().findStatic(TestClass.class, "sampleMethod", MethodType.methodType(void.class));
            MethodHandle filter1 = MethodHandles.lookup().findStatic(TestClass.class, "filterMethod", MethodType.methodType(void.class));
            MethodHandle filter2 = null; // null filter to simulate null element
            MethodHandle[] filters = new MethodHandle[] {filter1, filter2};
            MethodHandle result = MethodHandles.filterArguments(mh, 0, filters);
            if (result == null) {
                System.err.println("testFilterArgumentsExceedFilters failed: result is null");
            }
        } catch (NoSuchMethodException | IllegalAccessException e) {
            System.err.println("testFilterArgumentsExceedFilters failed: " + e);
        }
    }

    public static class TestClass {
        public static void sampleMethod() {
            // sample method
        }
        public static void filterMethod() {
            // filter method
        }
    }
}
