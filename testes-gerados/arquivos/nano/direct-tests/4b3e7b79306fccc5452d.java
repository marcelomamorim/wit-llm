/* @test    @summary Test constructor of ModuleHashes with valid parameters    @run main ModuleHashesWitupTest */

import java.util.HashMap;
import java.util.Map;
import java.util.Collections;

public class ModuleHashesWitupTest {

    public static void main(String[] args) {
        testConstructorWithValidParameters();
    }

    public static void testConstructorWithValidParameters() {
        Map<String, byte[]> nameToHash = new HashMap<>();
        byte[] hashBytes = {1, 2, 3, 4};
        nameToHash.put("moduleA", hashBytes);
        String algorithm = "SHA-256";
        try {
            jdk.internal.module.ModuleHashes mh = new jdk.internal.module.ModuleHashes(algorithm, Collections.unmodifiableMap(nameToHash));
            assert mh.algorithm().equals(algorithm) : "Algorithm mismatch";
            assert mh.names().contains("moduleA") : "Module name not found";
            byte[] retrievedHash = mh.hashFor("moduleA");
            assert retrievedHash != null : "Hash for moduleA should not be null";
            assert retrievedHash.length == hashBytes.length : "Hash length mismatch";
            for (int i = 0; i < hashBytes.length; i++) {
                assert retrievedHash[i] == hashBytes[i] : "Hash byte mismatch at index " + i;
            }
            Map<String, byte[]> hashesMap = mh.hashes();
            assert hashesMap.size() == 1 : "Hashes map size should be 1";
            assert hashesMap.containsKey("moduleA") : "Hashes map should contain moduleA";
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception: " + e, e);
        }
    }
}
