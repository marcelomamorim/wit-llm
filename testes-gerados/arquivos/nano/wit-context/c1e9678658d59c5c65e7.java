/*
 * @test
 * @summary Test for Collections.forEachRemaining with null Consumer
 * @run main CollectionsWitupTest
 */

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

public class CollectionsWitupTest {

    public static void main(String[] args) {
        testForEachRemainingWithNullAction();
        testForEachRemainingWithValidAction();
    }

    private static void testForEachRemainingWithNullAction() {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        Iterator<Integer> iterator = list.iterator();
        try {
            iterator.forEachRemaining(null);
            System.out.println("FAIL: Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown as expected");
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception: " + e);
        }
    }

    private static void testForEachRemainingWithValidAction() {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        List<Integer> result = new ArrayList<>();
        Iterator<Integer> iterator = list.iterator();
        Consumer<Integer> consumer = result::add;
        try {
            iterator.forEachRemaining(consumer);
            if (result.size() == 3 && result.containsAll(list)) {
                System.out.println("PASS: forEachRemaining executed successfully");
            } else {
                System.out.println("FAIL: forEachRemaining did not process all elements");
            }
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception: " + e);
        }
    }
}
