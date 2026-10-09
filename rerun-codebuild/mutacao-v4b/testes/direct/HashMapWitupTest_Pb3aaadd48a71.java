/* @test
   @summary Testa o método replaceAll de HashMap para verificar sua funcionalidade básica.
   @run main HashMapWitupTest_Pb3aaadd48a71
 */
import java.util.HashMap;
import java.util.function.BiFunction;

public class HashMapWitupTest_Pb3aaadd48a71 {
    public static void main(String[] args) {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("a", 1);
        map.put("b", 2);
        map.put("c", 3);
        
        BiFunction<String, Integer, Integer> increment = (k, v) -> v + 10;
        map.replaceAll(increment);
        
        assert map.get("a") == 11 : "Expected 11, got " + map.get("a");
        assert map.get("b") == 12 : "Expected 12, got " + map.get("b");
        assert map.get("c") == 13 : "Expected 13, got " + map.get("c");
    }
}
