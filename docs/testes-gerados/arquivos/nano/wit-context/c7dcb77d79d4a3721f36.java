/*
 * @test
 * @summary Test for ArrayList's forEachRemaining method, focusing on NullPointerException when action is null.
 * @run main ArrayListWitupTest
 */

import java.util.ArrayList;
import java.util.function.Consumer;

public class ArrayListWitupTest {

    public static void main(String[] args) {
        testForEachRemainingWithNullAction();
        testForEachRemainingWithValidAction();
    }

    public static void testForEachRemainingWithNullAction() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        try {
            list.forEachRemaining(null);
            System.out.println("FAIL: Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown as expected");
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception: " + e);
        }
    }

    public static void testForEachRemainingWithValidAction() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        StringBuilder sb = new StringBuilder();
        Consumer<String> consumer = s -> sb.append(s);
        list.forEachRemaining(consumer);
        if (sb.toString().equals("ab")) {
            System.out.println("PASS: forEachRemaining processed elements correctly");
        } else {
            System.out.println("FAIL: Unexpected result: " + sb.toString());
        }
    }
}
