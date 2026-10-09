/*
 * @test
 * @summary Test for ConcurrentHashMap.forEach with null BiConsumer
 * @run main ConcurrentHashMapWitupTest_Pf8e8fd8b1377
 */

import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;

public class ConcurrentHashMapWitupTest_Pf8e8fd8b1377 {

    public static void main(String[] args) {
        testForEachWithNullAction();
    }

    public static void testForEachWithNullAction() {
        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();
        map.put("a", 1);
        map.put("b", 2);
        boolean exceptionThrown = false;
        try {
            map.forEach(null);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected NullPointerException when action is null");
        }
    }
}
