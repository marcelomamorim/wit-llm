/* @test
   @summary Testa o método putIfAbsent para verificar comportamento com valores nulos e chaves nulas.
   @run main ConcurrentSkipListMapWitupTest_Pcaf32b66f943
 */
import java.util.concurrent.ConcurrentSkipListMap;
public class ConcurrentSkipListMapWitupTest_Pcaf32b66f943 {
    public static void main(String[] args) {
        testPutIfAbsentNullValue();
        testPutIfAbsentNullKey();
    }
    private static void testPutIfAbsentNullValue() {
        ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap<>();
        try {
            map.putIfAbsent("key", null);
            throw new AssertionError("Expected NullPointerException for null value");
        } catch (NullPointerException expected) {
            // expected
        }
    }
    private static void testPutIfAbsentNullKey() {
        ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap<>();
        try {
            map.putIfAbsent(null, "value");
            throw new AssertionError("Expected NullPointerException for null key");
        } catch (NullPointerException expected) {
            // expected
        }
    }
}
