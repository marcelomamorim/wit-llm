/* @test    @summary Test the next() method of LinkedList for correct element retrieval.    @run main LinkedListWitupTest */

import java.util.LinkedList;

public class LinkedListWitupTest {

    public static void main(String[] args) {
        testNextReturnsCorrectElement();
    }

    public static void testNextReturnsCorrectElement() {
        LinkedList<String> list = new LinkedList<>();
        list.add("first");
        list.add("second");
        list.add("third");
        java.util.ListIterator<String> itr = list.listIterator();
        String firstElement = itr.next();
        if (!"first".equals(firstElement)) {
            throw new AssertionError("Expected 'first', but got: " + firstElement);
        }
        String secondElement = itr.next();
        if (!"second".equals(secondElement)) {
            throw new AssertionError("Expected 'second', but got: " + secondElement);
        }
        String thirdElement = itr.next();
        if (!"third".equals(thirdElement)) {
            throw new AssertionError("Expected 'third', but got: " + thirdElement);
        }
    }
}
