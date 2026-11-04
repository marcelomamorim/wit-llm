/*
 * @test    @summary Test the previous() method of CopyOnWriteArrayList for correct behavior.
 * @run main CopyOnWriteArrayListWitupTest
 */

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.NoSuchElementException;

public class CopyOnWriteArrayListWitupTest {

    public static void main(String[] args) {
        testPreviousWithElements();
        testPreviousWithoutElements();
        testPreviousAtBeginning();
    }

    static void testPreviousWithElements() {
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        // Access internal cursor via reflection or assume cursor is at end
        // Since previous() uses internal snapshot and cursor, simulate by calling previous() after iteration
        // For testing, we can simulate by calling previous() after setting cursor to end
        // But since cursor is internal, we test by calling previous() after moving to end
        // For simplicity, we assume previous() is called after moving cursor to end
        // So, we simulate by calling previous() multiple times
        try {
            // First call: should return "c"
            String val1 = list.previous();
            assert "c".equals(val1) : "Expected 'c', got " + val1;
            // Second call: should return "b"
            String val2 = list.previous();
            assert "b".equals(val2) : "Expected 'b', got " + val2;
            // Third call: should return "a"
            String val3 = list.previous();
            assert "a".equals(val3) : "Expected 'a', got " + val3;
        } catch (NoSuchElementException e) {
            assert false : "Did not expect NoSuchElementException here";
        }
    }

    static void testPreviousWithoutElements() {
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();
        try {
            list.previous();
            assert false : "Expected NoSuchElementException";
        } catch (NoSuchElementException e) {
            // expected
        }
    }

    static void testPreviousAtBeginning() {
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();
        list.add("x");
        // Move cursor to start by calling previous() after resetting cursor
        // Since internal cursor is not accessible, simulate by calling previous() after adding element
        // But previous() depends on internal cursor position, which is not directly accessible
        // For testing, we assume previous() is called at start, which should throw
        try {
            list.previous();
            assert false : "Expected NoSuchElementException";
        } catch (NoSuchElementException e) {
            // expected
        }
    }
}
