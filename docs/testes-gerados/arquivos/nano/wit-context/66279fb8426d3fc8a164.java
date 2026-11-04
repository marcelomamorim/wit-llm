/* @test    @summary Test for sun.util.PreHashedMap.remove() method to verify UnsupportedOperationException is thrown.    @run main PreHashedMapWitupTest */

import sun.util.PreHashedMap;

public class PreHashedMapWitupTest {

    public static void main(String[] args) {
        testRemoveThrowsException();
    }

    public static void testRemoveThrowsException() {
        // Instancia de PreHashedMap com valores fictícios, pois o construtor é protegido
        // e não há método público de fábrica, então criamos uma subclasse anônima para testar.
        PreHashedMap<String> map = new PreHashedMap<String>(1, 0, 0, 0) {
            @Override
            protected void init(Object[] ht) {
                // não necessário para este teste
            }
        };
        try {
            map.remove();
            System.out.println("FAIL: Expected UnsupportedOperationException was not thrown.");
        } catch (UnsupportedOperationException e) {
            System.out.println("PASS: UnsupportedOperationException was correctly thrown.");
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception type: " + e);
        }
    }
}
