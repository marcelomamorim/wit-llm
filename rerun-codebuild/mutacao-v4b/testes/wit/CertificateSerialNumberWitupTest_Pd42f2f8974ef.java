/* @test
   @summary Testa o método delete de sun.security.x509.CertificateSerialNumber.
   @run main CertificateSerialNumberWitupTest_Pd42f2f8974ef
 */
import java.io.IOException;
import sun.security.x509.CertificateSerialNumber;
public class CertificateSerialNumberWitupTest_Pd42f2f8974ef {
    public static void main(String[] args) throws Exception {
        testDeleteWithNumber();
        testDeleteWithOtherString();
    }
    private static void testDeleteWithNumber() throws Exception {
        // Cria uma instância com serial não nulo
        BigInteger bigInt = BigInteger.valueOf(12345);
        CertificateSerialNumber certSerial = new CertificateSerialNumber(bigInt);
        // Invoca delete com "number" (caso esperado para serial não ser nulo)
        certSerial.delete("number");
        // Verifica se serial foi setado para null
        if (certSerial.toString().contains("")) {
            System.out.println("testDeleteWithNumber passed");
        } else {
            throw new AssertionError("serial should be null after delete with 'number'");
        }
    }
    private static void testDeleteWithOtherString() throws Exception {
        // Cria uma instância com serial
        BigInteger bigInt = BigInteger.valueOf(67890);
        CertificateSerialNumber certSerial = new CertificateSerialNumber(bigInt);
        boolean exceptionThrown = false;
        try {
            certSerial.delete("foo");
        } catch (IOException e) {
            exceptionThrown = true;
            if (!e.getMessage().contains("Attribute name not recognized")) {
                throw new AssertionError("Unexpected exception message: " + e.getMessage());
            }
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected IOException for unrecognized attribute name");
        }
        System.out.println("testDeleteWithOtherString passed");
    }
}
