/* @test    @summary Teste do método remove() de sun.util.PreHashedMap que lança UnsupportedOperationException    @run main PreHashedMapWitupTest */

import sun.util.PreHashedMap;

public class PreHashedMapWitupTest {

    public static void main(String[] args) {
        testRemoveThrowsException();
    }

    public static void testRemoveThrowsException() {
        try {
            // Cria uma instância de uma subclasse anônima de PreHashedMap, pois o construtor é protegido
            PreHashedMap<String> map = new PreHashedMap<String>(1, 0, 0, 0) {
                @Override
                protected void init(Object[] ht) {
                    // implementação vazia, pois não é usada neste teste
                }
            };
            map.remove();
            System.out.println("FAIL: Expected UnsupportedOperationException was not thrown.");
        } catch (UnsupportedOperationException e) {
            System.out.println("PASS: UnsupportedOperationException was thrown as expected.");
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception type: " + e);
        }
    }
}
