/*
 * @test
 * @summary Basic behavior of ModuleDescriptor.Version.parse: round-trip via toString, equality and ordering
 * @run main ModuleDescriptorWitupTest
 */

import java.lang.module.ModuleDescriptor;
import java.lang.module.ModuleDescriptor.Version;

public class ModuleDescriptorWitupTest {

    public static void main(String[] args) {
        testToStringRoundTrip("1.2.3");
        testToStringRoundTrip("11.0.2");
        testEqualsForSameVersion();
        testOrdering();
    }

    static void testToStringRoundTrip(String s) {
        Version v = ModuleDescriptor.Version.parse(s);
        if (!s.equals(v.toString())) {
            throw new RuntimeException("toString mismatch for " + s + ": " + v);
        }
    }

    static void testEqualsForSameVersion() {
        Version a = ModuleDescriptor.Version.parse("1.2.3");
        Version b = ModuleDescriptor.Version.parse("1.2.3");
        if (!a.equals(b)) {
            throw new RuntimeException("equal versions not equal");
        }
        if (a.compareTo(b) != 0) {
            throw new RuntimeException("compareTo of equal versions not zero");
        }
    }

    static void testOrdering() {
        Version lower = ModuleDescriptor.Version.parse("1.2.3");
        Version higher = ModuleDescriptor.Version.parse("1.2.4");
        if (lower.compareTo(higher) >= 0) {
            throw new RuntimeException("1.2.3 should be less than 1.2.4");
        }
        if (higher.compareTo(lower) <= 0) {
            throw new RuntimeException("1.2.4 should be greater than 1.2.3");
        }
    }
}
