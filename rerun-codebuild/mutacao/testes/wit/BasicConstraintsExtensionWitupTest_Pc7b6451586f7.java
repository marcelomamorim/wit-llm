/* @test    @summary Test BasicConstraintsExtension.set method for valid and invalid attribute names and types    @run main BasicConstraintsExtensionWitupTest_Pc7b6451586f7 */

import java.io.IOException;
import sun.security.x509.BasicConstraintsExtension;

public class BasicConstraintsExtensionWitupTest_Pc7b6451586f7 {

    public static void main(String[] args) {
        testSetWithValidIsCa();
        testSetWithValidPathLen();
        testSetWithInvalidAttributeName();
        testSetWithInvalidTypeForIsCa();
        testSetWithInvalidTypeForPathLen();
    }

    static void testSetWithValidIsCa() {
        try {
            BasicConstraintsExtension ext = new BasicConstraintsExtension(true, 0);
            ext.set("is_ca", Boolean.FALSE);
            System.out.println("testSetWithValidIsCa passed");
        } catch (IOException e) {
            System.err.println("testSetWithValidIsCa failed: " + e);
        }
    }

    static void testSetWithValidPathLen() {
        try {
            BasicConstraintsExtension ext = new BasicConstraintsExtension(true, 0);
            ext.set("path_len", Integer.valueOf(5));
            System.out.println("testSetWithValidPathLen passed");
        } catch (IOException e) {
            System.err.println("testSetWithValidPathLen failed: " + e);
        }
    }

    static void testSetWithInvalidAttributeName() {
        try {
            BasicConstraintsExtension ext = new BasicConstraintsExtension(true, 0);
            ext.set("unknown_attr", Boolean.TRUE);
            System.err.println("testSetWithInvalidAttributeName failed: exception expected");
        } catch (IOException e) {
            System.out.println("testSetWithInvalidAttributeName passed: " + e.getMessage());
        }
    }

    static void testSetWithInvalidTypeForIsCa() {
        try {
            BasicConstraintsExtension ext = new BasicConstraintsExtension(true, 0);
            ext.set("is_ca", "notABoolean");
            System.err.println("testSetWithInvalidTypeForIsCa failed: exception expected");
        } catch (IOException e) {
            System.out.println("testSetWithInvalidTypeForIsCa passed: " + e.getMessage());
        }
    }

    static void testSetWithInvalidTypeForPathLen() {
        try {
            BasicConstraintsExtension ext = new BasicConstraintsExtension(true, 0);
            ext.set("path_len", "notAnInteger");
            System.err.println("testSetWithInvalidTypeForPathLen failed: exception expected");
        } catch (IOException e) {
            System.out.println("testSetWithInvalidTypeForPathLen passed: " + e.getMessage());
        }
    }
}
