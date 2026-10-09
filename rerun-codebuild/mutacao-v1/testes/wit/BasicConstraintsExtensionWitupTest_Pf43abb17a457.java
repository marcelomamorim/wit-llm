/* @test    @summary Test for BasicConstraintsExtension.set(String, Object) method handling valid and invalid attribute names and types    @run main BasicConstraintsExtensionWitupTest_Pf43abb17a457 */

import sun.security.x509.BasicConstraintsExtension;
import java.io.IOException;

public class BasicConstraintsExtensionWitupTest_Pf43abb17a457 {

    public static void main(String[] args) throws Exception {
        testSetWithIsCaBoolean();
        testSetWithPathLenInteger();
        testSetWithInvalidAttributeName();
        testSetWithInvalidTypeForIsCa();
        testSetWithInvalidTypeForPathLen();
    }

    private static void testSetWithIsCaBoolean() throws IOException {
        BasicConstraintsExtension ext = new BasicConstraintsExtension(true, 0);
        ext.set("is_ca", Boolean.FALSE);
        if (ext.ca != false) {
            throw new AssertionError("Failed: is_ca attribute not set correctly to false");
        }
        ext.set("IS_CA", Boolean.TRUE);
        if (ext.ca != true) {
            throw new AssertionError("Failed: is_ca attribute not set correctly to true with uppercase");
        }
    }

    private static void testSetWithPathLenInteger() throws IOException {
        BasicConstraintsExtension ext = new BasicConstraintsExtension(true, 0);
        ext.set("path_len", Integer.valueOf(5));
        if (ext.pathLen != 5) {
            throw new AssertionError("Failed: pathLen attribute not set correctly to 5");
        }
        ext.set("PATH_LEN", Integer.valueOf(10));
        if (ext.pathLen != 10) {
            throw new AssertionError("Failed: pathLen attribute not set correctly to 10 with uppercase");
        }
    }

    private static void testSetWithInvalidAttributeName() {
        BasicConstraintsExtension ext;
        try {
            ext = new BasicConstraintsExtension(true, 0);
            ext.set("unknown_attr", Boolean.TRUE);
            throw new AssertionError("Failed: No exception thrown for invalid attribute name");
        } catch (IOException e) {
            if (!e.getMessage().contains("Attribute name not recognized")) {
                throw new AssertionError("Failed: Unexpected exception message: " + e.getMessage());
            }
        }
    }

    private static void testSetWithInvalidTypeForIsCa() {
        BasicConstraintsExtension ext;
        try {
            ext = new BasicConstraintsExtension(true, 0);
            ext.set("is_ca", "notABoolean");
            throw new AssertionError("Failed: No exception thrown for invalid type for is_ca");
        } catch (IOException e) {
            if (!e.getMessage().contains("Attribute value should be of type Boolean")) {
                throw new AssertionError("Failed: Unexpected exception message: " + e.getMessage());
            }
        }
    }

    private static void testSetWithInvalidTypeForPathLen() {
        BasicConstraintsExtension ext;
        try {
            ext = new BasicConstraintsExtension(true, 0);
            ext.set("path_len", "notAnInteger");
            throw new AssertionError("Failed: No exception thrown for invalid type for path_len");
        } catch (IOException e) {
            if (!e.getMessage().contains("Attribute value should be of type Integer")) {
                throw new AssertionError("Failed: Unexpected exception message: " + e.getMessage());
            }
        }
    }
}
