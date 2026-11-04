/* @test    @summary Test InnocuousThread.setContextClassLoader behavior    @run main InnocuousThreadWitupTest */

import java.security.SecurityException;
import java.lang.ClassLoader;

public class InnocuousThreadWitupTest {

    public static void main(String[] args) {
        testSetContextClassLoaderWithNull();
        testSetContextClassLoaderWithNonNull();
    }

    private static void testSetContextClassLoaderWithNull() {
        Thread thread = new Thread();
        try {
            // Using reflection to invoke setContextClassLoader as it is public
            java.lang.reflect.Method method = thread.getClass().getMethod("setContextClassLoader", ClassLoader.class);
            method.invoke(thread, (Object) null);
            // If no exception, test passes
        } catch (java.lang.reflect.InvocationTargetException e) {
            Throwable cause = e.getCause();
            if (cause instanceof SecurityException) {
                // Expected exception
            } else {
                throw new RuntimeException("Unexpected exception", e);
            }
        } catch (Exception e) {
            throw new RuntimeException("Reflection invocation failed", e);
        }
    }

    private static void testSetContextClassLoaderWithNonNull() {
        Thread thread = new Thread();
        try {
            java.lang.reflect.Method method = thread.getClass().getMethod("setContextClassLoader", ClassLoader.class);
            method.invoke(thread, (Object) ClassLoader.getSystemClassLoader());
            throw new AssertionError("Expected SecurityException was not thrown");
        } catch (java.lang.reflect.InvocationTargetException e) {
            Throwable cause = e.getCause();
            if (cause instanceof SecurityException) {
                // Expected exception, test passes
            } else {
                throw new RuntimeException("Unexpected exception", e);
            }
        } catch (Exception e) {
            throw new RuntimeException("Reflection invocation failed", e);
        }
    }
}
