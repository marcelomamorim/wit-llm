/* @test    @summary Test for SubjectAlternativeNameExtension.set method handling valid and invalid inputs    @run main SubjectAlternativeNameExtensionWitupTest */

import sun.security.x509.SubjectAlternativeNameExtension;
import sun.security.util.GeneralNames;
import java.io.IOException;

public class SubjectAlternativeNameExtensionWitupTest {

    public static void main(String[] args) {
        testSetWithValidGeneralNames();
        testSetWithInvalidName();
        testSetWithUnrecognizedAttributeName();
    }

    private static void testSetWithValidGeneralNames() {
        try {
            SubjectAlternativeNameExtension ext = new SubjectAlternativeNameExtension();
            GeneralNames gn = new GeneralNames();
            ext.set("subject_name", gn);
            System.out.println("testSetWithValidGeneralNames: PASSED");
        } catch (Exception e) {
            System.out.println("testSetWithValidGeneralNames: FAILED - " + e);
        }
    }

    private static void testSetWithInvalidName() {
        try {
            SubjectAlternativeNameExtension ext = new SubjectAlternativeNameExtension();
            GeneralNames gn = new GeneralNames();
            ext.set("invalid_name", gn);
            System.out.println("testSetWithInvalidName: FAILED - Exception expected but not thrown");
        } catch (IOException e) {
            if (e.getMessage().contains("Attribute name not recognized")) {
                System.out.println("testSetWithInvalidName: PASSED");
            } else {
                System.out.println("testSetWithInvalidName: FAILED - " + e);
            }
        } catch (Exception e) {
            System.out.println("testSetWithInvalidName: FAILED - " + e);
        }
    }

    private static void testSetWithUnrecognizedAttributeName() {
        try {
            SubjectAlternativeNameExtension ext = new SubjectAlternativeNameExtension();
            ext.set("subject_name", null); // Passing null to test behavior
            System.out.println("testSetWithUnrecognizedAttributeName: PASSED");
        } catch (IOException e) {
            System.out.println("testSetWithUnrecognizedAttributeName: FAILED - " + e);
        } catch (Exception e) {
            System.out.println("testSetWithUnrecognizedAttributeName: FAILED - " + e);
        }
    }
}
