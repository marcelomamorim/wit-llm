/* @test    @summary Test delete method of SubjectAlternativeNameExtension class    @run main SubjectAlternativeNameExtensionWitupTest */

import java.io.IOException;
import sun.security.x509.SubjectAlternativeNameExtension;
import sun.security.util.GeneralNames;

public class SubjectAlternativeNameExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        testDeleteWithValidName();
        testDeleteWithInvalidName();
    }

    private static void testDeleteWithValidName() throws IOException {
        GeneralNames genNames = new GeneralNames();
        SubjectAlternativeNameExtension ext = new SubjectAlternativeNameExtension(genNames);
        ext.delete("subject_name");
        if (ext.names != null) {
            throw new AssertionError("Expected names to be null after delete.");
        }
        System.out.println("testDeleteWithValidName passed.");
    }

    private static void testDeleteWithInvalidName() {
        GeneralNames genNames = new GeneralNames();
        SubjectAlternativeNameExtension ext;
        try {
            ext = new SubjectAlternativeNameExtension(genNames);
            ext.delete("invalid_name");
            throw new AssertionError("Expected IOException for invalid name.");
        } catch (IOException e) {
            if (!e.getMessage().contains("Attribute name not recognized")) {
                throw new AssertionError("Unexpected exception message: " + e.getMessage());
            }
            System.out.println("testDeleteWithInvalidName passed.");
        }
    }
}
