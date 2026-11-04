/*
 * @test
 * @summary Verify LinkedList.pop() removes the head element and throws NoSuchElementException when the list is empty
 * @run main LinkedListWitupTest
 */

import java.util.LinkedList;
import java.util.NoSuchElementException;

public class LinkedListWitupTest {
    public static void main(String[] args) {
        LinkedList<String> empty = new LinkedList<>();
        boolean thrown = false;
        try {
            empty.pop();
        } catch (NoSuchElementException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("pop() on empty list must throw NoSuchElementException");
        }
        if (!empty.isEmpty()) {
            throw new AssertionError("empty list must remain empty after failed pop()");
        }

        LinkedList<String> list = new LinkedList<>();
        list.push("a");
        list.push("b");
        String first = list.pop();
        if (!"b".equals(first)) {
            throw new AssertionError("pop() must return the head element, got " + first);
        }
        if (list.size() != 1) {
            throw new AssertionError("pop() must remove exactly one element, size=" + list.size());
        }
        String second = list.pop();
        if (!"a".equals(second)) {
            throw new AssertionError("second pop() must return \"a\", got " + second);
        }
        if (!list.isEmpty()) {
            throw new AssertionError("list must be empty after popping all elements");
        }

        LinkedList<String> drained = new LinkedList<>();
        drained.add("x");
        drained.pop();
        thrown = false;
        try {
            drained.pop();
        } catch (NoSuchElementException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("pop() after draining the list must throw NoSuchElementException");
        }
    }
}
