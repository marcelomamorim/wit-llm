/*
 * @test
 * @summary Test constructor of ModuleHashes for null and valid inputs
 * @run main ModuleHashesWitupTest
 */

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ModuleHashesWitupTest {

    public static void main(String[] args) {
        testConstructorWithValidInputs();
        testConstructorWithNullMap();
    }

    private static void testConstructorWithValidInputs() {
        Map<String, byte[]> map = new HashMap<>();
        map.put("mod1", new byte[] {1, 2, 3});
        map.put("mod2", new byte[] {4, 5, 6});

        ModuleHashes mh = new ModuleHashes("SHA-256", map);

        // Check algorithm is stored correctly
        if (!"SHA-256".equals(mh.algorithm())) {
            throw new AssertionError("Algorithm mismatch");
        }

        // Check names contains keys
        if (!mh.names().contains("mod1") || !mh.names().contains("mod2")) {
            throw new AssertionError("Names missing keys");
        }

        // Check hashFor returns correct arrays
        byte[] h1 = mh.hashFor("mod1");
        if (h1 == null || h1.length != 3 || h1[0] != 1 || h1[1] != 2 || h1[2] != 3) {
            throw new AssertionError("hashFor mod1 incorrect");
        }

        byte[] h2 = mh.hashFor("mod2");
        if (h2 == null || h2.length != 3 || h2[0] != 4 || h2[1] != 5 || h2[2] != 6) {
            throw new AssertionError("hashFor mod2 incorrect");
        }

        // Check hashes map is unmodifiable
        try {
            mh.hashes().put("mod3", new byte[] {7});
            throw new AssertionError("hashes map should be unmodifiable");
        } catch (UnsupportedOperationException expected) {
            // expected
        }
    }

    private static void testConstructorWithNullMap() {
        try {
            new ModuleHashes("SHA-256", null);
            throw new AssertionError("Expected NullPointerException for null map");
        } catch (NullPointerException expected) {
            // expected
        }
    }
}

final class ModuleHashes {
    private final String algorithm;
    private final Map<String, byte[]> nameToHash;

    public ModuleHashes(String algorithm, Map<String, byte[]> nameToHash) {
        this.algorithm = algorithm;
        this.nameToHash = Collections.unmodifiableMap(nameToHash);
    }

    public String algorithm() {
        return algorithm;
    }

    public java.util.Set<String> names() {
        return nameToHash.keySet();
    }

    public byte[] hashFor(String mn) {
        return nameToHash.get(mn);
    }

    public Map<String, byte[]> hashes() {
        return nameToHash;
    }
}
