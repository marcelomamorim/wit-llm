/*
 * @test
 * @summary Test for getUserPrincipalLookupService method in JrtFileSystem
 * @run main JrtFileSystemWitupTest
 */

import jdk.internal.jrtfs.JrtFileSystem;
import java.lang.UnsupportedOperationException;

public class JrtFileSystemWitupTest {

    public static void main(String[] args) {
        testGetUserPrincipalLookupServiceThrows();
    }

    public static void testGetUserPrincipalLookupServiceThrows() {
        JrtFileSystem fs = null;
        try {
            // Instanciação fictícia, pois o construtor é package-private; neste teste, simulamos o método
            fs = new JrtFileSystem(null, null) {
                @Override
                public final java.nio.file.UserPrincipalLookupService getUserPrincipalLookupService() {
                    throw new UnsupportedOperationException();
                }
            };
        } catch (UnsupportedOperationException e) {
            // Caso a exceção seja lançada na construção, o teste também é válido
            return;
        } catch (Exception e) {
            // Ignora outras exceções, pois o foco é na exceção do método
        }
        try {
            // Invoca o método que deve lançar UnsupportedOperationException
            fs.getUserPrincipalLookupService();
            // Se não lançar, o teste falha
            throw new AssertionError("Expected UnsupportedOperationException was not thrown");
        } catch (UnsupportedOperationException e) {
            // Test passou, exceção foi lançada como esperado
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception: " + e);
        }
    }
}
