/* @test    @summary Test get method of CRLDistributionPointsExtension for valid and invalid attribute names    @run main CRLDistributionPointsExtensionWitupTest */

import sun.security.x509.CRLDistributionPointsExtension;
import java.io.IOException;
import java.util.List;

public class CRLDistributionPointsExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        testGetWithValidName();
        testGetWithInvalidName();
    }

    private static void testGetWithValidName() throws IOException {
        // Create a dummy list for testing
        List<?> dummyList = java.util.Collections.emptyList();
        // Instantiate CRLDistributionPointsExtension with dummy data
        CRLDistributionPointsExtension ext = new CRLDistributionPointsExtension(
            true, dummyList
        );
        // Call get with valid name
        List<?> result = ext.get("x509.info.extensions.CRLDistributionPoints");
        // Verify that the result is the same as the dummy list
        if (result != dummyList) {
            throw new AssertionError("Expected list not returned for valid name");
        }
        // Also test case-insensitivity
        List<?> resultCaseInsensitive = ext.get("X509.INFO.EXTENSIONS.CRLDistributionPoints");
        if (resultCaseInsensitive != dummyList) {
            throw new AssertionError("Case-insensitive get failed");
        }
    }

    private static void testGetWithInvalidName() {
        // Create a dummy list for testing
        List<?> dummyList = java.util.Collections.emptyList();
        // Instantiate CRLDistributionPointsExtension with dummy data
        CRLDistributionPointsExtension ext = new CRLDistributionPointsExtension(
            false, dummyList
        );
        try {
            ext.get("InvalidName");
            throw new AssertionError("Expected IOException for invalid attribute name");
        } catch (IOException e) {
            String expectedMessagePart = "Attribute name [";
            if (!e.getMessage().contains(expectedMessagePart)) {
                throw new AssertionError("Unexpected exception message: " + e.getMessage());
            }
        }
    }
}
