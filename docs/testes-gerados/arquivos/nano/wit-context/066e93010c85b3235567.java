/* @test    @summary Test for TreeMap.tailMap method to observe behavior and exception handling    @run main TreeMapWitupTest */

import java.util.TreeMap;
import java.util.SortedMap;

public class TreeMapWitupTest {

    public static void main(String[] args) {
        testTailMapThrowsInternalError();
    }

    private static void testTailMapThrowsInternalError() {
        TreeMap<Integer, String> map = new TreeMap<>();
        // Adiciona elementos para evitar comportamento de mapa vazio
        map.put(1, "one");
        map.put(2, "two");
        try {
            SortedMap<Integer, String> result = map.tailMap(1);
            // Como o método lança InternalError, não esperamos um resultado válido
            // Se chegar aqui, o método não lançou exceção como esperado
            System.err.println("FAIL: tailMap() não lançou InternalError como esperado.");
        } catch (InternalError e) {
            // Esperado: o método lança InternalError
            System.out.println("PASS: tailMap() lançou InternalError como esperado.");
        } catch (Throwable t) {
            System.err.println("FAIL: tailMap() lançou uma exceção inesperada: " + t);
        }
    }
}
