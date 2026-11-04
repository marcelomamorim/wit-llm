/*
 * @test
 * @summary Test for LinkedList.forEachRemaining with null Consumer
 * @run main LinkedListWitupTest
 */

import java.util.LinkedList;
import java.util.function.Consumer;

public class LinkedListWitupTest {

    public static void main(String[] args) {
        testForEachRemainingWithNullConsumer();
        testForEachRemainingWithValidConsumer();
    }

    private static void testForEachRemainingWithNullConsumer() {
        LinkedList<Integer> list = new LinkedList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        try {
            list.forEachRemaining(null);
            System.out.println("FAIL: Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown as expected");
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception: " + e);
        }
    }

    private static void testForEachRemainingWithValidConsumer() {
        LinkedList<Integer> list = new LinkedList<>();
        list.add(10);
        list.add(20);
        list.add(30);
        StringBuilder sb = new StringBuilder();
        Consumer<Integer> consumer = e -> sb.append(e).append(",");
        list.forEachRemaining(consumer);
        String result = sb.toString();
        if (result.equals("10,20,30,")) {
            System.out.println("PASS: Consumer processed all elements correctly");
        } else {
            System.out.println("FAIL: Unexpected processing result: " + result);
        }
    }
}
