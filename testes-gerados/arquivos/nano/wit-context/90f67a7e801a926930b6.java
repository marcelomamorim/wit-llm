/*
 * @test
 * @summary Test addReads method for null Module argument.
 * @run main SystemWitupTest
 */

import java.lang.Module;

public class SystemWitupTest {

    public static void main(String[] args) {
        testAddReadsWithNullModule();
    }

    public static void testAddReadsWithNullModule() {
        java.lang.System system = null; // Using System class directly
        Module m1 = null;
        Module m2 = null;
        try {
            // Reflection to invoke addReads, since it's a final class with static method
            java.lang.reflect.Method addReadsMethod = java.lang.System.class.getDeclaredMethod("addReads", Module.class, Module.class);
            addReadsMethod.setAccessible(true);
            addReadsMethod.invoke(system, m1, m2);
            // If no exception, test fails because we expect NullPointerException
            System.out.println("FAIL: Expected NullPointerException was not thrown.");
        } catch (java.lang.reflect.InvocationTargetException e) {
            Throwable cause = e.getCause();
            if (cause instanceof NullPointerException) {
                System.out.println("PASS: NullPointerException thrown as expected.");
            } else {
                System.out.println("FAIL: Unexpected exception: " + cause);
            }
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception during reflection: " + e);
        }
    }
}
