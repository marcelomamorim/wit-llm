/* @test    @summary Test for ExtendedKeyUsageExtension.set method to ensure correct behavior and exception handling.    @run main ExtendedKeyUsageExtensionWitupTest */

import java.io.IOException;
import java.util.Vector;
import sun.security.util.ObjectIdentifier;
import sun.security.x509.ExtendedKeyUsageExtension;

public class ExtendedKeyUsageExtensionWitupTest {

    public static void main(String[] args) {
        testSetWithValidVector();
        testSetWithInvalidName();
        testSetWithInvalidObject();
    }

    static void testSetWithValidVector() {
        try {
            ExtendedKeyUsageExtension ext = new ExtendedKeyUsageExtension();
            Vector<ObjectIdentifier> usages = new Vector<>();
            usages.add(new ObjectIdentifier("1.3.6.1.5.5.7.3.1")); // serverAuth
            ext.set("usages", usages);
            System.out.println("testSetWithValidVector passed.");
        } catch (Exception e) {
            throw new RuntimeException("testSetWithValidVector failed: " + e.getMessage(), e);
        }
    }

    static void testSetWithInvalidName() {
        try {
            ExtendedKeyUsageExtension ext = new ExtendedKeyUsageExtension();
            Vector<ObjectIdentifier> usages = new Vector<>();
            usages.add(new ObjectIdentifier("1.3.6.1.5.5.7.3.2")); // clientAuth
            ext.set("invalidName", usages);
            throw new RuntimeException("testSetWithInvalidName failed: exception was expected but not thrown.");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (msg.contains("not recognized")) {
                System.out.println("testSetWithInvalidName passed.");
            } else {
                throw new RuntimeException("testSetWithInvalidName failed: " + msg, e);
            }
        } catch (Exception e) {
            throw new RuntimeException("testSetWithInvalidName failed: " + e.getMessage(), e);
        }
    }

    static void testSetWithInvalidObject() {
        try {
            ExtendedKeyUsageExtension ext = new ExtendedKeyUsageExtension();
            String invalidObject = "not a vector";
            ext.set("usages", invalidObject);
            throw new RuntimeException("testSetWithInvalidObject failed: exception was expected but not thrown.");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (msg.contains("Attribute value should be of type Vector")) {
                System.out.println("testSetWithInvalidObject passed.");
            } else {
                throw new RuntimeException("testSetWithInvalidObject failed: " + msg, e);
            }
        } catch (Exception e) {
            throw new RuntimeException("testSetWithInvalidObject failed: " + e.getMessage(), e);
        }
    }
}
