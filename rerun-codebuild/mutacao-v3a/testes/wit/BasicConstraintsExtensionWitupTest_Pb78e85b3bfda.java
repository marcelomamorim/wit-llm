/*
 * @test
 * @summary Test BasicConstraintsExtension.set method with valid and invalid inputs.
 * @run main BasicConstraintsExtensionWitupTest_Pb78e85b3bfda
 */

import java.io.IOException;
import sun.security.x509.BasicConstraintsExtension;

public class BasicConstraintsExtensionWitupTest_Pb78e85b3bfda {

    public static void main(String[] args) throws IOException {
        testSetWithValidBoolean();
        testSetWithInvalidBoolean();
        testSetWithValidInteger();
        testSetWithInvalidInteger();
        testSetWithUnrecognizedName();
    }

    static void testSetWithValidBoolean() throws IOException {
        BasicConstraintsExtension ext = new BasicConstraintsExtension(true, 0);
        ext.set("is_ca", Boolean.FALSE);
        System.out.println("testSetWithValidBoolean passed");
    }

    static void testSetWithInvalidBoolean() {
        try {
            BasicConstraintsExtension ext = new BasicConstraintsExtension(true, 0);
            ext.set("is_ca", "notABoolean");
            System.err.println("testSetWithInvalidBoolean failed: no exception thrown");
        } catch (IOException e) {
            System.out.println("testSetWithInvalidBoolean passed: " + e.getMessage());
        }
    }

    static void testSetWithValidInteger() throws IOException {
        BasicConstraintsExtension ext = new BasicConstraintsExtension(true, 0);
        ext.set("path_len", Integer.valueOf(5));
        System.out.println("testSetWithValidInteger passed");
    }

    static void testSetWithInvalidInteger() {
        try {
            BasicConstraintsExtension ext = new BasicConstraintsExtension(true, 0);
            ext.set("path_len", "notAnInteger");
            System.err.println("testSetWithInvalidInteger failed: no exception thrown");
        } catch (IOException e) {
            System.out.println("testSetWithInvalidInteger passed: " + e.getMessage());
        }
    }

    static void testSetWithUnrecognizedName() {
        try {
            BasicConstraintsExtension ext = new BasicConstraintsExtension(true, 0);
            ext.set("unknown_attr", Integer.valueOf(1));
            System.err.println("testSetWithUnrecognizedName failed: no exception thrown");
        } catch (IOException e) {
            System.out.println("testSetWithUnrecognizedName passed: " + e.getMessage());
        }
    }
}
