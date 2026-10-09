/* @test
   @summary Testa o método replaceAll de HashMap para comportamento padrão com função nula.
   @run main HashMapWitupTest_Pb3aaadd48a71
 */
import java.util.HashMap;
import java.util.function.BiFunction;
public class HashMapWitupTest_Pb3aaadd48a71 {
    public static void main(String[] args) {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("a", 1);
        map.put("b", 2);
        try {
            map.replaceAll(null);
            throw new AssertionError("Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            // expected
        }
        // Testa comportamento normal com função válida
        BiFunction<String, Integer, Integer> func = (k, v) -> v + 10;
        map.replaceAll(func);
        if (map.get("a") != 11 || map.get("b") != 12) {
            throw new AssertionError("replaceAll não aplicou a função corretamente");
        }
    }
}
