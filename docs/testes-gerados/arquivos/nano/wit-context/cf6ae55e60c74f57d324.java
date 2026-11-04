/*
 * @test
 * @summary Testa o construtor de JarFile com arquivo nulo.
 * @run main JarFileWitupTest
 */

import java.io.File;
import java.io.IOException;
import java.util.jar.JarFile;

public class JarFileWitupTest {

    public static void main(String[] args) {
        testNullFileConstructor();
    }

    public static void testNullFileConstructor() {
        try {
            new JarFile((File) null);
            System.err.println("Falha: Nenhuma exceção lançada ao passar null.");
        } catch (NullPointerException e) {
            System.out.println("Sucesso: NullPointerException lançada como esperado.");
        } catch (Exception e) {
            System.err.println("Falha: Exceção inesperada: " + e);
        }
    }
}
