/*
 * @test
 * @summary Teste do método lastKey() de ConcurrentSkipListMap
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.concurrent.ConcurrentSkipListMap;
import java.util.NoSuchElementException;

public class ConcurrentSkipListMapWitupTest {

    public static void main(String[] args) {
        testLastKeyDescending();
        testLastKeyNonDescending();
        testLastKeyEmptyMap();
    }

    static void testLastKeyDescending() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        // Configurar mapa para isDescending = true
        // Como o método lastKey() depende do estado interno, simular esse estado
        // Para fins de teste, usar uma subclasse que força isDescending = true
        class DescendingMap extends ConcurrentSkipListMap<Integer, String> {
            @Override
            public boolean isDescending() { return true; }
        }
        DescendingMap descMap = new DescendingMap();
        descMap.put(1, "a");
        descMap.put(2, "b");
        // Como lastKey() depende do método isDescending, testar o comportamento esperado
        try {
            Integer result = descMap.lastKey();
            assert result.equals(descMap.lowestKey()) : "Esperado lowestKey() quando isDescending=true";
        } catch (Exception e) {
            throw new AssertionError("Falha em testLastKeyDescending", e);
        }
    }

    static void testLastKeyNonDescending() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(1, "a");
        map.put(3, "c");
        map.put(2, "b");
        try {
            Integer result = map.lastKey();
            assert result.equals(map.highestKey()) : "lastKey() deve retornar highestKey() quando não é descendente";
        } catch (Exception e) {
            throw new AssertionError("Falha em testLastKeyNonDescending", e);
        }
    }

    static void testLastKeyEmptyMap() {
        ConcurrentSkipListMap<Integer, String> emptyMap = new ConcurrentSkipListMap<>();
        // Como o método lastKey() pode lançar NoSuchElementException se vazio
        try {
            emptyMap.lastKey();
            throw new AssertionError("Esperado NoSuchElementException ao chamar lastKey() em mapa vazio");
        } catch (NoSuchElementException e) {
            // esperado
        } catch (Exception e) {
            throw new AssertionError("Exceção inesperada em testLastKeyEmptyMap", e);
        }
    }
}
