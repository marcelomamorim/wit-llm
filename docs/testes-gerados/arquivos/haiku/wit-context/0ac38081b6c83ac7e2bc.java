/*
 * @test
 * @summary Verify ConcurrentHashMap value spliterator forEachRemaining null action handling and traversal
 * @run main ConcurrentHashMapWitupTest
 */

import java.util.Spliterator;
import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapWitupTest {

    public static void main(String[] args) {
        testNullActionThrowsNPE();
        testNullActionOnEmptyMapThrowsNPE();
        testForEachRemainingVisitsAllValues();
        System.out.println("PASSED");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNullActionThrowsNPE() {
        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();
        map.put("a", 1);
        map.put("b", 2);
        Spliterator<Integer> spliterator = map.values().spliterator();
        boolean thrown = false;
        try {
            spliterator.forEachRemaining(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "forEachRemaining(null) должен lançar NullPointerException");
    }

    static void testNullActionOnEmptyMapThrowsNPE() {
        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();
        Spliterator<Integer> spliterator = map.values().spliterator();
        boolean thrown = false;
        try {
            spliterator.forEachRemaining(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "forEachRemaining(null) em mapa vazio deve lançar NullPointerException");
    }

    static void testForEachRemainingVisitsAllValues() {
        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();
        map.put("a", 1);
        map.put("b", 2);
        map.put("c", 3);
        Spliterator<Integer> spliterator = map.values().spliterator();
        final int[] sum = new int[1];
        final int[] count = new int[1];
        spliterator.forEachRemaining(v -> {
            sum[0] += v;
            count[0]++;
        });
        check(count[0] == 3, "esperado visitar 3 valores, obtido " + count[0]);
        check(sum[0] == 6, "soma esperada 6, obtida " + sum[0]);
    }
}
