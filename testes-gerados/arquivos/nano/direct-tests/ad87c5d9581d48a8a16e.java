/* @test    @summary Test delete method of ExtendedKeyUsageExtension for recognized attribute name    @run main ExtendedKeyUsageExtensionWitupTest */

import sun.security.x509.ExtendedKeyUsageExtension;
import java.io.IOException;

public class ExtendedKeyUsageExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        testDeleteWithUsages();
        testDeleteWithInvalidName();
    }

    static void testDeleteWithUsages() throws IOException {
        ExtendedKeyUsageExtension ext = new ExtendedKeyUsageExtension();
        // Initialize with non-null keyUsages to test deletion
        ext.keyUsages = new java.util.Vector<>();
        ext.keyUsages.add(new sun.security.util.ObjectIdentifier("1.3.6.1.5.5.7.3.1"));
        ext.delete("usages");
        assert ext.keyUsages == null : "Expected keyUsages to be null after delete";
    }

    static void testDeleteWithInvalidName() {
        ExtendedKeyUsageExtension ext = new ExtendedKeyUsageExtension();
        boolean exceptionThrown = false;
        try {
            ext.delete("invalidName");
        } catch (IOException e) {
            exceptionThrown = true;
            assert e.getMessage().contains("not recognized") : "Unexpected exception message";
        }
        assert exceptionThrown : "Expected IOException to be thrown for invalid attribute name";
    }
}
