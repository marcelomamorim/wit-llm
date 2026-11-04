/*
 * @test
 * @summary Verifies that the Extension(Extension ext) copy constructor copies id, criticality and value
 * @modules java.base/sun.security.x509 java.base/sun.security.util
 * @run main ExtensionWitupTest
 */

import java.util.Arrays;
import sun.security.util.ObjectIdentifier;
import sun.security.x509.Extension;

public class ExtensionWitupTest {

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    private static void testCopyCriticalExtension() throws Exception {
        byte[] raw = {0x04, 0x02, 0x05, 0x00};
        ObjectIdentifier oid = new ObjectIdentifier("1.2.3.4");
        Extension orig = new Extension(oid, true, raw);
        Extension copy = new Extension(orig);

        check(copy != orig, "copy must be a new object");
        check(copy.getId().equals(orig.getId()), "id not copied");
        check(copy.isCritical() == orig.isCritical(), "criticality not copied");
        check(copy.isCritical(), "critical flag must be true");
        check(Arrays.equals(copy.getValue(), orig.getValue()), "value not copied");
    }

    private static void testCopyNonCriticalExtension() throws Exception {
        byte[] raw = {0x04, 0x02, 0x05, 0x00};
        ObjectIdentifier oid = new ObjectIdentifier("1.2.3.4");
        Extension orig = new Extension(oid, false, raw);
        Extension copy = new Extension(orig);

        check(copy.getId().equals("1.2.3.4"), "unexpected id in copy");
        check(!copy.isCritical(), "critical flag must be false");
        check(Arrays.equals(copy.getValue(), orig.getValue()), "value not copied");
    }

    private static void testCopyOfCopy() throws Exception {
        byte[] raw = {0x04, 0x02, 0x05, 0x00};
        ObjectIdentifier oid = new ObjectIdentifier("2.5.29.19");
        Extension orig = new Extension(oid, true, raw);
        Extension first = new Extension(orig);
        Extension second = new Extension(first);

        check(second.getId().equals(orig.getId()), "id lost in chained copy");
        check(second.isCritical() == orig.isCritical(), "criticality lost in chained copy");
        check(Arrays.equals(second.getValue(), orig.getValue()), "value lost in chained copy");
    }

    public static void main(String[] args) throws Exception {
        testCopyCriticalExtension();
        testCopyNonCriticalExtension();
        testCopyOfCopy();
    }
}
