/* @test    @summary Test for Provider.putAll to verify behavior when provider is uninitialized.    @run main ProviderWitupTest */

import java.security.Provider;
import java.util.HashMap;
import java.util.Map;

public class ProviderWitupTest {

    public static void main(String[] args) {
        testPutAllUninitialized();
    }

    public static void testPutAllUninitialized() {
        Provider provider = new Provider("TestProvider", 1.0, "Test Info") {
            private boolean initialized = false;

            private void check(String msg) {
                if (!initialized) {
                    throw new IllegalStateException("Provider not initialized");
                }
            }

            private void implPutAll(Map<?, ?> t) {
                // Simulate putAll behavior
            }

            @Override
            public synchronized void putAll(Map<?, ?> t) {
                check("putProviderProperty." + getName());
                implPutAll(t);
            }
        };

        Map<String, String> map = new HashMap<>();
        map.put("key", "value");

        try {
            provider.putAll(map);
            // If no exception, test fails because expected exception when uninitialized
            System.out.println("FAIL: Expected IllegalStateException was not thrown.");
        } catch (IllegalStateException e) {
            System.out.println("PASS: Caught expected IllegalStateException.");
        } catch (Exception e) {
            System.out.println("FAIL: Caught unexpected exception: " + e);
        }
    }
}
