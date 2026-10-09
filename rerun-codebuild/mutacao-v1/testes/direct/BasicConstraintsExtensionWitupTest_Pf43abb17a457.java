/* @test    @summary Test BasicConstraintsExtension.set method for valid and invalid inputs    @run main BasicConstraintsExtensionWitupTest_Pf43abb17a457 */

import java.io.IOException;
import sun.security.x509.BasicConstraintsExtension;

public class BasicConstraintsExtensionWitupTest_Pf43abb17a457 {

    public static void main(String[] args) {
        testSetIsCaValid();
        testSetPathLenValid();
        testSetInvalidName();
        testSetInvalidTypeIsCa();
        testSetInvalidTypePathLen();
    }

    static void testSetIsCaValid() {
        try {
            BasicConstraintsExtension ext = new BasicConstraintsExtension(true, 0);
            ext.set("is_ca", Boolean.FALSE);
            System.out.println("PASS: set is_ca to false");
        } catch (Exception e) {
            throw new AssertionError("FAIL: set is_ca to false", e);
        }
    }

    static void testSetPathLenValid() {
        try {
            BasicConstraintsExtension ext = new BasicConstraintsExtension(true, 0);
            ext.set("path_len", Integer.valueOf(5));
            System.out.println("PASS: set path_len to 5");
        } catch (Exception e) {
            throw new AssertionError("FAIL: set path_len to 5", e);
        }
    }

    static void testSetInvalidName() {
        try {
            BasicConstraintsExtension ext = new BasicConstraintsExtension(true, 0);
            ext.set("unknown_attribute", Boolean.TRUE);
            throw new AssertionError("FAIL: did not throw on invalid attribute name");
        } catch (IOException e) {
            if (e.getMessage().contains("Attribute name not recognized")) {
                System.out.println("PASS: invalid attribute name throws IOException");
            } else {
                throw new AssertionError("FAIL: unexpected IOException message", e);
            }
        } catch (Exception e) {
            throw new AssertionError("FAIL: unexpected exception type", e);
        }
    }

    static void testSetInvalidTypeIsCa() {
        try {
            BasicConstraintsExtension ext = new BasicConstraintsExtension(true, 0);
            ext.set("is_ca", "notABoolean");
            throw new AssertionError("FAIL: did not throw on invalid type for is_ca");
        } catch (IOException e) {
            if (e.getMessage().contains("Attribute value should be of type Boolean")) {
                System.out.println("PASS: invalid type for is_ca throws IOException");
            } else {
                throw new AssertionError("FAIL: unexpected IOException message", e);
            }
        } catch (Exception e) {
            throw new AssertionError("FAIL: unexpected exception type", e);
        }
    }

    static void testSetInvalidTypePathLen() {
        try {
            BasicConstraintsExtension ext = new BasicConstraintsExtension(true, 0);
            ext.set("path_len", "notAnInteger");
            throw new AssertionError("FAIL: did not throw on invalid type for path_len");
        } catch (IOException e) {
            if (e.getMessage().contains("Attribute value should be of type Integer")) {
                System.out.println("PASS: invalid type for path_len throws IOException");
            } else {
                throw new AssertionError("FAIL: unexpected IOException message", e);
            }
        } catch (Exception e) {
            throw new AssertionError("FAIL: unexpected exception type", e);
        }
    }
}
