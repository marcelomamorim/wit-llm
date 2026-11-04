/*
 * @test
 * @summary Test ClassLoader.nextElement() behavior including NoSuchElementException
 * @run main ClassLoaderWitupTest
 */

import java.util.NoSuchElementException;
import java.lang.ClassLoader;

public class ClassLoaderWitupTest {

    // Minimal subclass to expose nextElement() for testing
    static class TestClassLoader extends ClassLoader {
        private java.util.Enumeration<String>[] enums;
        private int index = 0;

        @SuppressWarnings("unchecked")
        TestClassLoader() {
            // Create two enumerations for testing
            enums = new java.util.Enumeration[2];
            enums[0] = java.util.Collections.enumeration(java.util.Arrays.asList("a", "b"));
            enums[1] = java.util.Collections.enumeration(java.util.Arrays.asList("c"));
        }

        // next() method to advance index and check if more elements exist
        boolean next() {
            while (index < enums.length) {
                if (enums[index].hasMoreElements()) {
                    return true;
                } else {
                    index++;
                }
            }
            return false;
        }

        // Expose nextElement() as per ClassLoader source
        public String nextElement() {
            if (!next()) {
                throw new NoSuchElementException();
            }
            return enums[index].nextElement();
        }
    }

    public static void main(String[] args) {
        TestClassLoader loader = new TestClassLoader();

        // Consume all elements and verify order
        String[] expected = {"a", "b", "c"};
        for (int i = 0; i < expected.length; i++) {
            String elem = loader.nextElement();
            if (!expected[i].equals(elem)) {
                throw new AssertionError("Expected element '" + expected[i] + "' but got '" + elem + "'");
            }
        }

        // After all elements consumed, nextElement() should throw NoSuchElementException
        boolean threw = false;
        try {
            loader.nextElement();
        } catch (NoSuchElementException e) {
            threw = true;
        }
        if (!threw) {
            throw new AssertionError("Expected NoSuchElementException when no elements remain");
        }

        System.out.println("TestClassLoader.nextElement() passed all tests.");
    }
}
