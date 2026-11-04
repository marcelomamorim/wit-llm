/*
 * @test
 * @summary Test for ArrayList.add(E e) method, including exception path for ConcurrentModificationException.
 * @run main ArrayListWitupTest
 */

import java.util.ArrayList;

public class ArrayListWitupTest {

    public static void main(String[] args) {
        testAddNormal();
        testAddConcurrentModification();
    }

    static void testAddNormal() {
        ArrayList<String> list = new ArrayList<>();
        list.add("element");
        if (!list.contains("element")) {
            throw new AssertionError("Failed: element not added properly.");
        }
    }

    static void testAddConcurrentModification() {
        ArrayList<String> list = new ArrayList<>();
        list.add("initial");
        ArrayList<String>.ListItr itr = list.new ListItr();
        list.add("another");
        try {
            itr.add("test");
            throw new AssertionError("Expected ConcurrentModificationException was not thrown.");
        } catch (ConcurrentModificationException e) {
            // Expected exception
        }
    }
}
