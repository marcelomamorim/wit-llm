/*
 * @test
 * @summary Test the nextElement() method of ClassLoader for correct iteration and exception handling.
 * @run main ClassLoaderWitupTest
 */

import java.lang.ClassLoader;
import java.util.NoSuchElementException;

public class ClassLoaderWitupTest {

    public static void main(String[] args) {
        testNextElementReturnsElement();
        testNextElementThrowsExceptionWhenNoMoreElements();
    }

    private static void testNextElementReturnsElement() {
        // Create a dummy ClassLoader with a simple enumeration for testing
        Enumeration<ClassLoader> enumeration = new Enumeration<>() {
            private int count = 0;
            @Override
            public boolean hasMoreElements() {
                return count < 1;
            }
            @Override
            public ClassLoader nextElement() {
                if (!hasMoreElements()) {
                    throw new NoSuchElementException();
                }
                count++;
                return new ClassLoader() {
                    @Override
                    public String toString() {
                        return "TestClassLoader";
                    }
                };
            }
        };
        // Instantiate ClassLoader with the enumeration
        ClassLoader loader = new ClassLoader() {
            private final Enumeration<ClassLoader> enums = enumeration;
            private int index = 0;
            @Override
            public boolean next() {
                return enums.hasMoreElements();
            }
            @Override
            public ClassLoader nextElement() {
                if (!next()) {
                    throw new NoSuchElementException();
                }
                return enums.nextElement();
            }
        };
        // Call nextElement and verify it returns the expected object
        ClassLoader result = loader.nextElement();
        if (result == null || !"TestClassLoader".equals(result.toString())) {
            throw new AssertionError("Expected TestClassLoader, got: " + result);
        }
        System.out.println("testNextElementReturnsElement passed.");
    }

    private static void testNextElementThrowsExceptionWhenNoMoreElements() {
        // Create an enumeration that has no elements
        Enumeration<ClassLoader> emptyEnum = new Enumeration<>() {
            @Override
            public boolean hasMoreElements() {
                return false;
            }
            @Override
            public ClassLoader nextElement() {
                throw new NoSuchElementException();
            }
        };
        // Instantiate ClassLoader with the empty enumeration
        ClassLoader loader = new ClassLoader() {
            private final Enumeration<ClassLoader> enums = emptyEnum;
            @Override
            public boolean next() {
                return enums.hasMoreElements();
            }
            @Override
            public ClassLoader nextElement() {
                if (!next()) {
                    throw new NoSuchElementException();
                }
                return enums.nextElement();
            }
        };
        // Expect NoSuchElementException when calling nextElement
        boolean exceptionThrown = false;
        try {
            loader.nextElement();
        } catch (NoSuchElementException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected NoSuchElementException to be thrown.");
        }
        System.out.println("testNextElementThrowsExceptionWhenNoMoreElements passed.");
    }
}
