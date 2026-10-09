/* @test
   @summary Verifies the replaceAll method behavior of ConcurrentSkipListMap.
   @run main ConcurrentSkipListMapWitupTest_Pa8e49090aef6
 */
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.function.BiFunction;

public class ConcurrentSkipListMapWitupTest_Pa8e49090aef6 {
    public static void main(String[] args) {
        testReplaceAllWithValidFunction();
        testReplaceAllWithNullFunction();
        testReplaceAllWithFunctionReturningNull();
    }

    private static void testReplaceAllWithValidFunction() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(1, "one");
        map.put(2, "two");
        map.put(3, "three");
        BiFunction<Integer, String, String> func = (k, v) -> v.toUpperCase();
        map.replaceAll(func);
        assert "ONE".equals(map.get(1)) : "Expected 'ONE' for key 1";
        assert "TWO".equals(map.get(2)) : "Expected 'TWO' for key 2";
        assert "THREE".equals(map.get(3)) : "Expected 'THREE' for key 3";
    }

    private static void testReplaceAllWithNullFunction() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(1, "one");
        boolean exceptionThrown = false;
        try {
            map.replaceAll(null);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        assert exceptionThrown : "Expected NullPointerException when function is null";
    }

    private static void testReplaceAllWithFunctionReturningNull() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(1, "one");
        BiFunction<Integer, String, String> func = (k, v) -> null;
        boolean exceptionThrown = false;
        try {
            map.replaceAll(func);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        assert exceptionThrown : "Expected NullPointerException when function returns null";
        assert "one".equals(map.get(1)) : "Value for key 1 should remain unchanged";
    }
}
