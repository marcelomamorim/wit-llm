/*
 * @test
 * @summary Verifies ExtendedKeyUsageExtension.delete(String) behavior for the USAGES attribute and unknown names
 * @modules java.base/sun.security.x509
 * @run main ExtendedKeyUsageExtensionWitupTest
 */

import java.io.IOException;
import java.util.Vector;

import sun.security.x509.ExtendedKeyUsageExtension;

public class ExtendedKeyUsageExtensionWitupTest {

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    static ExtendedKeyUsageExtension newExt() throws IOException {
        return new ExtendedKeyUsageExtension(new Vector<>());
    }

    public static void main(String[] args) throws Exception {
        testDeleteUsagesSucceeds();
        testDeleteUsagesIsCaseInsensitive();
        testDeleteUnknownNameThrowsIOException();
        testDeleteUnknownNameMessageContainsName();
        testDeleteNullNameThrowsNPE();
        testDeleteTwiceUsages();
    }

    static void testDeleteUsagesSucceeds() throws Exception {
        ExtendedKeyUsageExtension ext = newExt();
        ext.delete(ExtendedKeyUsageExtension.USAGES);
    }

    static void testDeleteUsagesIsCaseInsensitive() throws Exception {
        ExtendedKeyUsageExtension ext = newExt();
        ext.delete("USAGES");
        ext.delete("Usages");
    }

    static void testDeleteUnknownNameThrowsIOException() throws Exception {
        ExtendedKeyUsageExtension ext = newExt();
        boolean thrown = false;
        try {
            ext.delete("notAnAttribute");
        } catch (IOException e) {
            thrown = true;
        }
        check(thrown, "expected IOException for unknown attribute name");
    }

    static void testDeleteUnknownNameMessageContainsName() throws Exception {
        ExtendedKeyUsageExtension ext = newExt();
        String name = "bogusName";
        String msg = null;
        try {
            ext.delete(name);
        } catch (IOException e) {
            msg = e.getMessage();
        }
        check(msg != null, "expected IOException with message");
        check(msg.contains(name), "message should contain the attribute name: " + msg);
    }

    static void testDeleteNullNameThrowsNPE() throws Exception {
        ExtendedKeyUsageExtension ext = newExt();
        boolean npe = false;
        try {
            ext.delete(null);
        } catch (NullPointerException e) {
            npe = true;
        }
        check(npe, "expected NullPointerException for null name");
    }

    static void testDeleteTwiceUsages() throws Exception {
        ExtendedKeyUsageExtension ext = newExt();
        ext.delete(ExtendedKeyUsageExtension.USAGES);
        ext.delete(ExtendedKeyUsageExtension.USAGES);
    }
}
