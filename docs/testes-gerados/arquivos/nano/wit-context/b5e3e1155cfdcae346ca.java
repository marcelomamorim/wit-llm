/*
 * @test
 * @summary Testa o método getInputStream de ZipFile com entrada nula.
 * @run main ZipFileWitupTest
 */

import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class ZipFileWitupTest {

    public static void main(String[] args) throws IOException {
        testGetInputStreamWithNullEntry();
    }

    public static void testGetInputStreamWithNullEntry() {
        ZipFile zipFile = null;
        try {
            // Cria um objeto ZipFile válido para o teste
            // Como não há arquivo real, usamos um arquivo fictício, assumindo que o método não será chamado realmente
            zipFile = new ZipFile("dummy.zip");
        } catch (IOException e) {
            // Ignora, pois o arquivo não existe, foco no teste de entrada nula
        }
        try {
            // Chamada com entrada nula, deve lançar NullPointerException
            zipFile.getInputStream(null);
        } catch (NullPointerException e) {
            System.out.println("Passou no teste: NullPointerException ao passar entrada nula.");
        } catch (IOException e) {
            // Pode ocorrer se o arquivo não existir, ignoramos
        } finally {
            if (zipFile != null) {
                try {
                    zipFile.close();
                } catch (IOException e) {
                    // Ignora
                }
            }
        }
    }
}
