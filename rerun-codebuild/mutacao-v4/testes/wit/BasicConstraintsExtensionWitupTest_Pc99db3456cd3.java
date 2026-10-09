/* @test
   @summary Tests the set method of BasicConstraintsExtension for valid and invalid attribute names.
   @run main BasicConstraintsExtensionWitupTest_Pc99db3456cd3
 */
import java.io.IOException;
import sun.security.x509.BasicConstraintsExtension;
public class BasicConstraintsExtensionWitupTest_Pc99db3456cd3 {
    public static void main(String[] args) throws Exception {
        testSetWithValidIsCa();
        testSetWithValidPathLen();
        testSetWithInvalidName();
        testSetWithInvalidType();
    }

    static void testSetWithValidIsCa() throws Exception {
        BasicConstraintsExtension ext = new BasicConstraintsExtension(true, 3);
        ext.set("is_ca", Boolean.FALSE);
        if (ext.ca != false) {
            throw new AssertionError("Expected ca to be false after set");
        }
        ext.encodeThis(); // to ensure no exception
    }

    static void testSetWithValidPathLen() throws Exception {
        BasicConstraintsExtension ext = new BasicConstraintsExtension(true, 3);
        ext.set("path_len", Integer.valueOf(5));
        if (ext.pathLen != 5) {
            throw new AssertionError("Expected pathLen to be 5 after set");
        }
        ext.encodeThis(); // to ensure no exception
    }

    static void testSetWithInvalidName() {
        BasicConstraintsExtension ext = new BasicConstraintsExtension(true, 3);
        try {
            ext.set("unknown_attribute", Boolean.TRUE);
            throw new AssertionError("Expected IOException for unknown attribute name");
        } catch (IOException e) {
            if (!e.getMessage().contains("Attribute name not recognized")) {
                throw new AssertionError("Unexpected exception message: " + e.getMessage());
            }
        }
    }

    static void testSetWithInvalidType() {
        BasicConstraintsExtension ext = new BasicConstraintsExtension(true, 3);
        try {
            ext.set("is_ca", "notABoolean");
            throw new AssertionError("Expected IOException for invalid type on is_ca");
        } catch (IOException e) {
            if (!e.getMessage().contains("Attribute value should be of type Boolean")) {
                throw new AssertionError("Unexpected exception message: " + e.getMessage());
            }
        }
        try {
            ext.set("path_len", "notAnInteger");
            throw new AssertionError("Expected IOException for invalid type on path_len");
        } catch (IOException e) {
            if (!e.getMessage().contains("Attribute value should be of type Integer")) {
                throw new AssertionError("Unexpected exception message: " + e.getMessage());
            }
        }
    }
}
