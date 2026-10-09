/* @test
   @summary Testa o método remove com chave nula, esperando NullPointerException.
   @run main ConcurrentSkipListMapWitupTest_Pe01bba25fd34
 */
import java.util.concurrent.ConcurrentSkipListMap;
public class ConcurrentSkipListMapWitupTest_Pe01bba25fd34 {
    public static void main(String[] args) {
        ConcurrentSkipListMap<Object, Object> map = new ConcurrentSkipListMap<>();
        Object key = null;
        Object value = new Object();
        try {
            map.remove(key, value);
            throw new AssertionError("Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            // expected
        }
    }
}
