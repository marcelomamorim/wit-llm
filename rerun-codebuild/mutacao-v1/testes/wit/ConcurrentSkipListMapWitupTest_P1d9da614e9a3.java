/*
 * @test
 * @summary Test ConcurrentSkipListMap.forEach for null action
 * @run main ConcurrentSkipListMapWitupTest_P1d9da614e9a3
 */

import java.util.concurrent.ConcurrentSkipListMap;
import java.util.function.BiConsumer;

public class ConcurrentSkipListMapWitupTest_P1d9da614e9a3 {

    public static void main(String[] args) {
        testForEachNullAction();
    }

    public static void testForEachNullAction() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(1, "one");
        map.put(2, "two");
        boolean exceptionThrown = false;
        try {
            map.forEach(null);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected NullPointerException when passing null to forEach");
        }
    }
}
