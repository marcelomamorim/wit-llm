/*
 * @test
 * @summary Test for HashMap's forEachRemaining method, focusing on NullPointerException when action is null.
 * @run main HashMapWitupTest
 */

import java.util.HashMap;
import java.util.function.Consumer;

public class HashMapWitupTest {

    public static void main(String[] args) {
        testForEachRemainingWithNullAction();
        testForEachRemainingWithValidAction();
    }

    private static void testForEachRemainingWithNullAction() {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("a", 1);
        map.put("b", 2);
        HashMap.KeySpliterator<String, Integer> spliterator = map.keySet().spliterator();
        try {
            spliterator.forEachRemaining(null);
            System.out.println("FAIL: Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown as expected");
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception: " + e);
        }
    }

    private static void testForEachRemainingWithValidAction() {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("a", 1);
        map.put("b", 2);
        HashMap.KeySpliterator<String, Integer> spliterator = map.keySet().spliterator();
        StringBuilder sb = new StringBuilder();
        Consumer<String> consumer = key -> sb.append(key);
        try {
            spliterator.forEachRemaining(consumer);
            String result = sb.toString();
            if (result.contains("a") && result.contains("b")) {
                System.out.println("PASS: forEachRemaining executed successfully with consumer");
            } else {
                System.out.println("FAIL: forEachRemaining did not process all keys: " + result);
            }
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception during forEachRemaining with consumer: " + e);
        }
    }
}
