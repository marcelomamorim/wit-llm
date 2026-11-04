/* @test    @summary Test for SubjectInfoAccessExtension.set method behavior    @run main SubjectInfoAccessExtensionWitupTest */

import java.io.IOException;
import java.util.*;
import sun.security.x509.SubjectInfoAccessExtension;
import sun.security.x509.AccessDescription;

public class SubjectInfoAccessExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        testSetWithValidList();
        testSetWithInvalidName();
        testSetWithInvalidType();
    }

    static void testSetWithValidList() throws IOException {
        List<AccessDescription> list = new ArrayList<>();
        SubjectInfoAccessExtension ext = new SubjectInfoAccessExtension(list);
        ext.set("descriptions", list);
        System.out.println("testSetWithValidList passed");
    }

    static void testSetWithInvalidName() {
        List<AccessDescription> list = new ArrayList<>();
        SubjectInfoAccessExtension ext = new SubjectInfoAccessExtension(list);
        try {
            ext.set("invalidName", list);
            throw new AssertionError("Expected IOException for invalid name");
        } catch (IOException e) {
            if (!e.getMessage().contains("not recognized")) {
                throw new AssertionError("Unexpected exception message: " + e.getMessage());
            }
        }
        System.out.println("testSetWithInvalidName passed");
    }

    static void testSetWithInvalidType() {
        List<AccessDescription> list = new ArrayList<>();
        SubjectInfoAccessExtension ext = new SubjectInfoAccessExtension(list);
        try {
            ext.set("descriptions", "not a list");
            throw new AssertionError("Expected IOException for invalid type");
        } catch (IOException e) {
            if (!e.getMessage().contains("Attribute value should be of type List")) {
                throw new AssertionError("Unexpected exception message: " + e.getMessage());
            }
        }
        System.out.println("testSetWithInvalidType passed");
    }
}
