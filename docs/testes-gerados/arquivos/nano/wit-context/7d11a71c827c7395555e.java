/* @test    @summary Test for LinkedList.pop() method to verify exception on empty list    @run main LinkedListWitupTest */

import java.util.LinkedList;

public class LinkedListWitupTest {

    public static void main(String[] args) {
        testPopOnEmptyList();
    }

    public static void testPopOnEmptyList() {
        LinkedList<Object> list = new LinkedList<>();
        try {
            list.pop();
            System.err.println("Expected NoSuchElementException was not thrown.");
            System.exit(1);
        } catch (NoSuchElementException e) {
            // Expected exception caught
            System.out.println("Caught expected NoSuchElementException.");
        } catch (Throwable t) {
            System.err.println("Unexpected exception: " + t);
            System.exit(1);
        }
    }
}
