/*
 * @test
 * @summary Test for sun.security.x509.CertificateSerialNumber.delete method.
 * @run main CertificateSerialNumberWitupTest_P856bbdea08de
 */

import java.io.IOException;
import java.io.ByteArrayInputStream;
import sun.security.x509.CertificateSerialNumber;

public class CertificateSerialNumberWitupTest_P856bbdea08de {

    public static void main(String[] args) throws Exception {
        testDeleteWithNumber();
        testDeleteWithOtherName();
    }

    private static void testDeleteWithNumber() throws Exception {
        // Cria um objeto com um serial válido
        CertificateSerialNumber csn = new CertificateSerialNumber(12345);
        // Invoca delete com o nome "number" (deve definir serial como null)
        csn.delete("number");
        // Verifica se serial é null após a deleção
        assert csn.toString().equals("") : "Expected empty string after delete with 'number'";
    }

    private static void testDeleteWithOtherName() throws Exception {
        // Cria um objeto com um serial válido
        CertificateSerialNumber csn = new CertificateSerialNumber(67890);
        boolean exceptionThrown = false;
        try {
            // Invoca delete com nome diferente de "number" (deve lançar IOException)
            csn.delete("foo");
        } catch (IOException e) {
            exceptionThrown = true;
            // Verifica mensagem de exceção (opcional)
            assert e.getMessage().contains("Attribute name not recognized") : "Unexpected exception message";
        }
        assert exceptionThrown : "Expected IOException to be thrown for unrecognized attribute name";
    }
}
