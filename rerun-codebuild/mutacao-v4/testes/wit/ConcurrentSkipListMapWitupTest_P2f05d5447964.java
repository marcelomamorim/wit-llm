/* @test
   @summary Testa tailMap com entrada nula para verificar NullPointerException.
   @run main ConcurrentSkipListMapWitupTest_P2f05d5447964
 */
import java.util.concurrent.ConcurrentSkipListMap;
public class ConcurrentSkipListMapWitupTest_P2f05d5447964 {
    public static void main(String[] args) {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        try {
            map.tailMap(null);
            throw new AssertionError("Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            // expected
        }
    }
}
