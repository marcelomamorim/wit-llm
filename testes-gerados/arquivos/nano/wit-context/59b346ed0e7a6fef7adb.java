/*
 * @test
 * @summary Test for AbstractList's forEachRemaining method, focusing on null action handling.
 * @run main AbstractListWitupTest
 */

import java.util.AbstractList;
import java.util.function.Consumer;

public class AbstractListWitupTest {

    public static void main(String[] args) {
        testForEachRemainingWithNullAction();
        testForEachRemainingWithValidAction();
    }

    static void testForEachRemainingWithNullAction() {
        AbstractList<Integer> list = new AbstractList<Integer>() {
            @Override
            public Integer get(int index) {
                return index;
            }

            @Override
            public int size() {
                return 3;
            }
        };
        try {
            list.forEachRemaining(null);
            System.out.println("FAIL: Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown as expected");
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception: " + e);
        }
    }

    static void testForEachRemainingWithValidAction() {
        AbstractList<Integer> list = new AbstractList<Integer>() {
            @Override
            public Integer get(int index) {
                return index * 2;
            }

            @Override
            public int size() {
                return 3;
            }
        };
        StringBuilder sb = new StringBuilder();
        Consumer<Integer> consumer = (Integer x) -> sb.append(x).append(",");
        list.index = 0; // Not used in this implementation, but kept for context.
        list.forEachRemaining(consumer);
        String result = sb.toString();
        if (result.equals("0,2,4,")) {
            System.out.println("PASS: forEachRemaining processed elements correctly");
        } else {
            System.out.println("FAIL: Unexpected output: " + result);
        }
    }
}
