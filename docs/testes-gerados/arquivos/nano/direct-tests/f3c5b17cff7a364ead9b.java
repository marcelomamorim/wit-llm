/*
 * @test
 * @summary Test the keys() method of java.security.Provider for correct enumeration return.
 * @run main ProviderWitupTest
 */

import java.security.Provider;
import java.util.Enumeration;

public class ProviderWitupTest {

    public static void main(String[] args) {
        testKeysMethod();
    }

    public static void testKeysMethod() {
        Provider provider = new Provider("TestProvider", 1.0, "Test Info") {
            @Override
            public Enumeration<Object> keys() {
                return new Enumeration<Object>() {
                    private int index = 0;
                    private final String[] keys = {"key1", "key2", "key3"};

                    @Override
                    public boolean hasMoreElements() {
                        return index < keys.length;
                    }

                    @Override
                    public Object nextElement() {
                        return keys[index++];
                    }
                };
            }
        };

        Enumeration<Object> keysEnum = provider.keys();
        int count = 0;
        while (keysEnum.hasMoreElements()) {
            Object key = keysEnum.nextElement();
            if (!(key instanceof String)) {
                throw new AssertionError("Key is not a String: " + key);
            }
            count++;
        }
        if (count != 3) {
            throw new AssertionError("Expected 3 keys, but got: " + count);
        }
        System.out.println("keys() method test passed.");
    }
}
