/* @test    @summary Test for InvalidityDateExtension.get(String) method    @run main InvalidityDateExtensionWitupTest */

import java.io.IOException;
import java.util.Date;
import sun.security.x509.InvalidityDateExtension;

public class InvalidityDateExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        testGetWithValidDate();
        testGetWithNullDate();
        testGetWithInvalidName();
    }

    static void testGetWithValidDate() throws Exception {
        Date testDate = new Date(1630454400000L); // 2021-09-01T00:00:00Z
        InvalidityDateExtension ext = new InvalidityDateExtension(false, testDate);
        Date result = ext.get("date");
        if (result == null) {
            throw new AssertionError("Expected non-null date");
        }
        if (result.getTime() != testDate.getTime()) {
            throw new AssertionError("Returned date does not match the original date");
        }
        System.out.println("testGetWithValidDate passed");
    }

    static void testGetWithNullDate() throws Exception {
        InvalidityDateExtension ext = new InvalidityDateExtension(false, (Date) null);
        Date result = ext.get("date");
        if (result != null) {
            throw new AssertionError("Expected null date");
        }
        System.out.println("testGetWithNullDate passed");
    }

    static void testGetWithInvalidName() throws Exception {
        Date testDate = new Date(1630454400000L);
        InvalidityDateExtension ext = new InvalidityDateExtension(false, testDate);
        try {
            ext.get("invalidName");
            throw new AssertionError("Expected IOException for invalid name");
        } catch (IOException e) {
            if (!e.getMessage().contains("Name not supported")) {
                throw new AssertionError("Unexpected exception message: " + e.getMessage());
            }
        }
        System.out.println("testGetWithInvalidName passed");
    }
}
