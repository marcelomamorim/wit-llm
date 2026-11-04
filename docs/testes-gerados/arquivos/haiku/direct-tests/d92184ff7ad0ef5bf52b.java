/*
 * @test
 * @summary Verify public get(String) behavior of InvalidityDateExtension
 * @modules java.base/sun.security.x509
 * @run main InvalidityDateExtensionWitupTest
 */

import java.io.IOException;
import java.util.Date;
import sun.security.x509.InvalidityDateExtension;

public class InvalidityDateExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        testGetReturnsDate();
        testGetIsCaseInsensitive();
        testGetReturnsCopy();
        testNullDateReturnsNull();
        testCriticalConstructorKeepsDate();
        testUnsupportedNameThrowsIOException();
    }

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    static void testGetReturnsDate() throws IOException {
        Date d = new Date(1000000000000L);
        InvalidityDateExtension ext = new InvalidityDateExtension(d);
        Date r = ext.get(InvalidityDateExtension.DATE);
        check(r != null, "get(DATE) returned null for non-null date");
        check(r.getTime() == 1000000000000L, "get(DATE) returned wrong time: " + r.getTime());
    }

    static void testGetIsCaseInsensitive() throws IOException {
        Date d = new Date(1234567890000L);
        InvalidityDateExtension ext = new InvalidityDateExtension(d);
        Date upper = ext.get("DATE");
        Date mixed = ext.get("DaTe");
        check(upper != null && upper.getTime() == 1234567890000L, "uppercase name not handled");
        check(mixed != null && mixed.getTime() == 1234567890000L, "mixed-case name not handled");
    }

    static void testGetReturnsCopy() throws IOException {
        Date d = new Date(1000000000000L);
        InvalidityDateExtension ext = new InvalidityDateExtension(d);
        Date first = ext.get(InvalidityDateExtension.DATE);
        first.setTime(0L);
        Date second = ext.get(InvalidityDateExtension.DATE);
        check(second.getTime() == 1000000000000L, "internal date was modified via returned value");
        check(first != second, "get returned the same instance twice");
    }

    static void testNullDateReturnsNull() throws IOException {
        InvalidityDateExtension ext = new InvalidityDateExtension(null);
        check(ext.get(InvalidityDateExtension.DATE) == null, "expected null for null date");
    }

    static void testCriticalConstructorKeepsDate() throws IOException {
        Date d = new Date(2000000000000L);
        InvalidityDateExtension ext = new InvalidityDateExtension(true, d);
        Date r = ext.get(InvalidityDateExtension.DATE);
        check(r != null && r.getTime() == 2000000000000L, "critical constructor changed date");
    }

    static void testUnsupportedNameThrowsIOException() {
        InvalidityDateExtension ext = new InvalidityDateExtension(new Date(0L)) ;
        try {
            ext.get("foo");
            throw new RuntimeException("expected IOException for unsupported name");
        } catch (IOException expected) {
        }
    }
}
