/* @test    @summary Test for CRLReasonCodeExtension.get(String) method to verify behavior with valid and invalid input.    @run main CRLReasonCodeExtensionWitupTest_P05f0783c450c */

import sun.security.x509.CRLReasonCodeExtension;
import java.io.IOException;

public class CRLReasonCodeExtensionWitupTest_P05f0783c450c {

    public static void main(String[] args) throws Exception {
        testGetWithValidReason();
        testGetWithInvalidName();
    }

    public static void testGetWithValidReason() throws Exception {
        CRLReasonCodeExtension ext = new CRLReasonCodeExtension(1); // reason code 1
        Integer result = ext.get("reason");
        if (result == null) {
            throw new AssertionError("Expected non-null result for valid reason");
        }
        if (result.intValue() != 1) {
            throw new AssertionError("Expected reason code 1, got " + result);
        }
    }

    public static void testGetWithInvalidName() {
        CRLReasonCodeExtension ext;
        try {
            ext = new CRLReasonCodeExtension(1); // reason code 1
        } catch (IOException e) {
            throw new RuntimeException("Failed to create extension instance", e);
        }
        boolean thrown = false;
        try {
            ext.get("invalidName");
        } catch (IOException e) {
            thrown = true;
            if (!e.getMessage().equals("Name not supported by CRLReasonCodeExtension")) {
                throw new AssertionError("Unexpected exception message: " + e.getMessage());
            }
        }
        if (!thrown) {
            throw new AssertionError("Expected IOException for invalid name");
        }
    }
}
