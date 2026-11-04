/*
 * @test
 * @summary Test for getInputStream method in ModulePatcher
 * @run main ModulePatcherWitupTest
 */

import java.io.IOException;
import java.io.InputStream;
import jdk.internal.module.ModulePatcher;

public class ModulePatcherWitupTest {

    public static void main(String[] args) throws Exception {
        testGetInputStreamReturnsExpected();
    }

    private static void testGetInputStreamReturnsExpected() throws IOException {
        // Cria uma instância de ModulePatcher com um mapa vazio, pois o método não depende de estado interno
        ModulePatcher patcher = new ModulePatcher(Collections.emptyMap());
        InputStream is = patcher.getInputStream();
        if (is == null) {
            throw new AssertionError("getInputStream() retornou null");
        }
        // Opcional: verificar se o InputStream é do tipo esperado ou fechar após uso
        is.close();
    }
}
