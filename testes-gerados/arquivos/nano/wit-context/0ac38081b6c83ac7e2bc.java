/*
 * @test
 * @summary Test for ConcurrentHashMap.forEachRemaining with null action
 * @run main ConcurrentHashMapWitupTest
 */

import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

public class ConcurrentHashMapWitupTest {

    public static void main(String[] args) {
        testForEachRemainingNullAction();
    }

    public static void testForEachRemainingNullAction() {
        ConcurrentHashMap<Integer, String> map = new ConcurrentHashMap<>();
        map.put(1, "one");
        map.put(2, "two");
        map.put(3, "three");
        try {
            map.forEachRemaining(null);
            // Se não lançar exceção, o teste falha
            throw new AssertionError("Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            // Esperado
        }
    }
}
