/*
 * @test
 * @summary Verify HashMap.forEach null action handling, empty map behavior, value iteration and concurrent modification detection
 * @run main HashMapWitupTest
 */

import java.util.ConcurrentModificationException;
import java.util.HashMap;

public class HashMapWitupTest {

    public static void main(String[] args) {
        testNullActionThrowsNPE();
        testNullActionOnEmptyMapThrowsNPE();
        testEmptyMapDoesNotInvokeAction();
        testVisitsAllValues();
        testConcurrentModificationDetected();
    }

    static void testNullActionThrowsNPE() {
        HashMap<String, Integer> m = new HashMap<>();
        m.put("a", 1);
        try {
            m.forEach(null);
        } catch (NullPointerException e) {
            return;
        }
        throw new RuntimeException("expected NullPointerException for null action");
    }

    static void testNullActionOnEmptyMapThrowsNPE() {
        HashMap<String, Integer> m = new HashMap<>();
        try {
            m.forEach(null);
        } catch (NullPointerException e) {
            return;
        }
        throw new RuntimeException("expected NullPointerException for null action on empty map");
    }

    static void testEmptyMapDoesNotInvokeAction() {
        HashMap<String, Integer> m = new HashMap<>();
        final int[] calls = {0};
        m.forEach(v -> calls[0]++);
        if (calls[0] != 0) {
            throw new RuntimeException("action invoked " + calls[0] + " times on empty map");
        }
    }

    static void testVisitsAllValues() {
        HashMap<String, Integer> m = new HashMap<>();
        m.put("a", 1);
        m.put("b", 2);
        m.put("c", 4);
        m.put(null, 8);
        final int[] sum = {0};
        final int[] count = {0};
        m.forEach(v -> {
            sum[0] += v;
            count[0]++;
        });
        if (count[0] != 4) {
            throw new RuntimeException("expected 4 visits but got " + count[0]);
        }
        if (sum[0] != 15) {
            throw new RuntimeException("expected value sum 15 but got " + sum[0]);
        }
    }

    static void testConcurrentModificationDetected() {
        HashMap<String, Integer> m = new HashMap<>();
        m.put("a", 1);
        m.put("b", 2);
        final boolean[] added = {false};
        try {
            m.forEach(v -> {
                if (!added[0]) {
                    added[0] = true;
                    m.put("extra", 0);
                }
            });
        } catch (ConcurrentModificationException e) {
            return;
        }
        throw new RuntimeException("expected ConcurrentModificationException after structural modification");
    }
}
