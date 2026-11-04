/* @test    @summary Test delete method of CertificateX509Key class    @run main CertificateX509KeyWitupTest */

import java.io.IOException;
import java.security.PublicKey;
import sun.security.x509.CertificateX509Key;

public class CertificateX509KeyWitupTest {

    public static void main(String[] args) throws Exception {
        testDeleteWithValidKey();
        testDeleteWithInvalidName();
    }

    private static void testDeleteWithValidKey() throws Exception {
        PublicKey mockKey = new PublicKey() {
            @Override
            public String getAlgorithm() { return "RSA"; }
            @Override
            public String getFormat() { return "X.509"; }
            @Override
            public byte[] getEncoded() { return new byte[]{1, 2, 3}; }
        };
        CertificateX509Key certKey = new CertificateX509Key(mockKey);
        certKey.delete("value");
        String result = certKey.toString();
        if (!result.contains("")) {
            throw new AssertionError("Expected empty string after delete, got: " + result);
        }
        System.out.println("testDeleteWithValidKey passed");
    }

    private static void testDeleteWithInvalidName() {
        PublicKey mockKey = new PublicKey() {
            @Override
            public String getAlgorithm() { return "RSA"; }
            @Override
            public String getFormat() { return "X.509"; }
            @Override
            public byte[] getEncoded() { return new byte[]{1, 2, 3}; }
        };
        CertificateX509Key certKey = new CertificateX509Key(mockKey);
        try {
            certKey.delete("invalidName");
            throw new AssertionError("Expected IOException was not thrown");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.contains("Attribute name not recognized")) {
                throw new AssertionError("Unexpected exception message: " + msg);
            }
        }
        System.out.println("testDeleteWithInvalidName passed");
    }
}
