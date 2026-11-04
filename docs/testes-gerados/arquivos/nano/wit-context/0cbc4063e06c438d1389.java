/*
 * @test
 * @summary Tests for ArrayList.tryAdvance method to verify exception paths and behavior.
 * @run main ArrayListWitupTest
 */

import java.util.ArrayList;
import java.util.function.Consumer;

public class ArrayListWitupTest {

    public static void main(String[] args) {
        testTryAdvanceNullAction();
        testTryAdvanceNormal();
        testTryAdvanceConcurrentModification();
    }

    public static void testTryAdvanceNullAction() {
        ArrayList<String> list = new ArrayList<>();
        list.add("element");
        try {
            list.tryAdvance(null);
            System.out.println("testTryAdvanceNullAction: Failed - Expected NullPointerException");
        } catch (NullPointerException e) {
            System.out.println("testTryAdvanceNullAction: Passed");
        } catch (Throwable t) {
            System.out.println("testTryAdvanceNullAction: Failed - Unexpected exception: " + t);
        }
    }

    public static void testTryAdvanceNormal() {
        ArrayList<String> list = new ArrayList<>();
        list.add("element");
        Consumer<String> consumer = s -> {
            // Consume element
        };
        boolean result = list.tryAdvance(consumer);
        if (result) {
            System.out.println("testTryAdvanceNormal: Passed");
        } else {
            System.out.println("testTryAdvanceNormal: Failed - Expected true");
        }
    }

    public static void testTryAdvanceConcurrentModification() {
        ArrayList<String> list = new ArrayList<>();
        list.add("element1");
        list.add("element2");
        Consumer<String> consumer = s -> {
            // Consume element
        };
        try {
            // Simulate concurrent modification
            list.getClass().getDeclaredField("modCount").setAccessible(true);
            list.getClass().getDeclaredField("modCount").setInt(list, 1);
            list.tryAdvance(consumer);
            System.out.println("testTryAdvanceConcurrentModification: Failed - Expected ConcurrentModificationException");
        } catch (java.util.ConcurrentModificationException e) {
            System.out.println("testTryAdvanceConcurrentModification: Passed");
        } catch (Throwable t) {
            System.out.println("testTryAdvanceConcurrentModification: Failed - Unexpected exception: " + t);
        }
    }
}
