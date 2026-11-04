/* @test    @summary Test for sun.security.x509.IssuerAlternativeNameExtension.set(String, Object) handling valid and invalid inputs    @run main IssuerAlternativeNameExtensionWitupTest */

import java.io.IOException;
import sun.security.x509.IssuerAlternativeNameExtension;
import sun.security.util.GeneralNames;

public class IssuerAlternativeNameExtensionWitupTest {

    public static void main(String[] args) {
        testSetWithValidGeneralNames();
        testSetWithInvalidName();
        testSetWithUnrecognizedName();
    }

    private static void testSetWithValidGeneralNames() {
        try {
            GeneralNames gn = new GeneralNames();
            IssuerAlternativeNameExtension ext = new IssuerAlternativeNameExtension();
            ext.set("issuer_name", gn);
            System.out.println("testSetWithValidGeneralNames: PASSED");
        } catch (Exception e) {
            System.out.println("testSetWithValidGeneralNames: FAILED - " + e);
        }
    }

    private static void testSetWithInvalidName() {
        try {
            GeneralNames gn = new GeneralNames();
            IssuerAlternativeNameExtension ext = new IssuerAlternativeNameExtension();
            ext.set("invalid_name", gn);
            System.out.println("testSetWithInvalidName: FAILED - no exception thrown");
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

    private static void testSetWithUnrecognizedObjectType() {
        try {
            IssuerAlternativeNameExtension ext = new IssuerAlternativeNameExtension();
            ext.set("issuer_name", new Object());
            System.out.println("testSetWithUnrecognizedObjectType: PASSED");
        } catch (IOException e) {
            if (e.getMessage().contains("Attribute value should be of")) {
                System.out.println("testSetWithUnrecognizedObjectType: PASSED");
            } else {
                System.out.println("testSetWithUnrecognizedObjectType: FAILED - " + e);
            }
        } catch (Exception e) {
            System.out.println("testSetWithUnrecognizedObjectType: FAILED - " + e);
        }
    }
}
