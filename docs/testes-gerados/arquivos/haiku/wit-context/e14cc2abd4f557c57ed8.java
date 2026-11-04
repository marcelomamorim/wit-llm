/*
 * @test
 * @summary Checks that getExtendedKeyUsage returns an empty, independent List<String> for an extension built with no key usages
 * @modules java.base/sun.security.x509
 * @run main ExtendedKeyUsageExtensionWitupTest
 */

import java.util.List;
import java.util.Vector;
import sun.security.x509.ExtendedKeyUsageExtension;

public class ExtendedKeyUsageExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        ExtendedKeyUsageExtension ext = new ExtendedKeyUsageExtension(new Vector<>());

        List<String> first = ext.getExtendedKeyUsage();
        if (first == null) {
            throw new AssertionError("getExtendedKeyUsage returned null");
        }
        if (!first.isEmpty()) {
            throw new AssertionError("Expected empty list but got: " + first);
        }

        List<String> second = ext.getExtendedKeyUsage();
        if (second == first) {
            throw new AssertionError("Expected a new list on each call");
        }
        if (!second.isEmpty()) {
            throw new AssertionError("Expected empty list on second call but got: " + second);
        }

        first.add("1.3.6.1.5.5.7.3.1");
        List<String> third = ext.getExtendedKeyUsage();
        if (!third.isEmpty()) {
            throw new AssertionError("Mutating a returned list must not affect later results: " + third);
        }
    }
}
