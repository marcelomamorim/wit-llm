/*
 * @test
 * @summary Verify that getExtendedKeyUsage returns the dotted OID strings in insertion order and returns a defensive copy
 * @modules java.base/sun.security.x509
 *          java.base/sun.security.util
 * @run main ExtendedKeyUsageExtensionWitupTest
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

import sun.security.util.ObjectIdentifier;
import sun.security.x509.ExtendedKeyUsageExtension;

public class ExtendedKeyUsageExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        Vector<ObjectIdentifier> usages = new Vector<>();
        usages.add(new ObjectIdentifier("1.3.6.1.5.5.7.3.1"));
        usages.add(new ObjectIdentifier("1.3.6.1.5.5.7.3.2"));

        ExtendedKeyUsageExtension ext = new ExtendedKeyUsageExtension(usages);
        List<String> got = ext.getExtendedKeyUsage();
        List<String> expected = Arrays.asList("1.3.6.1.5.5.7.3.1", "1.3.6.1.5.5.7.3.2");
        check(got.equals(expected), "unexpected list: " + got);

        // Returned list must be a new copy: mutating it must not affect the extension.
        got.add("9.9.9");
        List<String> again = ext.getExtendedKeyUsage();
        check(again.equals(expected), "extension state changed by caller: " + again);

        // Critical constructor must expose the same usages.
        ExtendedKeyUsageExtension critical = new ExtendedKeyUsageExtension(Boolean.TRUE, usages);
        check(critical.getExtendedKeyUsage().equals(expected),
              "critical extension list mismatch: " + critical.getExtendedKeyUsage());

        // Single-element case.
        Vector<ObjectIdentifier> single = new Vector<>();
        single.add(new ObjectIdentifier("1.3.6.1.5.5.7.3.1"));
        List<String> one = new ExtendedKeyUsageExtension(single).getExtendedKeyUsage();
        check(one.size() == 1 && one.get(0).equals("1.3.6.1.5.5.7.3.1"),
              "single element mismatch: " + one);
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
