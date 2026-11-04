/*
 * @test
 * @summary Test SubjectInfoAccessExtension.set(String,Object) for valid and invalid inputs
 * @run main SubjectInfoAccessExtensionWitupTest
 */

import sun.security.x509.SubjectInfoAccessExtension;
import sun.security.x509.AccessDescription;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SubjectInfoAccessExtensionWitupTest {
    public static void main(String[] args) throws Exception {
        testSetWithValidDescriptions();
        testSetWithInvalidName();
        testSetWithInvalidObjectType();
    }

    private static void testSetWithValidDescriptions() throws Exception {
        List<AccessDescription> list = new ArrayList<>();
        // We do not add actual AccessDescription instances because construction is unclear
        // but an empty list is still a valid List<AccessDescription>
        SubjectInfoAccessExtension ext = new SubjectInfoAccessExtension(list);
        ext.set("descriptions", list);
        // If no exception, test passes
    }

    private static void testSetWithInvalidName() {
        try {
            List<AccessDescription> list = new ArrayList<>();
            SubjectInfoAccessExtension ext = new SubjectInfoAccessExtension(list);
            ext.set("invalidName", list);
            throw new RuntimeException("Expected IOException not thrown for invalid name");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (!msg.contains("not recognized")) {
                throw new RuntimeException("Unexpected IOException message: " + msg);
            }
        }
    }

    private static void testSetWithInvalidObjectType() {
        try {
            List<AccessDescription> list = new ArrayList<>();
            SubjectInfoAccessExtension ext = new SubjectInfoAccessExtension(list);
            ext.set("descriptions", "not a list");
            throw new RuntimeException("Expected IOException not thrown for invalid object type");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (!msg.contains("should be of type List")) {
                throw new RuntimeException("Unexpected IOException message: " + msg);
            }
        }
    }
}
