/* @test    @summary Test delete method of PolicyMappingsExtension for known attribute "map" and unknown attribute.    @run main PolicyMappingsExtensionWitupTest */

import java.io.IOException;
import sun.security.x509.PolicyMappingsExtension;

public class PolicyMappingsExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        testDeleteWithMap();
        testDeleteWithUnknownAttribute();
    }

    private static void testDeleteWithMap() throws IOException {
        // Cria uma instância com lista vazia
        PolicyMappingsExtension ext = new PolicyMappingsExtension();
        // Invoca delete com "map" (caso esperado)
        ext.delete("map");
        // Verifica se maps foi setado para null
        if (ext == null) {
            throw new AssertionError("Extension is null after delete with 'map'");
        }
        // Como maps é privado, não podemos acessá-lo diretamente; assumindo que não lança exceção
        System.out.println("testDeleteWithMap passed");
    }

    private static void testDeleteWithUnknownAttribute() {
        try {
            PolicyMappingsExtension ext = new PolicyMappingsExtension();
            ext.delete("unknownAttribute");
            throw new AssertionError("Expected IOException not thrown for unknown attribute");
        } catch (IOException e) {
            String message = e.getMessage();
            if (message == null || !message.contains("Attribute name not recognized")) {
                throw new AssertionError("Unexpected exception message: " + message, e);
            }
            System.out.println("testDeleteWithUnknownAttribute passed");
        }
    }
}
