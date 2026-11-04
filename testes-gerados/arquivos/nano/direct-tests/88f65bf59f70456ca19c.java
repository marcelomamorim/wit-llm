/* @test    @summary Test the keySet() method of java.security.Provider for correct unmodifiable set return.    @run main ProviderWitupTest */

import java.security.Provider;
import java.util.Set;

public class ProviderWitupTest {

    public static void main(String[] args) {
        testKeySetReturnsUnmodifiableSet();
    }

    public static void testKeySetReturnsUnmodifiableSet() {
        Provider provider = new Provider("TestProvider", 1.0, "Test Info") {
            @Override
            public Set<Object> keySet() {
                return super.keySet();
            }
        };
        // Initialize provider to ensure checkInitialized() passes
        try {
            provider.keySet();
        } catch (Exception e) {
            // ignore, just to trigger checkInitialized()
        }
        Set<Object> keys = provider.keySet();
        // Verify that the returned set is unmodifiable
        boolean isUnmodifiable = false;
        try {
            keys.add("newKey");
        } catch (UnsupportedOperationException e) {
            isUnmodifiable = true;
        }
        if (!isUnmodifiable) {
            throw new AssertionError("keySet() did not return an unmodifiable set");
        }
        // Verify that the set contains expected elements (empty or not depends on implementation)
        // Since super.keySet() is empty by default, we expect empty set
        if (!keys.isEmpty()) {
            throw new AssertionError("Expected empty key set, but got: " + keys);
        }
        System.out.println("testKeySetReturnsUnmodifiableSet passed.");
    }
}
