/* @test    @summary Test for FloatingDecimal.digitsRoundedUp() method behavior    @run main FloatingDecimalWitupTest */

import java.lang.reflect.Method;

public class FloatingDecimalWitupTest {

    public static void main(String[] args) throws Exception {
        testDigitsRoundedUpThrowsException();
    }

    private static void testDigitsRoundedUpThrowsException() throws Exception {
        Class<?> cls = Class.forName("jdk.internal.math.FloatingDecimal");
        Method method = cls.getDeclaredMethod("digitsRoundedUp");
        try {
            method.setAccessible(true);
            method.invoke(null);
            // If no exception, test fails
            throw new AssertionError("Expected IllegalArgumentException was not thrown.");
        } catch (java.lang.reflect.InvocationTargetException e) {
            Throwable cause = e.getCause();
            if (!(cause instanceof IllegalArgumentException)) {
                throw new AssertionError("Unexpected exception cause: " + cause, cause);
            }
            if (!cause.getMessage().equals("Exceptional value is not rounded")) {
                throw new AssertionError("Unexpected exception message: " + cause.getMessage());
            }
            // Test passes if exception with correct message is thrown
        }
    }
}
