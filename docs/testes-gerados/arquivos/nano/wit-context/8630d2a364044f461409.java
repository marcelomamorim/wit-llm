/* @test    @summary Test delete method of CertificateSerialNumber for exception path handling    @run main CertificateSerialNumberWitupTest */

import java.io.IOException;
import sun.security.x509.CertificateSerialNumber;

public class CertificateSerialNumberWitupTest {

    public static void main(String[] args) throws Exception {
        testDeleteWithRecognizedName();
        testDeleteWithUnrecognizedName();
    }

    private static void testDeleteWithRecognizedName() {
        try {
            // Cria uma instância com um serial válido
            CertificateSerialNumber csn = new CertificateSerialNumber(12345);
            // Invoca delete com o nome reconhecido "number"
            csn.delete("number");
            // Se não lançar exceção, o teste passa
            System.out.println("testDeleteWithRecognizedName: PASSED");
        } catch (IOException e) {
            System.err.println("testDeleteWithRecognizedName: FAILED - " + e);
        }
    }

    private static void testDeleteWithUnrecognizedName() {
        try {
            // Cria uma instância com um serial válido
            CertificateSerialNumber csn = new CertificateSerialNumber(12345);
            // Invoca delete com um nome não reconhecido
            csn.delete("unknownAttribute");
            System.err.println("testDeleteWithUnrecognizedName: FAILED - Expected IOException not thrown");
        } catch (IOException e) {
            // Esperado: IOException deve ser lançado
            if (e.getMessage().contains("Attribute name not recognized")) {
                System.out.println("testDeleteWithUnrecognizedName: PASSED");
            } else {
                System.err.println("testDeleteWithUnrecognizedName: FAILED - " + e);
            }
        }
    }
}
