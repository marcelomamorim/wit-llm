/* @test
   @summary Tests the set method of CertificateX509Key.
   @run main CertificateX509KeyWitupTest_Pc8979220676f
 */
import java.io.IOException;
import java.security.PublicKey;
import sun.security.x509.CertificateX509Key;

public class CertificateX509KeyWitupTest_Pc8979220676f {
    public static void main(String[] args) throws Exception {
        testSetWithValidKey();
        testSetWithInvalidName();
    }

    private static void testSetWithValidKey() throws Exception {
        PublicKey mockKey = new PublicKey() {
            @Override
            public String getAlgorithm() { return "RSA"; }
            @Override
            public String getFormat() { return "X.509"; }
            @Override
            public byte[] getEncoded() { return new byte[] {1, 2, 3}; }
        };
        CertificateX509Key certKey = new CertificateX509Key(mockKey);
        certKey.set("value", mockKey);
        if (certKey.toString() == null || certKey.toString().isEmpty()) {
            throw new AssertionError("toString() should not be empty");
        }
    }

    private static void testSetWithInvalidName() {
        PublicKey mockKey = new PublicKey() {
            @Override
            public String getAlgorithm() { return "RSA"; }
            @Override
            public String getFormat() { return "X.509"; }
            @Override
            public byte[] getEncoded() { return new byte[] {1, 2, 3}; }
        };
        CertificateX509Key certKey = new CertificateX509Key(mockKey);
        boolean exceptionThrown = false;
        try {
            certKey.set("invalidName", mockKey);
        } catch (IOException e) {
            exceptionThrown = true;
            if (!e.getMessage().contains("Attribute name not recognized")) {
                throw new AssertionError("Unexpected exception message: " + e.getMessage());
            }
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected IOException was not thrown");
        }
    }
}
