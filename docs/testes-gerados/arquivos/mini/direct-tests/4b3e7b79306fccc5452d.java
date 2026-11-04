/*
 * @test
 * @summary Test constructor of ModuleHashes for correct field assignment and immutability
 * @run main ModuleHashesWitupTest
 */

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ModuleHashesWitupTest {
    public static void main(String[] args) {
        // Prepare test data
        String algorithm = "SHA-256";
        Map<String, byte[]> map = new HashMap<>();
        byte[] hash1 = new byte[] {1, 2, 3};
        byte[] hash2 = new byte[] {4, 5, 6};
        map.put("moduleA", hash1);
        map.put("moduleB", hash2);

        // Construct ModuleHashes
        jdk.internal.module.ModuleHashes mh = new jdk.internal.module.ModuleHashes(algorithm, map);

        // Check algorithm is returned correctly
        if (!algorithm.equals(mh.algorithm())) {
            throw new AssertionError("Algorithm mismatch: expected " + algorithm + " but got " + mh.algorithm());
        }

        // Check names() returns the keys
        Set<String> names = mh.names();
        if (!names.contains("moduleA") || !names.contains("moduleB") || names.size() != 2) {
            throw new AssertionError("Names set incorrect: " + names);
        }

        // Check hashFor returns correct byte arrays
        byte[] h1 = mh.hashFor("moduleA");
        byte[] h2 = mh.hashFor("moduleB");
        if (h1 == null || h1.length != 3 || h1[0] != 1 || h1[1] != 2 || h1[2] != 3) {
            throw new AssertionError("hashFor moduleA incorrect");
        }
        if (h2 == null || h2.length != 3 || h2[0] != 4 || h2[1] != 5 || h2[2] != 6) {
            throw new AssertionError("hashFor moduleB incorrect");
        }

        // Check hashFor returns null for unknown module
        if (mh.hashFor("unknown") != null) {
            throw new AssertionError("hashFor unknown module should be null");
        }

        // Check hashes() returns unmodifiable map with correct entries
        Map<String, byte[]> hashes = mh.hashes();
        if (hashes.size() != 2 || !hashes.containsKey("moduleA") || !hashes.containsKey("moduleB")) {
            throw new AssertionError("hashes map incorrect");
        }
        try {
            hashes.put("moduleC", new byte[] {7});
            throw new AssertionError("hashes map should be unmodifiable");
        } catch (UnsupportedOperationException expected) {
            // expected
        }

        // Check that modifying original map after construction does not affect ModuleHashes
        map.put("moduleC", new byte[] {7});
        if (mh.names().contains("moduleC")) {
            throw new AssertionError("ModuleHashes names changed after original map modified");
        }

        System.out.println("PASS");
    }
}
