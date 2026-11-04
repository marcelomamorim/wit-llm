/* @test    @summary Test for TreeMap.tailMap method handling of out-of-range fromKey causing IllegalArgumentException    @run main TreeMapWitupTest */

import java.util.TreeMap;
import java.util.NavigableMap;

public class TreeMapWitupTest {

    public static void main(String[] args) {
        testTailMapOutOfRange();
    }

    public static void testTailMapOutOfRange() {
        TreeMap<Integer, String> map = new TreeMap<>();
        map.put(10, "Ten");
        map.put(20, "Twenty");
        map.put(30, "Thirty");

        // Caso onde fromKey está fora do intervalo válido, esperando IllegalArgumentException
        try {
            map.tailMap(5, true);
            // Se não lançar exceção, o teste falha
            throw new AssertionError("Expected IllegalArgumentException for fromKey out of range");
        } catch (IllegalArgumentException e) {
            // Exceção esperada, teste passa
        }

        // Caso onde fromKey é maior que o maior elemento, deve retornar mapa vazio
        NavigableMap<Integer, String> subMap = map.tailMap(40, true);
        if (!subMap.isEmpty()) {
            throw new AssertionError("Expected empty map for fromKey beyond max key");
        }

        // Caso onde fromKey é igual a um elemento existente, deve retornar submap a partir dele
        NavigableMap<Integer, String> subMap2 = map.tailMap(20, true);
        if (subMap2.size() != 2 || !subMap2.firstKey().equals(20)) {
            throw new AssertionError("Unexpected submap result for fromKey=20");
        }
    }
}
