/*
 * @test
 * @summary Test ClassLoader.nextElement() behavior
 * @run main ClassLoaderWitupTest
 */

import java.util.NoSuchElementException;
import java.util.Vector;
import java.util.Enumeration;

public class ClassLoaderWitupTest {

    static class TestEnumeration<E> implements Enumeration<E> {
        private final E[] elements;
        private int pos = 0;

        @SafeVarargs
        TestEnumeration(E... elements) {
            this.elements = elements;
        }

        public boolean hasMoreElements() {
            return pos < elements.length;
        }

        public E nextElement() {
            if (!hasMoreElements()) {
                throw new NoSuchElementException();
            }
            return elements[pos++];
        }
    }

    static class TestClassLoader {
        // Mimic minimal fields and methods to test nextElement()
        Enumeration<String>[] enums;
        int index = 0;

        TestClassLoader(Enumeration<String>[] enums) {
            this.enums = enums;
        }

        boolean next() {
            if (enums == null) return false;
            while (index < enums.length) {
                if (enums[index] != null && enums[index].hasMoreElements()) {
                    return true;
                }
                index++;
            }
            return false;
        }

        String nextElement() {
            if (!next()) {
                throw new NoSuchElementException();
            }
            return enums[index].nextElement();
        }
    }

    public static void main(String[] args) {
        testNextElementReturnsCorrectValue();
        testNextElementThrowsWhenNoMoreElements();
    }

    private static void testNextElementReturnsCorrectValue() {
        String[] arr1 = {"a", "b"};
        String[] arr2 = {"c"};
        Enumeration<String> e1 = new TestEnumeration<>(arr1);
        Enumeration<String> e2 = new TestEnumeration<>(arr2);
        @SuppressWarnings("unchecked")
        Enumeration<String>[] enums = (Enumeration<String>[]) new Enumeration<?>[] {e1, e2};

        TestClassLoader loader = new TestClassLoader(enums);

        // Should return "a"
        String first = loader.nextElement();
        if (!"a".equals(first)) {
            throw new AssertionError("Expected 'a' but got " + first);
        }

        // Should return "b"
        String second = loader.nextElement();
        if (!"b".equals(second)) {
            throw new AssertionError("Expected 'b' but got " + second);
        }

        // Should return "c"
        String third = loader.nextElement();
        if (!"c".equals(third)) {
            throw new AssertionError("Expected 'c' but got " + third);
        }
    }

    private static void testNextElementThrowsWhenNoMoreElements() {
        Enumeration<String> emptyEnum = new Vector<String>().elements();
        @SuppressWarnings("unchecked")
        Enumeration<String>[] enums = (Enumeration<String>[]) new Enumeration<?>[] {emptyEnum};

        TestClassLoader loader = new TestClassLoader(enums);

        boolean thrown = false;
        try {
            loader.nextElement();
        } catch (NoSuchElementException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected NoSuchElementException when no elements available");
        }
    }
}
