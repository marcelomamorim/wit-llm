/* @test    @summary Test for ClassLoader.nextElement() method to verify exception behavior and normal operation    @run main ClassLoaderWitupTest */

import java.lang.ClassLoader;
import java.util.NoSuchElementException;

public class ClassLoaderWitupTest {

    public static void main(String[] args) {
        testNextElementNormal();
        testNextElementThrows();
    }

    private static void testNextElementNormal() {
        // Create a dummy enumeration with one element
        Enumeration<Object> enumInstance = new Enumeration<Object>() {
            private boolean hasNext = true;
            @Override
            public boolean hasMoreElements() {
                return hasNext;
            }
            @Override
            public Object nextElement() {
                if (hasNext) {
                    hasNext = false;
                    return "element";
                } else {
                    throw new NoSuchElementException();
                }
            }
        };
        // Create a subclass of ClassLoader with overridden enums array
        ClassLoader loader = new ClassLoader() {
            private final Enumeration<Object>[] enums = new Enumeration[] { enumInstance };
            private int index = 0;
            @Override
            public Enumeration<Object>[] getEnvironments() {
                return enums;
            }
            // Override nextElement to use the actual method
            public Object invokeNextElement() {
                return new ClassLoader().nextElement();
            }
        };
        // Use reflection to set the enums array and index if needed
        // But since enums is private, we simulate the call directly
        try {
            // Call nextElement directly
            Object result = loader.nextElement();
            if (!"element".equals(result)) {
                throw new AssertionError("Expected 'element', got: " + result);
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception: " + e);
        }
    }

    private static void testNextElementThrows() {
        // Create an enumeration that throws NoSuchElementException on nextElement
        Enumeration<Object> enumInstance = new Enumeration<Object>() {
            @Override
            public boolean hasMoreElements() {
                return false;
            }
            @Override
            public Object nextElement() {
                throw new NoSuchElementException();
            }
        };
        // Create a loader with this enumeration
        ClassLoader loader = new ClassLoader() {
            private final Enumeration<Object>[] enums = new Enumeration[] { enumInstance };
            private int index = 0;
            @Override
            public Enumeration<Object>[] getEnvironments() {
                return enums;
            }
            public Object invokeNextElement() {
                return new ClassLoader().nextElement();
            }
        };
        // Expect NoSuchElementException
        try {
            loader.nextElement();
            throw new AssertionError("Expected NoSuchElementException to be thrown");
        } catch (NoSuchElementException e) {
            // Expected exception
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception: " + e);
        }
    }
}
