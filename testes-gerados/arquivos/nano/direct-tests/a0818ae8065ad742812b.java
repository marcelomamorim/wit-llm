/* @test    @summary Test get method of PrivateKeyUsageExtension for known attribute names    @run main PrivateKeyUsageExtensionWitupTest */

import java.security.cert.CertificateException;
import java.util.Date;
import sun.security.x509.PrivateKeyUsageExtension;

public class PrivateKeyUsageExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        testGetMethod("not_before");
        testGetMethod("not_after");
        testGetMethod("unknown_attribute");
    }

    private static void testGetMethod(String attributeName) throws Exception {
        Date now = new Date();
        PrivateKeyUsageExtension extension = new PrivateKeyUsageExtension(now, new Date(now.getTime() + 100000));
        try {
            Date result = extension.get(attributeName);
            if (attributeName.equalsIgnoreCase("not_before")) {
                assert result.equals(new Date(now.getTime())) : "Expected not_before date";
            } else if (attributeName.equalsIgnoreCase("not_after")) {
                assert result.equals(new Date(now.getTime() + 100000)) : "Expected not_after date";
            } else {
                // For unknown attribute, expect CertificateException
                assert false : "Expected CertificateException for attribute: " + attributeName;
            }
        } catch (CertificateException e) {
            if (attributeName.equalsIgnoreCase("unknown_attribute")) {
                // Expected exception
                assert e.getMessage().contains("Attribute name not recognized") : "Unexpected exception message";
            } else {
                throw e;
            }
        }
    }
}
