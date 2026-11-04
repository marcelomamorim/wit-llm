/*
 * @test
 * @summary Witup-generated behavioral tests for ModuleHashes constructor and accessors
 * @modules java.base/jdk.internal.module
 * @run main ModuleHashesWitupTest
 */

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import jdk.internal.module.ModuleHashes;

public class ModuleHashesWitupTest {

    public static void main(String[] args) {
        testNullMapThrowsNullPointerException();
        testAlgorithmAndHashForReturnStoredValues();
        testHashForMissingModuleReturnsNull();
        testNamesAndHashesAreUnmodifiable();
        testMapIsBackedByProvidedMap();
    }

    static void fail(String msg) {
        throw new RuntimeException(msg);
    }

    static void testNullMapThrowsNullPointerException() {
        try {
            new ModuleHashes("SHA-256", null);
            fail("expected NullPointerException for null nameToHash");
        } catch (NullPointerException expected) {
        }
    }

    static void testAlgorithmAndHashForReturnStoredValues() {
        Map<String, byte[]> map = new HashMap<>();
        byte[] h = new byte[] {1, 2, 3};
        map.put("java.base", h);
        ModuleHashes mh = new ModuleHashes("SHA-256", map);
        if (!"SHA-256".equals(mh.algorithm())) {
            fail("algorithm() mismatch: " + mh.algorithm());
        }
        if (mh.hashFor("java.base") != h) {
            fail("hashFor() did not return stored array");
        }
        if (mh.hashes().get("java.base") != h) {
            fail("hashes() did not expose stored hash");
        }
    }

    static void testHashForMissingModuleReturnsNull() {
        ModuleHashes mh = new ModuleHashes("SHA-256", new HashMap<String, byte[]>());
        if (mh.hashFor("no.such.module") != null) {
            fail("hashFor() of missing module should be null");
        }
        Set<String> names = mh.names();
        if (!names.isEmpty()) {
            fail("names() should be empty");
        }
    }

    static void testNamesAndHashesAreUnmodifiable() {
        Map<String, byte[]> map = new HashMap<>();
        map.put("m", new byte[] {9});
        ModuleHashes mh = new ModuleHashes("SHA-256", map);
        try {
            mh.hashes().put("x", new byte[] {1});
            fail("hashes() should be unmodifiable");
        } catch (UnsupportedOperationException expected) {
        }
        try {
            mh.names().remove("m");
            fail("names() should be unmodifiable");
        } catch (UnsupportedOperationException expected) {
        }
    }

    static void testMapIsBackedByProvidedMap() {
        Map<String, byte[]> map = new HashMap<>();
        ModuleHashes mh = new ModuleHashes("SHA-256", map);
        map.put("late", new byte[] {7});
        if (mh.hashFor("late") == null) {
            fail("unmodifiable view should reflect later changes to the source map");
        }
    }
}
