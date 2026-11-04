/*
 * @test
 * @summary Test for Collections.forEachRemaining to handle null action.
 * @run main CollectionsWitupTest
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

public class CollectionsWitupTest {

    public static void main(String[] args) {
        testForEachRemainingWithNullAction();
        testForEachRemainingWithValidAction();
    }

    private static void testForEachRemainingWithNullAction() {
        List<Integer> list = Arrays.asList(1, 2, 3);
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
        Iterator<Integer> iterator = list.iterator();
        StringBuilder sb = new StringBuilder();
        Consumer<Integer> consumer = (Integer i) -> sb.append(i);
        try {
            iterator.forEachRemaining(consumer);
            String result = sb.toString();
            if (result.equals("123")) {
                System.out.println("PASS: forEachRemaining applied correctly");
            } else {
                System.out.println("FAIL: Unexpected result: " + result);
            }
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception: " + e);
        }
    }
}
