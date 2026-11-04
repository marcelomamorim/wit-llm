/*
 * @test
 * @summary Checks public get(String) behavior of SubjectInfoAccessExtension
 * @modules java.base/sun.security.x509
 * @run main SubjectInfoAccessExtensionWitupTest
 */

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import sun.security.x509.AccessDescription;
import sun.security.x509.SubjectInfoAccessExtension;

public class SubjectInfoAccessExtensionWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testGetDescriptionsReturnsSameList() throws IOException {
        List<AccessDescription> list = new ArrayList<AccessDescription>();
        SubjectInfoAccessExtension ext = new SubjectInfoAccessExtension(list);
        List<AccessDescription> result = ext.get(SubjectInfoAccessExtension.DESCRIPTIONS);
        check(result == list, "get(DESCRIPTIONS) should return the list passed to the constructor");
    }

    static void testGetIsCaseInsensitive() throws IOException {
        List<AccessDescription> list = new ArrayList<AccessDescription>();
        SubjectInfoAccessExtension ext = new SubjectInfoAccessExtension(list);
        check(ext.get("DESCRIPTIONS") == list, "upper-case name should be accepted");
        check(ext.get("Descriptions") == list, "mixed-case name should be accepted");
    }

    static void testGetUnknownNameThrows() throws IOException {
        List<AccessDescription> list = new ArrayList<AccessDescription>();
        SubjectInfoAccessExtension ext = new SubjectInfoAccessExtension(list);
        boolean thrown = false;
        try {
            ext.get("unknown");
        } catch (IOException e) {
            thrown = true;
        }
        check(thrown, "get with unrecognized name should throw IOException");
    }

    public static void main(String[] args) throws Exception {
        testGetDescriptionsReturnsSameList();
        testGetIsCaseInsensitive();
        testGetUnknownNameThrows();
    }
}
