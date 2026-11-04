/*
 * @test
 * @summary Test java.security.Provider.load(InputStream) method
 * @run main ProviderWitupTest
 */

import java.security.Provider;
import java.io.*;
import java.util.Properties;

public class ProviderWitupTest extends Provider {

    private static final long serialVersionUID = 1L;

    public ProviderWitupTest() {
        super("TestProvider", 1.0, "Test Provider for jtreg");
    }

    public static void main(String[] args) throws Exception {
        ProviderWitupTest provider = new ProviderWitupTest();

        // Test 1: Load valid properties from InputStream
        String props = "key1=value1\nkey2=value2";
        InputStream validStream = new ByteArrayInputStream(props.getBytes("UTF-8"));
        provider.load(validStream);
        // Check that properties were loaded
        String val1 = provider.getProperty("key1");
        String val2 = provider.getProperty("key2");
        if (!"value1".equals(val1) || !"value2".equals(val2)) {
            throw new RuntimeException("Failed to load properties correctly");
        }

        // Test 2: Load from empty InputStream (should not fail, no properties added)
        InputStream emptyStream = new ByteArrayInputStream(new byte[0]);
        provider.load(emptyStream);
        // No exception expected, properties remain unchanged
        if (!"value1".equals(provider.getProperty("key1"))) {
            throw new RuntimeException("Properties changed unexpectedly after empty load");
        }

        // Test 3: Load from malformed properties InputStream (should throw IOException)
        String badProps = "badpropertywithoutseparator";
        InputStream badStream = new ByteArrayInputStream(badProps.getBytes("UTF-8"));
        boolean threw = false;
        try {
            provider.load(badStream);
        } catch (IOException e) {
            threw = true;
        }
        if (!threw) {
            throw new RuntimeException("Expected IOException not thrown for malformed properties");
        }

        System.out.println("All tests passed.");
    }

    // Override check to allow loading without security exception
    protected void check(String permission) {
        // no-op for testing
    }

    // Override implPutAll to put properties into this Provider's properties
    protected void implPutAll(Properties props) {
        for (String key : props.stringPropertyNames()) {
            put(key, props.getProperty(key));
        }
    }
}