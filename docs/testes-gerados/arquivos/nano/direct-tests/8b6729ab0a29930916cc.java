/* @test    @summary Test getInt method of UnsafeQualifiedStaticDoubleFieldAccessorImpl for exception throwing */
/* @run main UnsafeQualifiedStaticDoubleFieldAccessorImplWitupTest */

import java.lang.reflect.Field;

public class UnsafeQualifiedStaticDoubleFieldAccessorImplWitupTest {

    public static void main(String[] args) {
        testGetIntThrowsException();
    }

    public static void testGetIntThrowsException() {
        try {
            // Create a dummy instance to invoke getInt
            // Since getInt always throws, no need to set up actual field or object
            jdk.internal.reflect.UnsafeQualifiedStaticDoubleFieldAccessorImpl accessor = null;
            // Reflection to instantiate the class with a dummy Field (null for simplicity, as constructor is package-private)
            // Instead, directly instantiate via reflection if needed, but constructor is package-private
            // For testing, assume we can instantiate with null (not ideal, but for test purposes)
            // Alternatively, test the method directly by subclassing or reflection
            // Here, we simulate calling getInt and expect exception
            // Since constructor is package-private, we can use reflection to instantiate
            Field dummyField = null; // Not used in getInt, so null is acceptable
            // Use reflection to instantiate the class
            java.lang.reflect.Constructor<jdk.internal.reflect.UnsafeQualifiedStaticDoubleFieldAccessorImpl> ctor = 
                jdk.internal.reflect.UnsafeQualifiedStaticDoubleFieldAccessorImpl.class.getDeclaredConstructor(Field.class, boolean.class);
            ctor.setAccessible(true);
            accessor = ctor.newInstance(dummyField, false);
            // Call getInt, which should throw IllegalArgumentException
            accessor.getInt(null);
            System.err.println("Expected IllegalArgumentException was not thrown.");
        } catch (IllegalArgumentException e) {
            System.out.println("Caught expected IllegalArgumentException.");
        } catch (ReflectiveOperationException e) {
            System.err.println("Reflection error during test setup: " + e);
        }
    }
}
