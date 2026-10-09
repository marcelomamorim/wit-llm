/* @test
   @summary Verifica comportamento do método HashMap.forEach com ação nula.
   @run main HashMapWitupTest_P7d3f6a672d75
 */
import java.util.HashMap;
import java.util.function.BiConsumer;
public class HashMapWitupTest_P7d3f6a672d75 {
    public static void main(String[] args) {
        HashMap<String, Integer> map = new HashMap<>();
        try {
            map.forEach(null);
            throw new AssertionError("Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            // expected
        }
    }
}
