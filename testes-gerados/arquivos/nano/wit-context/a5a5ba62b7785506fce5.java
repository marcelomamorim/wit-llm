/* @test    @summary Test for sun.security.x509.CertificateVersion.delete(String) method    @run main CertificateVersionWitupTest */

import sun.security.x509.CertificateVersion;
import java.io.IOException;

public class CertificateVersionWitupTest {

    public static void main(String[] args) throws Exception {
        testDeleteWithValidName();
        testDeleteWithInvalidName();
    }

    private static void testDeleteWithValidName() throws IOException {
        CertificateVersion cv = new CertificateVersion();
        cv.delete("version");
        int version = getVersion(cv);
        if (version != 0) {
            throw new AssertionError("Expected version to be reset to 0, but was " + version);
        }
    }

    private static void testDeleteWithInvalidName() {
        CertificateVersion cv = new CertificateVersion();
        try {
            cv.delete("invalidName");
            throw new AssertionError("Expected IOException was not thrown");
        } catch (IOException e) {
            String message = e.getMessage();
            if (message == null || !message.contains("Attribute name not recognized")) {
                throw new AssertionError("Unexpected exception message: " + message, e);
            }
        }
    }

    private static int getVersion(CertificateVersion cv) throws Exception {
        java.lang.reflect.Field versionField = CertificateVersion.class.getDeclaredField("version");
        versionField.setAccessible(true);
        return versionField.getInt(cv);
    }
}
