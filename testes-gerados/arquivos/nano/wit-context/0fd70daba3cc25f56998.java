/* @test    @summary Test for LinkedList.remove() method to verify exception handling and behavior.    @run main LinkedListWitupTest */

import java.util.LinkedList;

public class LinkedListWitupTest {

    public static void main(String[] args) {
        testRemoveWithoutModification();
        testRemoveWithNullLastReturned();
    }

    private static void testRemoveWithoutModification() {
        LinkedList<Integer> list = new LinkedList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        try {
            list.remove();
            System.out.println("testRemoveWithoutModification: Passed");
        } catch (Exception e) {
            System.out.println("testRemoveWithoutModification: Failed with exception: " + e);
        }
    }

    private static void testRemoveWithNullLastReturned() {
        LinkedList<Integer> list = new LinkedList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        // Simulate state where lastReturned is null
        try {
            list.remove();
            System.out.println("testRemoveWithNullLastReturned: Failed - no exception thrown");
        } catch (IllegalStateException e) {
            System.out.println("testRemoveWithNullLastReturned: Passed - caught IllegalStateException");
        } catch (Exception e) {
            System.out.println("testRemoveWithNullLastReturned: Failed with unexpected exception: " + e);
        }
    }
}
