/*
 * @test
 * @summary Test for Collections.forEachRemaining with null action
 * @run main CollectionsWitupTest
 */

import java.util.Collections;
import java.util.Iterator;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class CollectionsWitupTest {

    public static void main(String[] args) {
        testForEachRemainingNullAction();
        testForEachRemainingValidAction();
    }

    private static void testForEachRemainingNullAction() {
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

    private static void testForEachRemainingValidAction() {
        List<Integer> list = Arrays.asList(4, 5, 6);
        StringBuilder sb = new StringBuilder();
        Consumer<Integer> consumer = (Integer x) -> sb.append(x).append(",");
        Iterator<Integer> iterator = list.iterator();
        try {
            iterator.forEachRemaining(consumer);
            String result = sb.toString();
            if (result.equals("4,5,6,")) {
                System.out.println("PASS: forEachRemaining applied correctly");
            } else {
                System.out.println("FAIL: Unexpected result: " + result);
            }
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception: " + e);
        }
    }
}
