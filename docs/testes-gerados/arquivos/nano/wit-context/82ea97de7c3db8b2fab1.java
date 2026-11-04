/*
 * @test
 * @summary Test for ArrayList.forEachRemaining with null Consumer
 * @run main ArrayListWitupTest
 */

import java.util.ArrayList;
import java.util.function.Consumer;

public class ArrayListWitupTest {

    public static void main(String[] args) {
        testForEachRemainingWithNullConsumer();
        testForEachRemainingWithValidConsumer();
    }

    private static void testForEachRemainingWithNullConsumer() {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        ArrayList<Integer>.Itr iterator = list.new Itr();
        iterator.cursor = 0;
        try {
            iterator.forEachRemaining(null);
            System.out.println("FAIL: Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown as expected");
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception: " + e);
        }
    }

    private static void testForEachRemainingWithValidConsumer() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        ArrayList<String>.Itr iterator = list.new Itr();
        iterator.cursor = 0;
        StringBuilder sb = new StringBuilder();
        Consumer<String> consumer = s -> sb.append(s);
        try {
            iterator.forEachRemaining(consumer);
            if (sb.toString().equals("abc")) {
                System.out.println("PASS: forEachRemaining processed all elements correctly");
            } else {
                System.out.println("FAIL: Unexpected result: " + sb.toString());
            }
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception: " + e);
        }
    }
}
