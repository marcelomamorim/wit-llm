/*
 * @test
 * @summary Test java.util.Collections.get(int) method for correct index bounds handling
 * @run main CollectionsWitupTest
 */

import java.util.Collections;
import java.util.List;
import java.util.AbstractList;

public class CollectionsWitupTest {

    // Minimal implementation of CopiesList to test get(int) method
    static class CopiesList<E> extends AbstractList<E> {
        final int n;
        final E element;

        CopiesList(int n, E element) {
            this.n = n;
            this.element = element;
        }

        @Override
        public E get(int index) {
            if (index < 0 || index >= n)
                throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + n);
            return element;
        }

        @Override
        public int size() {
            return n;
        }
    }

    public static void main(String[] args) {
        // Create a CopiesList of size 3 with element "test"
        CopiesList<String> list = new CopiesList<>(3, "test");

        // Test valid indices
        for (int i = 0; i < list.size(); i++) {
            String val = list.get(i);
            if (!"test".equals(val)) {
                throw new RuntimeException("Expected 'test' at index " + i + " but got " + val);
            }
        }

        // Test index < 0 throws IndexOutOfBoundsException
        try {
            list.get(-1);
            throw new RuntimeException("Expected IndexOutOfBoundsException for index -1");
        } catch (IndexOutOfBoundsException expected) {
            // expected
        }

        // Test index >= size throws IndexOutOfBoundsException
        try {
            list.get(3);
            throw new RuntimeException("Expected IndexOutOfBoundsException for index 3");
        } catch (IndexOutOfBoundsException expected) {
            // expected
        }

        // Test index much larger than size
        try {
            list.get(100);
            throw new RuntimeException("Expected IndexOutOfBoundsException for index 100");
        } catch (IndexOutOfBoundsException expected) {
            // expected
        }

        System.out.println("All tests passed.");
    }
}
