/* @test    @summary BasicConstraintsExtension set method behavior test    @run main BasicConstraintsExtensionWitupTest_Pb78e85b3bfda */

import java.io.IOException;
import sun.security.x509.BasicConstraintsExtension;

public class BasicConstraintsExtensionWitupTest_Pb78e85b3bfda {
    public static void main(String[] args) throws Exception {
        testSetWithIsCa();
        testSetWithPathLen();
        testSetWithInvalidName();
        testSetWithInvalidType();
    }

    static void testSetWithIsCa() throws IOException {
        BasicConstraintsExtension ext = new BasicConstraintsExtension(false, 0);
        ext.set("is_ca", Boolean.TRUE);
        if (!ext.ca) {
            throw new AssertionError("Expected ca to be true after setting is_ca to true");
        }
    }

    static void testSetWithPathLen() throws IOException {
        BasicConstraintsExtension ext = new BasicConstraintsExtension(false, -1);
        ext.set("path_len", Integer.valueOf(3));
        if (ext.pathLen != 3) {
            throw new AssertionError("Expected pathLen to be 3 after setting to 3");
        }
    }

    static void testSetWithInvalidName() {
        BasicConstraintsExtension ext = new BasicConstraintsExtension(false, -1);
        try {
            ext.set("unknown_attribute", Boolean.TRUE);
            throw new AssertionError("Expected IOException for unrecognized attribute name");
        } catch (IOException e) {
            if (!e.getMessage().contains("Attribute name not recognized")) {
                throw new AssertionError("Unexpected exception message: " + e.getMessage());
            }
        }
    }

    static void testSetWithInvalidType() {
        BasicConstraintsExtension ext = new BasicConstraintsExtension(false, -1);
        try {
            ext.set("is_ca", "notABoolean");
            throw new AssertionError("Expected IOException for wrong type with is_ca");
        } catch (IOException e) {
            if (!e.getMessage().contains("Attribute value should be of type Boolean")) {
                throw new AssertionError("Unexpected exception message: " + e.getMessage());
            }
        }
        try {
            ext.set("path_len", "notAnInteger");
            throw new AssertionError("Expected IOException for wrong type with path_len");
        } catch (IOException e) {
            if (!e.getMessage().contains("Attribute value should be of type Integer")) {
                throw new AssertionError("Unexpected exception message: " + e.getMessage());
            }
        }
    }
}
