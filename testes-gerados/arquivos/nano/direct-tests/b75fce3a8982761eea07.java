/* @test    @summary Test delete method of InvalidityDateExtension for valid and invalid names    @run main InvalidityDateExtensionWitupTest */

import java.io.IOException;
import java.util.Date;
import sun.security.x509.InvalidityDateExtension;

public class InvalidityDateExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        testDeleteWithValidName();
        testDeleteWithInvalidName();
    }

    static void testDeleteWithValidName() throws Exception {
        Date date = new Date();
        InvalidityDateExtension ext = new InvalidityDateExtension(false, date);
        ext.delete("date");
        // After deletion, date should be null
        if (ext.getClass().getDeclaredField("date").get(ext) != null) {
            throw new AssertionError("Expected date to be null after delete");
        }
    }

    static void testDeleteWithInvalidName() {
        Date date = new Date();
        try {
            InvalidityDateExtension ext = new InvalidityDateExtension(false, date);
            ext.delete("invalidName");
            throw new AssertionError("Expected IOException for invalid name");
        } catch (IOException e) {
            if (!e.getMessage().equals("Name not supported by InvalidityDateExtension")) {
                throw new AssertionError("Unexpected exception message: " + e.getMessage());
            }
        }
    }
}
