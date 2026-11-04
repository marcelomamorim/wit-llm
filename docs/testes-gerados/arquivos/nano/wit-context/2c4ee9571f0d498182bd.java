/* @test    @summary Test for LinkedList.next() method, covering normal and exceptional paths    @run main LinkedListWitupTest */

import java.util.LinkedList;

public class LinkedListWitupTest {

    public static void main(String[] args) {
        testNextNormal();
        testNextThrowsNoSuchElementException();
        testNextThrowsConcurrentModificationException();
    }

    private static void testNextNormal() {
        LinkedList<String> list = new LinkedList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        LinkedList<String>.ListItr iterator = list.new ListItr(0);
        try {
            String first = iterator.next();
            String second = iterator.next();
            String third = iterator.next();
            if (!"a".equals(first) || !"b".equals(second) || !"c".equals(third)) {
                throw new AssertionError("Unexpected elements returned by next()");
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in normal next() test", e);
        }
    }

    private static void testNextThrowsNoSuchElementException() {
        LinkedList<String> list = new LinkedList<>();
        list.add("a");
        LinkedList<String>.ListItr iterator = list.new ListItr(1); // position after last element
        try {
            iterator.next();
            throw new AssertionError("Expected NoSuchElementException was not thrown");
        } catch (NoSuchElementException e) {
            // expected
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in NoSuchElementException test", e);
        }
    }

    private static void testNextThrowsConcurrentModificationException() {
        LinkedList<String> list = new LinkedList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        LinkedList<String>.ListItr iterator = list.new ListItr(0);
        list.add("d"); // modify list after iterator creation
        try {
            iterator.next();
            throw new AssertionError("Expected ConcurrentModificationException was not thrown");
        } catch (java.util.ConcurrentModificationException e) {
            // expected
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in ConcurrentModificationException test", e);
        }
    }
}
