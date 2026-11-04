/* @test    @summary Test for SubjectAlternativeNameExtension.get(String) method to verify behavior with valid and invalid attribute names.    @run main SubjectAlternativeNameExtensionWitupTest */

import sun.security.x509.SubjectAlternativeNameExtension;
import sun.security.util.GeneralNames;
import java.io.IOException;

public class SubjectAlternativeNameExtensionWitupTest {

    public static void main(String[] args) {
        testGetWithValidName();
        testGetWithInvalidName();
    }

    private static void testGetWithValidName() {
        try {
            // Setup: create a GeneralNames object (mock or minimal instance)
            GeneralNames gn = new GeneralNames();
            // Instantiate the extension with the GeneralNames
            SubjectAlternativeNameExtension ext = new SubjectAlternativeNameExtension(gn);
            // Call get with the valid name "subject_name"
            GeneralNames result = ext.get("subject_name");
            if (result != null) {
                System.out.println("testGetWithValidName: PASSED");
            } else {
                System.out.println("testGetWithValidName: FAILED - result is null");
            }
        } catch (IOException e) {
            System.out.println("testGetWithValidName: FAILED with IOException: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("testGetWithValidName: FAILED with exception: " + e.getMessage());
        }
    }

    private static void testGetWithInvalidName() {
        try {
            // Setup: create a GeneralNames object
            GeneralNames gn = new GeneralNames();
            // Instantiate the extension
            SubjectAlternativeNameExtension ext = new SubjectAlternativeNameExtension(gn);
            // Call get with an invalid name
            ext.get("invalid_name");
            System.out.println("testGetWithInvalidName: FAILED - no exception thrown");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (msg != null && msg.contains("Attribute name not recognized")) {
                System.out.println("testGetWithInvalidName: PASSED");
            } else {
                System.out.println("testGetWithInvalidName: FAILED with unexpected IOException: " + msg);
            }
        } catch (Exception e) {
            System.out.println("testGetWithInvalidName: FAILED with exception: " + e.getMessage());
        }
    }
}
