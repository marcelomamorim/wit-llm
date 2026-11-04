/*
 * @test
 * @summary Witup generated tests for CRLReasonCodeExtension.get(String)
 * @modules java.base/sun.security.x509
 * @run main CRLReasonCodeExtensionWitupTest
 */

import java.io.IOException;
import sun.security.x509.CRLReasonCodeExtension;

public class CRLReasonCodeExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        testGetReasonReturnsCode();
        testGetReasonIsCaseInsensitive();
        testGetReasonZero();
        testGetReasonFromDerConstructorPath();
        testGetUnsupportedNameThrowsIOException();
        testGetEmptyNameThrowsIOException();
        testGetNullNameThrowsNullPointerException();
        System.out.println("PASSED");
    }

    private static void testGetReasonReturnsCode() throws IOException {
        CRLReasonCodeExtension ext = new CRLReasonCodeExtension(1);
        check(Integer.valueOf(1).equals(ext.get("reason")), "reason should return 1");
    }

    private static void testGetReasonIsCaseInsensitive() throws IOException {
        CRLReasonCodeExtension ext = new CRLReasonCodeExtension(3);
        check(Integer.valueOf(3).equals(ext.get("REASON")), "REASON should return 3");
        check(Integer.valueOf(3).equals(ext.get("Reason")), "Reason should return 3");
    }

    private static void testGetReasonZero() throws IOException {
        CRLReasonCodeExtension ext = new CRLReasonCodeExtension(0);
        check(Integer.valueOf(0).equals(ext.get("reason")), "reason should return 0");
    }

    private static void testGetReasonFromDerConstructorPath() throws IOException {
        CRLReasonCodeExtension ext = new CRLReasonCodeExtension(true, 4);
        check(Integer.valueOf(4).equals(ext.get("reason")), "reason should return 4 for critical extension");
    }

    private static void testGetUnsupportedNameThrowsIOException() throws IOException {
        CRLReasonCodeExtension ext = new CRLReasonCodeExtension(1);
        try {
            ext.get("bogus");
            fail("expected IOException for unsupported name");
        } catch (IOException expected) {
        }
    }

    private static void testGetEmptyNameThrowsIOException() throws IOException {
        CRLReasonCodeExtension ext = new CRLReasonCodeExtension(1);
        try {
            ext.get("");
            fail("expected IOException for empty name");
        } catch (IOException expected) {
        }
    }

    private static void testGetNullNameThrowsNullPointerException() throws IOException {
        CRLReasonCodeExtension ext = new CRLReasonCodeExtension(1);
        try {
            ext.get(null);
            fail("expected NullPointerException for null name");
        } catch (NullPointerException expected) {
        }
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    private static void fail(String message) {
        throw new RuntimeException(message);
    }
}
