/*
 * @test
 * @summary Test SubjectInfoAccessExtension.set method behavior
 * @run main SubjectInfoAccessExtensionWitupTest
 */

import sun.security.x509.SubjectInfoAccessExtension;
import sun.security.x509.AccessDescription;
import sun.security.x509.PKIXExtensions;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SubjectInfoAccessExtensionWitupTest {
    public static void main(String[] args) throws Exception {
        testSetWithValidList();
        testSetWithInvalidType();
        testSetWithInvalidName();
    }

    private static void testSetWithValidList() throws Exception {
        List<AccessDescription> list = new ArrayList<>();
        // Create SubjectInfoAccessExtension with empty list
        SubjectInfoAccessExtension ext = new SubjectInfoAccessExtension(list);

        // Create a new list with one AccessDescription
        List<AccessDescription> newList = new ArrayList<>();
        // Use a dummy AccessDescription with valid OID and GeneralName
        AccessDescription ad = new AccessDescription(PKIXExtensions.AuthorityInfoAccess_Id, null);
        newList.add(ad);

        ext.set(SubjectInfoAccessExtension.DESCRIPTIONS, newList);

        // After set, the internal list should be the new list
        List<AccessDescription> result = (List<AccessDescription>) getAccessDescriptions(ext);
        if (result != newList) {
            throw new RuntimeException("set did not update accessDescriptions list correctly");
        }
    }

    private static void testSetWithInvalidType() {
        try {
            SubjectInfoAccessExtension ext = new SubjectInfoAccessExtension(new ArrayList<AccessDescription>());
            ext.set(SubjectInfoAccessExtension.DESCRIPTIONS, "not a list");
            throw new RuntimeException("Expected IOException for invalid type not thrown");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (!msg.equals("Attribute value should be of type List.")) {
                throw new RuntimeException("Unexpected exception message: " + msg);
            }
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception type: " + e);
        }
    }

    private static void testSetWithInvalidName() {
        try {
            SubjectInfoAccessExtension ext = new SubjectInfoAccessExtension(new ArrayList<AccessDescription>());
            ext.set("invalidName", new ArrayList<AccessDescription>());
            throw new RuntimeException("Expected IOException for invalid name not thrown");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (!msg.startsWith("Attribute name [invalidName] not recognized")) {
                throw new RuntimeException("Unexpected exception message: " + msg);
            }
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception type: " + e);
        }
    }

    // Helper method to access private field accessDescriptions via reflection
    // Reflection is used because no public getter is provided
    private static List<AccessDescription> getAccessDescriptions(SubjectInfoAccessExtension ext) throws Exception {
        java.lang.reflect.Field f = SubjectInfoAccessExtension.class.getDeclaredField("accessDescriptions");
        f.setAccessible(true);
        return (List<AccessDescription>) f.get(ext);
    }
}
