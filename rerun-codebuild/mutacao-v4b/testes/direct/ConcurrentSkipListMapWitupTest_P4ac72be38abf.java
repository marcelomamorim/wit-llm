/* @test
   @summary Verifies the behavior of containsValue method in ConcurrentSkipListMap.
   @run main ConcurrentSkipListMapWitupTest_P4ac72be38abf
 */
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.Objects;

public class ConcurrentSkipListMapWitupTest_P4ac72be38abf {
    public static void main(String[] args) {
        testContainsValueWithExistingValue();
        testContainsValueWithNonExistingValue();
        testContainsValueWithNullThrows();
    }

    static void testContainsValueWithExistingValue() {
        ConcurrentSkipListMap<String, Integer> map = new ConcurrentSkipListMap<>();
        map.put("a", 1);
        map.put("b", 2);
        boolean result = map.containsValue(1);
        if (!result) {
            throw new AssertionError("Expected true for existing value 1");
        }
    }

    static void testContainsValueWithNonExistingValue() {
        ConcurrentSkipListMap<String, Integer> map = new ConcurrentSkipListMap<>();
        map.put("a", 1);
        map.put("b", 2);
        boolean result = map.containsValue(3);
        if (result) {
            throw new AssertionError("Expected false for non-existing value 3");
        }
    }

    static void testContainsValueWithNullThrows() {
        ConcurrentSkipListMap<String, Integer> map = new ConcurrentSkipListMap<>();
        map.put("a", 1);
        boolean threw = false;
        try {
            map.containsValue(null);
        } catch (NullPointerException e) {
            threw = true;
        }
        if (!threw) {
            throw new AssertionError("Expected NullPointerException for null argument");
        }
    }
}
