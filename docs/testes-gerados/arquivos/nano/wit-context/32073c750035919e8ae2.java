/*
 * @test
 * @summary Test for Vector.forEachRemaining with null Consumer
 * @run main VectorWitupTest
 */

import java.util.Vector;
import java.util.function.Consumer;

public class VectorWitupTest {

    public static void main(String[] args) {
        testForEachRemainingWithNullConsumer();
        testForEachRemainingWithValidConsumer();
    }

    static void testForEachRemainingWithNullConsumer() {
        Vector<Integer> vector = new Vector<>();
        vector.add(1);
        vector.add(2);
        vector.add(3);
        try {
            vector.forEachRemaining(null);
            System.out.println("FAIL: Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown as expected");
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception: " + e);
        }
    }

    static void testForEachRemainingWithValidConsumer() {
        Vector<String> vector = new Vector<>();
        vector.add("a");
        vector.add("b");
        vector.add("c");
        StringBuilder sb = new StringBuilder();
        Consumer<String> consumer = s -> sb.append(s);
        vector.forEachRemaining(consumer);
        String result = sb.toString();
        if ("abc".equals(result)) {
            System.out.println("PASS: Consumer processed all elements correctly");
        } else {
            System.out.println("FAIL: Unexpected result: " + result);
        }
    }
}
