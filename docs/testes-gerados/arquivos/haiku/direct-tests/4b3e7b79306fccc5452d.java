/*
 * @test
 * @summary Behavioral tests for the public constructor and accessors of jdk.internal.module.ModuleHashes
 * @modules java.base/jdk.internal.module
 * @run main ModuleHashesWitupTest
 */

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import jdk.internal.module.ModuleHashes;

public class ModuleHashesWitupTest {

    public static void main(String[] args) {
        testAlgorithmAndNames();
        testHashFor();
        testHashesUnmodifiable();
        testMapIsViewOfBacking();
        testNullMapRejected();
        testNullAlgorithmPreserved();
    }

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    static Map<String, byte[]> sampleMap() {
        Map<String, byte[]> m = new HashMap<>();
        m.put("mod.a", new byte[] {1, 2, 3});
        m.put("mod.b", new byte[] {4, 5});
        return m;
    }

    static void testAlgorithmAndNames() {
        ModuleHashes mh = new ModuleHashes("SHA-256", sampleMap());
        check("SHA-256".equals(mh.algorithm()), "algorithm() mismatch");
        Set<String> names = mh.names();
        check(names.size() == 2, "names() size expected 2 but was " + names.size());
        check(names.contains("mod.a"), "names() missing mod.a");
        check(names.contains("mod.b"), "names() missing mod.b");
    }

    static void testHashFor() {
        Map<String, byte[]> src = sampleMap();
        ModuleHashes mh = new ModuleHashes("SHA-256", src);
        check(mh.hashFor("mod.a") == src.get("mod.a"), "hashFor(mod.a) should return the recorded array");
        check(mh.hashFor("missing") == null, "hashFor(missing) should be null");
    }

    static void testHashesUnmodifiable() {
        ModuleHashes mh = new ModuleHashes("SHA-256", sampleMap());
        try {
            mh.hashes().put("mod.c", new byte[0]);
            throw new RuntimeException("hashes() map should be unmodifiable");
        } catch (UnsupportedOperationException expected) {
            // expected
        }
        check(mh.names().size() == 2, "map size changed after rejected put");
    }

    static void testMapIsViewOfBacking() {
        Map<String, byte[]> src = sampleMap();
        ModuleHashes mh = new ModuleHashes("SHA-256", src);
        src.put("mod.c", new byte[] {9});
        check(mh.names().contains("mod.c"), "unmodifiable view should reflect backing map changes");
        check(mh.hashFor("mod.c") != null, "hashFor should see entry added to backing map");
    }

    static void testNullMapRejected() {
        try {
            new ModuleHashes("SHA-256", null);
            throw new RuntimeException("null map should be rejected");
        } catch (NullPointerException expected) {
            // expected: Collections.unmodifiableMap rejects null
        }
    }

    static void testNullAlgorithmPreserved() {
        ModuleHashes mh = new ModuleHashes(null, sampleMap());
        check(mh.algorithm() == null, "null algorithm should be returned as null");
        check(mh.names().size() == 2, "names() should still be populated");
    }
}
