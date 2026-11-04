/*
 * @test
 * @summary Verifies that AbstractMap.put throws UnsupportedOperationException by default
 * @run main AbstractMapWitupTest
 */

import java.util.AbstractMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class AbstractMapWitupTest {

    static class EmptyMap extends AbstractMap<String, String> {
        @Override
        public Set<Map.Entry<String, String>> entrySet() {
            return new HashSet<Map.Entry<String, String>>();
        }
    }

    public static void main(String[] args) {
        EmptyMap map = new EmptyMap();
        checkPutThrows(map, "k", "v");
        checkPutThrows(map, null, null);
        checkPutThrows(map, "k", null);
        if (!map.isEmpty()) {
            throw new RuntimeException("map state changed after rejected put");
        }
    }

    static void checkPutThrows(Map<String, String> map, String key, String value) {
        boolean thrown = false;
        try {
            map.put(key, value);
        } catch (UnsupportedOperationException expected) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("put did not throw UnsupportedOperationException for key=" + key);
        }
    }
}
