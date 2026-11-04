/*
 * @test    @summary Test the next() method of CopyOnWriteArrayList to ensure it returns elements correctly and throws NoSuchElementException when exhausted.
 * @run main CopyOnWriteArrayListWitupTest
 */

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.NoSuchElementException;

public class CopyOnWriteArrayListWitupTest {

    public static void main(String[] args) {
        testNextReturnsElements();
        testNextThrowsExceptionWhenEmpty();
    }

    static void testNextReturnsElements() {
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        java.util.Iterator<String> iterator = list.iterator();
        String first = iterator.next();
        String second = iterator.next();
        String third = iterator.next();
        assert "a".equals(first) : "Expected 'a', got " + first;
        assert "b".equals(second) : "Expected 'b', got " + second;
        assert "c".equals(third) : "Expected 'c', got " + third;
    }

    static void testNextThrowsExceptionWhenEmpty() {
        CopyOnWriteArrayList<Integer> list = new CopyOnWriteArrayList<>();
        java.util.Iterator<Integer> iterator = list.iterator();
        boolean thrown = false;
        try {
            iterator.next();
        } catch (NoSuchElementException e) {
            thrown = true;
        }
        assert thrown : "Expected NoSuchElementException when calling next() on empty iterator";
    }
}
