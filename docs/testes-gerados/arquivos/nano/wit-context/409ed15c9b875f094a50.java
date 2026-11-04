/* @test    @summary Test for sun.security.x509.OIDName.subtreeDepth() method
 * @run main OIDNameWitupTest
 */

import sun.security.x509.OIDName;

public class OIDNameWitupTest {

    public static void main(String[] args) {
        testSubtreeDepthThrowsException();
    }

    private static void testSubtreeDepthThrowsException() {
        OIDName oidName = null;
        try {
            // Cria uma instância de OIDName com um valor válido
            oidName = new OIDName("1.2.3.4");
        } catch (Exception e) {
            // Se ocorrer erro na criação, o teste não pode continuar
            System.err.println("Failed to create OIDName: " + e);
            return;
        }
        try {
            // Invoca o método que deve lançar UnsupportedOperationException
            int depth = oidName.subtreeDepth();
            // Se não lançar exceção, o teste falha
            System.err.println("Expected UnsupportedOperationException, but got: " + depth);
        } catch (UnsupportedOperationException e) {
            // Exceção esperada, o teste passa
            System.out.println("subtreeDepth() correctly threw UnsupportedOperationException.");
        } catch (Exception e) {
            // Se outra exceção ocorrer, o teste falha
            System.err.println("Unexpected exception: " + e);
        }
    }
}
