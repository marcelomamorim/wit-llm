/* @test
   @summary Verifies the behavior of putIfAbsent method in ConcurrentSkipListMap.
   @run main ConcurrentSkipListMapWitupTest_Pcaf32b66f943
 */
import java.util.concurrent.ConcurrentSkipListMap;
public class ConcurrentSkipListMapWitupTest_Pcaf32b66f943 {
    public static void main(String[] args) {
        ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap<>();
        String key = "testKey";
        String value1 = "value1";
        String value2 = "value2";
        
        // Test putIfAbsent when key is absent
        String result1 = map.putIfAbsent(key, value1);
        if (result1 != null) {
            throw new AssertionError("Expected null when inserting new key, but got: " + result1);
        }
        if (!map.get(key).equals(value1)) {
            throw new AssertionError("Value not correctly inserted.");
        }
        
        // Test putIfAbsent when key is present
        String result2 = map.putIfAbsent(key, value2);
        if (result2 == null) {
            throw new AssertionError("Expected existing value when key is present, but got null.");
        }
        if (!result2.equals(value1)) {
            throw new AssertionError("Returned value does not match existing value.");
        }
        if (!map.get(key).equals(value1)) {
            throw new AssertionError("Value should not have been overwritten.");
        }
    }
}
