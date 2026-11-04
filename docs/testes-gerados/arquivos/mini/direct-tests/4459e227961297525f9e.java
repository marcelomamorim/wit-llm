/*
 * @test
 * @summary Test MethodHandles.findClass behavior
 * @run main MethodHandlesWitupTest
 */

import java.lang.invoke.MethodHandles;

public class MethodHandlesWitupTest {
    public static void main(String[] args) throws Throwable {
        MethodHandles mh = new MethodHandles();

        // Test with a valid class name
        Class<?> cls = mh.findClass("java.lang.String");
        if (cls != String.class) {
            throw new AssertionError("Expected java.lang.String class");
        }

        // Test with a class that does not exist - expect ClassNotFoundException
        try {
            mh.findClass("non.existent.ClassName");
            throw new AssertionError("Expected ClassNotFoundException for non.existent.ClassName");
        } catch (ClassNotFoundException e) {
            // expected
        }

        // Test with a class that is not accessible - simulate by using a class loader that cannot load it
        // Since the method uses lookupClass.getClassLoader(), and lookupClass is not visible here,
        // we cannot simulate IllegalAccessException directly without reflection or internal access.
        // So we skip direct test of IllegalAccessException as no public way to trigger it is visible.
    }
}
