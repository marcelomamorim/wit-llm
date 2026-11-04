/*
 * @test
 * @summary Test java.security.Provider.putAll(Map) method behavior
 * @run main ProviderWitupTest
 */

import java.security.Provider;
import java.util.HashMap;
import java.util.Map;

public class ProviderWitupTest extends Provider {

    private Map<Object, Object> internalMap = new HashMap<>();
    private boolean checkCalled = false;
    private String lastCheckArg = null;

    protected ProviderWitupTest() {
        super("TestProvider", 1.0, "Test Provider for putAll");
    }

    // Override check to record invocation and argument
    @Override
    protected void check(String permission) {
        checkCalled = true;
        lastCheckArg = permission;
    }

    // Override implPutAll to record the map contents
    @Override
    protected void implPutAll(Map<?, ?> t) {
        internalMap.putAll(t);
    }

    public static void main(String[] args) {
        ProviderWitupTest p = new ProviderWitupTest();

        // Test 1: putAll with empty map
        Map<String, String> emptyMap = new HashMap<>();
        p.putAll(emptyMap);
        if (!p.checkCalled) {
            throw new RuntimeException("check() was not called");
        }
        if (!"putProviderProperty.TestProvider".equals(p.lastCheckArg)) {
            throw new RuntimeException("check() called with wrong argument: " + p.lastCheckArg);
        }
        if (!p.internalMap.isEmpty()) {
            throw new RuntimeException("implPutAll should not add entries for empty map");
        }

        // Test 2: putAll with some entries
        Map<String, String> map = new HashMap<>();
        map.put("key1", "value1");
        map.put("key2", "value2");
        p.putAll(map);

        if (!p.internalMap.containsKey("key1") || !"value1".equals(p.internalMap.get("key1"))) {
            throw new RuntimeException("implPutAll did not add key1 correctly");
        }
        if (!p.internalMap.containsKey("key2") || !"value2".equals(p.internalMap.get("key2"))) {
            throw new RuntimeException("implPutAll did not add key2 correctly");
        }

        // Test 3: putAll with null map reference should throw NullPointerException
        boolean npeThrown = false;
        try {
            p.putAll(null);
        } catch (NullPointerException e) {
            npeThrown = true;
        }
        if (!npeThrown) {
            throw new RuntimeException("putAll(null) did not throw NullPointerException");
        }

        System.out.println("All tests passed.");
    }
}
