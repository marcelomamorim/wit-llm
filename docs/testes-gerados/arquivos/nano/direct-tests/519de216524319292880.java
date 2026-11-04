/*
 * @test
 * @summary Test tryAdvance method of ConcurrentHashMap for correct behavior
 * @run main ConcurrentHashMapWitupTest
 */

import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

public class ConcurrentHashMapWitupTest {

    public static void main(String[] args) {
        testTryAdvanceWithEmptyMap();
        testTryAdvanceWithSingleElement();
        testTryAdvanceMultipleCalls();
    }

    static void testTryAdvanceWithEmptyMap() {
        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();
        boolean result = map.tryAdvance(key -> {
            throw new AssertionError("Should not be called on empty map");
        });
        assert !result : "tryAdvance should return false on empty map";
    }

    static void testTryAdvanceWithSingleElement() {
        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();
        map.put("a", 1);
        final boolean[] called = {false};
        boolean result = map.tryAdvance(key -> {
            called[0] = true;
            assert "a".equals(key) : "Key should be 'a'";
        });
        assert result : "tryAdvance should return true when element exists";
        assert called[0] : "Consumer should be called";
    }

    static void testTryAdvanceMultipleCalls() {
        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();
        map.put("a", 1);
        map.put("b", 2);
        final StringBuilder sb = new StringBuilder();
        boolean firstCall = map.tryAdvance(key -> {
            sb.append(key);
        });
        assert firstCall : "First tryAdvance should return true";
        assert sb.toString().contains("a") || sb.toString().contains("b") : "Consumer should be called with a key";
        sb.setLength(0);
        boolean secondCall = map.tryAdvance(key -> {
            sb.append(key);
        });
        assert secondCall : "Second tryAdvance should return true";
        assert sb.toString().contains("a") || sb.toString().contains("b") : "Consumer should be called with a key";
        boolean thirdCall = map.tryAdvance(key -> {
            throw new AssertionError("No more elements expected")
        });
        assert !thirdCall : "No more elements, should return false";
    }
}
