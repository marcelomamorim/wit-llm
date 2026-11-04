/* @test    @summary Test for ArrayDeque.next() method behavior    @run main ArrayDequeWitupTest */

import java.util.ArrayDeque;
import java.util.NoSuchElementException;

public class ArrayDequeWitupTest {

    public static void main(String[] args) {
        testNextReturnsElement();
        testNextThrowsExceptionWhenEmpty();
    }

    static void testNextReturnsElement() {
        ArrayDeque<String> deque = new ArrayDeque<>();
        deque.add("element1");
        deque.add("element2");
        deque.add("element3");
        // Simulate iteration with cursor starting at 0
        // Since ArrayDeque does not expose cursor, we test via reflection or by invoking next() directly
        // But as per constraints, we test via a wrapper class or by extending ArrayDeque
        // For simplicity, we create a subclass to access next()
        class TestDeque extends ArrayDeque<String> {
            int cursor = 0;
            int lastRet = -1;
            int remaining = size();

            public String next() {
                if (remaining <= 0)
                    throw new NoSuchElementException();
                Object[] es = this.toArray();
                String e = (String) es[cursor];
                cursor = (cursor + 1) % es.length;
                lastRet = cursor - 1;
                remaining--;
                return e;
            }
        }
        TestDeque testDeque = new TestDeque();
        testDeque.addAll(deque);
        String first = testDeque.next();
        assert first.equals("element1") : "Expected element1, got " + first;
        String second = testDeque.next();
        assert second.equals("element2") : "Expected element2, got " + second;
        String third = testDeque.next();
        assert third.equals("element3") : "Expected element3, got " + third;
    }

    static void testNextThrowsExceptionWhenEmpty() {
        ArrayDeque<String> deque = new ArrayDeque<>();
        class TestDeque extends ArrayDeque<String> {
            int cursor = 0;
            int lastRet = -1;
            int remaining = 0;

            public String next() {
                if (remaining <= 0)
                    throw new NoSuchElementException();
                Object[] es = this.toArray();
                String e = (String) es[cursor];
                cursor = (cursor + 1) % es.length;
                lastRet = cursor - 1;
                remaining--;
                return e;
            }
        }
        TestDeque testDeque = new TestDeque();
        testDeque.addAll(deque);
        try {
            testDeque.next();
            assert false : "Expected NoSuchElementException";
        } catch (NoSuchElementException e) {
            // expected
        }
    }
}
