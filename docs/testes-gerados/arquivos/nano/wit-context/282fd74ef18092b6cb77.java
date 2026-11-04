/* @test    @summary Test for sun.util.PreHashedMap.setValue(V v) method.
 * @run main PreHashedMapWitupTest
 */

import sun.util.PreHashedMap;

public class PreHashedMapWitupTest {

    public static void main(String[] args) {
        testSetValueThrowsException();
    }

    public static void testSetValueThrowsException() {
        try {
            // Instancia de PreHashedMap com valores fictícios, pois o construtor é protegido
            // Como o construtor é protegido, usar uma subclasse anônima para instanciar
            PreHashedMap<String> map = new PreHashedMap<String>(1, 0, 0, 0) {
                {
                    // implementação vazia, não é necessário para o teste
                }
            };
            // Invoca o método que deve lançar UnsupportedOperationException
            map.setValue("test");
            // Se não lançar exceção, falha o teste
            System.err.println("FAIL: Expected UnsupportedOperationException was not thrown.");
        } catch (UnsupportedOperationException e) {
            // Sucesso: exceção esperada
            System.out.println("PASS: UnsupportedOperationException was thrown as expected.");
        } catch (Exception e) {
            // Se outra exceção for lançada, falha o teste
            System.err.println("FAIL: Unexpected exception: " + e);
        }
    }
}
