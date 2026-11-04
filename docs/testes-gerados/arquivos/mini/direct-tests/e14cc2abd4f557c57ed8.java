/*
 * @test
 * @summary Test ExtendedKeyUsageExtension.getExtendedKeyUsage returns correct OID strings
 * @run main ExtendedKeyUsageExtensionWitupTest
 */

import sun.security.x509.ExtendedKeyUsageExtension;
import sun.security.util.ObjectIdentifier;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class ExtendedKeyUsageExtensionWitupTest {
    public static void main(String[] args) throws Exception {
        testEmptyKeyUsages();
        testSingleKeyUsage();
        testMultipleKeyUsages();
    }

    private static void testEmptyKeyUsages() {
        Vector<ObjectIdentifier> emptyVector = new Vector<>();
        ExtendedKeyUsageExtension ext = new ExtendedKeyUsageExtension(emptyVector);
        List<String> usages = ext.getExtendedKeyUsage();
        if (usages == null) {
            throw new RuntimeException("Returned list is null for empty keyUsages");
        }
        if (!usages.isEmpty()) {
            throw new RuntimeException("Expected empty list for empty keyUsages, got: " + usages);
        }
    }

    private static void testSingleKeyUsage() {
        Vector<ObjectIdentifier> v = new Vector<>();
        ObjectIdentifier oid = new ObjectIdentifier(new int[] {1, 2, 3, 4});
        v.add(oid);
        ExtendedKeyUsageExtension ext = new ExtendedKeyUsageExtension(v);
        List<String> usages = ext.getExtendedKeyUsage();
        if (usages.size() != 1) {
            throw new RuntimeException("Expected list size 1, got " + usages.size());
        }
        if (!usages.get(0).equals(oid.toString())) {
            throw new RuntimeException("Expected " + oid.toString() + " but got " + usages.get(0));
        }
    }

    private static void testMultipleKeyUsages() {
        Vector<ObjectIdentifier> v = new Vector<>();
        ObjectIdentifier oid1 = new ObjectIdentifier(new int[] {1, 2, 3});
        ObjectIdentifier oid2 = new ObjectIdentifier(new int[] {2, 5, 29, 37});
        ObjectIdentifier oid3 = new ObjectIdentifier(new int[] {1, 3, 6, 1, 5, 5, 7, 3, 1});
        v.add(oid1);
        v.add(oid2);
        v.add(oid3);
        ExtendedKeyUsageExtension ext = new ExtendedKeyUsageExtension(v);
        List<String> usages = ext.getExtendedKeyUsage();
        if (usages.size() != 3) {
            throw new RuntimeException("Expected list size 3, got " + usages.size());
        }
        if (!usages.get(0).equals(oid1.toString())) {
            throw new RuntimeException("Expected " + oid1.toString() + " but got " + usages.get(0));
        }
        if (!usages.get(1).equals(oid2.toString())) {
            throw new RuntimeException("Expected " + oid2.toString() + " but got " + usages.get(1));
        }
        if (!usages.get(2).equals(oid3.toString())) {
            throw new RuntimeException("Expected " + oid3.toString() + " but got " + usages.get(2));
        }
    }
}
