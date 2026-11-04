/* @test    @summary Test for ExtendedKeyUsageExtension.set(String, Object) method.
 * @run main ExtendedKeyUsageExtensionWitupTest
 */

import sun.security.x509.ExtendedKeyUsageExtension;
import java.io.IOException;
import java.util.Vector;
import sun.security.util.ObjectIdentifier;

public class ExtendedKeyUsageExtensionWitupTest {

    public static void main(String[] args) {
        testSetWithValidUsages();
        testSetWithInvalidName();
        testSetWithInvalidObjectType();
    }

    private static void testSetWithValidUsages() {
        try {
            ExtendedKeyUsageExtension ext = new ExtendedKeyUsageExtension();
            Vector<ObjectIdentifier> usages = new Vector<>();
            usages.add(new ObjectIdentifier("1.3.6.1.5.5.7.3.1")); // serverAuth
            ext.set("usages", usages);
            System.out.println("testSetWithValidUsages passed.");
        } catch (IOException e) {
            throw new RuntimeException("Unexpected exception in testSetWithValidUsages", e);
        }
    }

    private static void testSetWithInvalidName() {
        try {
            ExtendedKeyUsageExtension ext = new ExtendedKeyUsageExtension();
            Vector<ObjectIdentifier> usages = new Vector<>();
            usages.add(new ObjectIdentifier("1.3.6.1.5.5.7.3.1"));
            ext.set("invalidName", usages);
            throw new AssertionError("Expected IOException for invalid name not thrown.");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (msg != null && msg.contains("not recognized")) {
                System.out.println("testSetWithInvalidName passed.");
            } else {
                throw new RuntimeException("Unexpected exception message: " + msg, e);
            }
        }
    }

    private static void testSetWithInvalidObjectType() {
        try {
            ExtendedKeyUsageExtension ext = new ExtendedKeyUsageExtension();
            String invalidObj = "not a vector";
            ext.set("usages", invalidObj);
            throw new AssertionError("Expected IOException for invalid object type not thrown.");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (msg != null && msg.contains("Attribute value should be of type Vector")) {
                System.out.println("testSetWithInvalidObjectType passed.");
            } else {
                throw new RuntimeException("Unexpected exception message: " + msg, e);
            }
        }
    }
}
