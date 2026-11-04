/*
 * @test
 * @summary Testa o método containsKey para verificar comportamento com entradas nulas e válidas
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapWitupTest {

    public static void main(String[] args) {
        testContainsKeyWithNull();
        testContainsKeyWithValidKey();
    }

    static void testContainsKeyWithNull() {
        ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap<>();
        boolean exceptionThrown = false;
        try {
            map.containsKey(null);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected NullPointerException when calling containsKey with null");
        }
    }

    static void testContainsKeyWithValidKey() {
        ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap<>();
        String key = "testKey";
        String value = "testValue";
        map.put(key, value);
        boolean result = map.containsKey(key);
        if (!result) {
            throw new AssertionError("Map should contain the key after put");
        }
    }
}
