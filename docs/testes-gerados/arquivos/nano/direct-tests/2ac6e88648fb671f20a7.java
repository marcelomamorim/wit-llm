/* @test    @summary Test for SubjectAlternativeNameExtension.set method to verify correct handling of GeneralNames objects and exception throwing for invalid attribute names.    @run main SubjectAlternativeNameExtensionWitupTest */

import java.io.IOException;
import sun.security.x509.SubjectAlternativeNameExtension;
import sun.security.util.GeneralNames;

public class SubjectAlternativeNameExtensionWitupTest {

    public static void main(String[] args) {
        testSetWithValidGeneralNames();
        testSetWithInvalidName();
        testSetWithInvalidObjectType();
    }

    private static void testSetWithValidGeneralNames() {
        try {
            SubjectAlternativeNameExtension ext = new SubjectAlternativeNameExtension();
            GeneralNames genNames = new GeneralNames();
            ext.set("subject_name", genNames);
            System.out.println("testSetWithValidGeneralNames passed.");
        } catch (Exception e) {
            System.err.println("testSetWithValidGeneralNames failed: " + e);
        }
    }

    private static void testSetWithInvalidName() {
        try {
            SubjectAlternativeNameExtension ext = new SubjectAlternativeNameExtension();
            GeneralNames genNames = new GeneralNames();
            ext.set("invalid_name", genNames);
            System.err.println("testSetWithInvalidName failed: Exception expected but not thrown.");
        } catch (IOException e) {
            System.out.println("testSetWithInvalidName passed.");
        } catch (Exception e) {
            System.err.println("testSetWithInvalidName failed: " + e);
        }
    }

    private static void testSetWithInvalidObjectType() {
        try {
            SubjectAlternativeNameExtension ext = new SubjectAlternativeNameExtension();
            String invalidObject = "not a GeneralNames";
            ext.set("subject_name", invalidObject);
            System.err.println("testSetWithInvalidObjectType failed: Exception expected but not thrown.");
        } catch (IOException e) {
            System.out.println("testSetWithInvalidObjectType passed.");
        } catch (Exception e) {
            System.err.println("testSetWithInvalidObjectType failed: " + e);
        }
    }
}
