/* @test    @summary Test BasicConstraintsExtension.set method for valid and invalid inputs    @run main BasicConstraintsExtensionWitupTest_Pc7b6451586f7 */

import java.io.IOException;
import sun.security.x509.BasicConstraintsExtension;

public class BasicConstraintsExtensionWitupTest_Pc7b6451586f7 {

    public static void main(String[] args) {
        testSetWithValidBoolean();
        testSetWithInvalidBoolean();
        testSetWithValidInteger();
        testSetWithInvalidInteger();
        testSetWithUnknownAttribute();
    }

    static void testSetWithValidBoolean() {
        try {
            BasicConstraintsExtension ext = new BasicConstraintsExtension(true, 0);
            ext.set("is_ca", Boolean.FALSE);
            System.out.println("testSetWithValidBoolean passed");
        } catch (IOException e) {
            System.err.println("testSetWithValidBoolean failed: " + e.getMessage());
        }
    }

    static void testSetWithInvalidBoolean() {
        try {
            BasicConstraintsExtension ext = new BasicConstraintsExtension(true, 0);
            ext.set("is_ca", "notABoolean");
            System.err.println("testSetWithInvalidBoolean failed: exception expected");
        } catch (IOException e) {
            System.out.println("testSetWithInvalidBoolean passed: " + e.getMessage());
        }
    }

    static void testSetWithValidInteger() {
        try {
            BasicConstraintsExtension ext = new BasicConstraintsExtension(true, 0);
            ext.set("path_len", Integer.valueOf(5));
            System.out.println("testSetWithValidInteger passed");
        } catch (IOException e) {
            System.err.println("testSetWithValidInteger failed: " + e.getMessage());
        }
    }

    static void testSetWithInvalidInteger() {
        try {
            BasicConstraintsExtension ext = new BasicConstraintsExtension(true, 0);
            ext.set("path_len", "notAnInteger");
            System.err.println("testSetWithInvalidInteger failed: exception expected");
        } catch (IOException e) {
            System.out.println("testSetWithInvalidInteger passed: " + e.getMessage());
        }
    }

    static void testSetWithUnknownAttribute() {
        try {
            BasicConstraintsExtension ext = new BasicConstraintsExtension(true, 0);
            ext.set("unknown_attr", Boolean.TRUE);
            System.err.println("testSetWithUnknownAttribute failed: exception expected");
        } catch (IOException e) {
            System.out.println("testSetWithUnknownAttribute passed: " + e.getMessage());
        }
    }
}
