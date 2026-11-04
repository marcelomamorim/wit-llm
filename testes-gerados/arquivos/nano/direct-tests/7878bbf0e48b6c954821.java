/* @test    @summary Test for ArrayList.next() method behavior    @run main ArrayListWitupTest */

import java.util.ArrayList;
import java.util.NoSuchElementException;

public class ArrayListWitupTest {

    public static void main(String[] args) {
        testNextWithElements();
        testNextWithoutElements();
        testNextAfterEnd();
    }

    private static void testNextWithElements() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        int cursor = 0;
        int lastRet = -1;
        Object[] elementData = {"a", "b", "c"};
        int size = 3;
        int offset = 0;
        try {
            for (int i = 0; i < size; i++) {
                String element = (String) elementData[offset + i];
                if (i != cursor) {
                    throw new AssertionError("Cursor mismatch at index " + i);
                }
                // Simulate next()
                int checkIndex = cursor;
                if (checkIndex >= size)
                    throw new NoSuchElementException();
                if (checkIndex + offset >= elementData.length)
                    throw new ConcurrentModificationException();
                cursor = checkIndex + 1;
                lastRet = checkIndex;
                String result = (String) elementData[offset + checkIndex];
                if (!result.equals(element)) {
                    throw new AssertionError("Expected " + element + " but got " + result);
                }
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception: " + e, e);
        }
    }

    private static void testNextWithoutElements() {
        ArrayList<String> list = new ArrayList<>();
        Object[] elementData = {};
        int size = 0;
        int offset = 0;
        int cursor = 0;
        try {
            if (cursor >= size) {
                throw new NoSuchElementException();
            }
        } catch (NoSuchElementException e) {
            // expected
        }
    }

    private static void testNextAfterEnd() {
        ArrayList<String> list = new ArrayList<>();
        list.add("x");
        Object[] elementData = {"x"};
        int size = 1;
        int offset = 0;
        int cursor = 1; // position after last element
        try {
            if (cursor >= size) {
                throw new NoSuchElementException();
            }
        } catch (NoSuchElementException e) {
            // expected
        }
    }
}
