/*
 * @test
 * @summary Test sun.security.x509.ExtendedKeyUsageExtension.set(String,Object) method
 * @run main ExtendedKeyUsageExtensionWitupTest
 */

import sun.security.x509.ExtendedKeyUsageExtension;
import sun.security.util.ObjectIdentifier;

import java.io.IOException;
import java.util.Vector;

public class ExtendedKeyUsageExtensionWitupTest {
    public static void main(String[] args) throws Exception {
        testSetWithValidUsages();
        testSetWithInvalidName();
        testSetWithInvalidObjectType();
    }

    private static void testSetWithValidUsages() throws Exception {
        Vector<ObjectIdentifier> usages = new Vector<>();
        usages.add(new ObjectIdentifier(new int[] {1, 3, 6, 1, 5, 5, 7, 3, 1})); // id-kp-serverAuth
        ExtendedKeyUsageExtension ext = new ExtendedKeyUsageExtension(false, new Vector<ObjectIdentifier>());
        ext.set("usages", usages);
        // If no exception, test passes
    }

    private static void testSetWithInvalidName() {
        ExtendedKeyUsageExtension ext = new ExtendedKeyUsageExtension(false, new Vector<ObjectIdentifier>());
        try {
            ext.set("invalidName", new Vector<ObjectIdentifier>());
            throw new RuntimeException("Expected IOException not thrown for invalid name");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (!msg.contains("not recognized")) {
                throw new RuntimeException("Unexpected IOException message: " + msg);
            }
        }
    }

    private static void testSetWithInvalidObjectType() {
        ExtendedKeyUsageExtension ext = new ExtendedKeyUsageExtension(false, new Vector<ObjectIdentifier>());
        try {
            ext.set("usages", "not a vector");
            throw new RuntimeException("Expected IOException not thrown for invalid object type");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (!msg.contains("should be of type Vector")) {
                throw new RuntimeException("Unexpected IOException message: " + msg);
            }
        }
    }
}
