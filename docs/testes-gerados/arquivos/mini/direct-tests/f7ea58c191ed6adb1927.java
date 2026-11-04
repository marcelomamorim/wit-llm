/*
 * @test
 * @summary Test java.security.Provider.get(Object) method behavior
 * @run main ProviderWitupTest
 */

import java.security.Provider;

public class ProviderWitupTest {
    public static void main(String[] args) throws Exception {
        // Create a minimal concrete subclass of Provider to test get method
        Provider p = new Provider("TestProvider", 1.0, "Test Provider Info") {
            private static final long serialVersionUID = 1L;
        };

        // Test get with null key - should return null (Hashtable behavior)
        Object resultNullKey = p.get(null);
        if (resultNullKey != null) {
            throw new RuntimeException("Expected null when getting with null key, got: " + resultNullKey);
        }

        // Test get with a key not present - should return null
        Object resultMissingKey = p.get("nonexistentKey");
        if (resultMissingKey != null) {
            throw new RuntimeException("Expected null when getting with missing key, got: " + resultMissingKey);
        }

        // Test get with a key after put - should return the value
        p.put("key1", "value1");
        Object resultPresentKey = p.get("key1");
        if (!"value1".equals(resultPresentKey)) {
            throw new RuntimeException("Expected 'value1' when getting with 'key1', got: " + resultPresentKey);
        }

        // Test get with a non-String key (Integer) - should behave normally
        p.put(Integer.valueOf(42), "answer");
        Object resultIntKey = p.get(Integer.valueOf(42));
        if (!"answer".equals(resultIntKey)) {
            throw new RuntimeException("Expected 'answer' when getting with Integer key 42, got: " + resultIntKey);
        }

        System.out.println("All tests passed.");
    }
}
