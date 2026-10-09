/* @test
   @summary Tests remove method of ConcurrentHashMap for null key.
   @run main ConcurrentHashMapWitupTest_Pb33a6c375e7b
 */
import java.util.concurrent.ConcurrentHashMap;
public class ConcurrentHashMapWitupTest_Pb33a6c375e7b {
    public static void main(String[] args) {
        ConcurrentHashMap<Object, Object> map = new ConcurrentHashMap<>();
        Object key = null;
        Object value = new Object();
        boolean exceptionThrown = false;
        try {
            map.remove(key, value);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected NullPointerException when removing with null key");
        }
    }
}
