/* @test    @summary Test getShort method of UnsafeQualifiedStaticIntegerFieldAccessorImpl    @run main UnsafeQualifiedStaticIntegerFieldAccessorImplWitupTest */

import java.lang.reflect.Field;

public class UnsafeQualifiedStaticIntegerFieldAccessorImplWitupTest {

    public static void main(String[] args) throws Exception {
        testGetShortReturnsException();
    }

    private static void testGetShortReturnsException() {
        try {
            // Create an instance of the class under test
            Class<?> clazz = Class.forName("jdk.internal.reflect.UnsafeQualifiedStaticIntegerFieldAccessorImpl");
            // Obtain the getShort method
            java.lang.reflect.Method method = clazz.getDeclaredMethod("getShort", Object.class);
            // Invoke the method with null (since it does not use the parameter)
            method.invoke(clazz.getDeclaredConstructor(Field.class, boolean.class).newInstance(null, false), (Object) null);
            // If no exception, test fails
            System.out.println("FAIL: getShort did not throw exception as expected.");
        } catch (java.lang.reflect.InvocationTargetException e) {
            Throwable cause = e.getCause();
            if (cause instanceof IllegalArgumentException) {
                System.out.println("PASS: getShort threw IllegalArgumentException as expected.");
            } else {
                System.out.println("FAIL: getShort threw unexpected exception: " + cause);
            }
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception: " + e);
        }
    }
}
