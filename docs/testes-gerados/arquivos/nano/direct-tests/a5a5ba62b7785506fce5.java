/* @test    @summary Test the delete method of CertificateVersion class    @run main CertificateVersionWitupTest */

import java.io.IOException;
import sun.security.x509.CertificateVersion;

public class CertificateVersionWitupTest {

    public static void main(String[] args) throws Exception {
        testDeleteVersionAttribute();
        testDeleteInvalidAttribute();
    }

    private static void testDeleteVersionAttribute() throws IOException {
        CertificateVersion certVersion = new CertificateVersion(2); // V3
        certVersion.delete("version");
        int resultVersion = getVersion(certVersion);
        if (resultVersion != 0) {
            throw new AssertionError("Expected version to be reset to V1 (0), but was: " + resultVersion);
        }
    }

    private static void testDeleteInvalidAttribute() {
        try {
            CertificateVersion certVersion = new CertificateVersion(1); // V2
            certVersion.delete("invalidAttribute");
            throw new AssertionError("Expected IOException for invalid attribute name");
        } catch (IOException e) {
            String expectedMessage = "Attribute name not recognized by CertAttrSet: CertificateVersion.";
            if (!e.getMessage().equals(expectedMessage)) {
                throw new AssertionError("Unexpected exception message: " + e.getMessage());
            }
        }
    }

    private static int getVersion(CertificateVersion certVersion) throws Exception {
        java.lang.reflect.Field versionField = CertificateVersion.class.getDeclaredField("version");
        versionField.setAccessible(true);
        return versionField.getInt(certVersion);
    }
}
