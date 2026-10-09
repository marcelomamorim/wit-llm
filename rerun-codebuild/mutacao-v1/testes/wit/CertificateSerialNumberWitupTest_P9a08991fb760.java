/* @test    @summary Test for sun.security.x509.CertificateSerialNumber.delete(String) method    @run main CertificateSerialNumberWitupTest_P9a08991fb760 */

import java.io.IOException;
import sun.security.x509.CertificateSerialNumber;

public class CertificateSerialNumberWitupTest_P9a08991fb760 {

    public static void main(String[] args) throws Exception {
        testDeleteWithNumber();
        testDeleteWithOtherName();
    }

    private static void testDeleteWithNumber() throws IOException {
        // Cria uma instância com um BigInteger válido
        CertificateSerialNumber csn = new CertificateSerialNumber(BigInteger.valueOf(12345));
        // Chama delete com "number" (caso esperado)
        csn.delete("number");
        // Verifica se serial foi setado para null
        if (csn.toString().length() != 0) {
            throw new AssertionError("Expected serial to be null or empty after delete with 'number'");
        }
    }

    private static void testDeleteWithOtherName() {
        // Cria uma instância com um BigInteger válido
        CertificateSerialNumber csn;
        try {
            csn = new CertificateSerialNumber(BigInteger.valueOf(12345));
        } catch (Exception e) {
            throw new RuntimeException("Failed to create CertificateSerialNumber", e);
        }
        // Tenta deletar com nome diferente de "number"
        boolean exceptionThrown = false;
        try {
            csn.delete("foo");
            throw new AssertionError("Expected IOException to be thrown for unrecognized attribute name");
        } catch (IOException e) {
            // Verifica se a mensagem de exceção é a esperada
            if (!e.getMessage().contains("Attribute name not recognized")) {
                throw new AssertionError("Unexpected exception message: " + e.getMessage());
            }
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("IOException was not thrown as expected");
        }
    }
}
