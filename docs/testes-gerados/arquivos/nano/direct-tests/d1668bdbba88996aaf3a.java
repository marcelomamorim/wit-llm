/* @test    @summary Test getDouble method throws IllegalArgumentException as per implementation    @run main UnsafeQualifiedStaticBooleanFieldAccessorImplWitupTest */

import java.lang.reflect.Field;

public class UnsafeQualifiedStaticBooleanFieldAccessorImplWitupTest {

    public static void main(String[] args) {
        testGetDoubleThrowsException();
    }

    public static void testGetDoubleThrowsException() {
        try {
            // Create a dummy instance to invoke getDouble
            // Since getDouble always throws, the input object is irrelevant
            jdk.internal.reflect.UnsafeQualifiedStaticBooleanFieldAccessorImpl accessor = null;
            // Reflection to instantiate the class with dummy parameters
            Field dummyField = null;
            // We cannot instantiate the class directly as constructor is package-private,
            // but for the purpose of this test, we assume an instance exists or mock behavior.
            // Since the method always throws, we can invoke it via reflection.
            // Alternatively, create a subclass or use reflection to access the method.
            // Here, we invoke via reflection.
            Class<?> clazz = Class.forName("jdk.internal.reflect.UnsafeQualifiedStaticBooleanFieldAccessorImpl");
            java.lang.reflect.Method getDoubleMethod = clazz.getDeclaredMethod("getDouble", Object.class);
            getDoubleMethod.setAccessible(true);
            // Invoke with null or dummy object
            getDoubleMethod.invoke(null, (Object) null);
            // If no exception, test fails
            System.out.println("FAIL: getDouble did not throw IllegalArgumentException");
        } catch (java.lang.reflect.InvocationTargetException e) {
            Throwable cause = e.getCause();
            if (cause instanceof IllegalArgumentException) {
                System.out.println("PASS: getDouble threw IllegalArgumentException as expected");
            } else {
                System.out.println("FAIL: getDouble threw unexpected exception: " + cause);
            }
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception: " + e);
        }
    }
}
