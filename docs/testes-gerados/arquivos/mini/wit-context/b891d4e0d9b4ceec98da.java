/*
 * @test
 * @summary Test java.security.Provider.load(InputStream) behavior and exception
 * @run main ProviderWitupTest
 */

import java.security.Provider;
import java.io.*;

public class ProviderWitupTest {
    public static void main(String[] args) throws Exception {
        testLoadThrowsWhenNotInitialized();
        testLoadLoadsProperties();
    }

    static void testLoadThrowsWhenNotInitialized() {
        // Create a minimal subclass to instantiate Provider (abstract class)
        Provider p = new Provider("TestProvider", 1.0, "Test Info") {
            private static final long serialVersionUID = 1L;
        };

        // The load method calls check("putProviderProperty."+name) which calls checkInitialized internally
        // According to WIT, if not initialized, load throws IllegalStateException
        // The default Provider constructor sets initialized to false, so load should throw

        ByteArrayInputStream dummyStream = new ByteArrayInputStream(new byte[0]);

        boolean threw = false;
        try {
            p.load(dummyStream);
        } catch (IllegalStateException ise) {
            threw = true;
        } catch (IOException ioe) {
            // IOException is declared but not expected here with empty stream
            throw new RuntimeException("Unexpected IOException", ioe);
        }

        if (!threw) {
            throw new RuntimeException("Expected IllegalStateException when calling load on uninitialized Provider");
        }
    }

    static void testLoadLoadsProperties() throws Exception {
        // Create a subclass and initialize it by calling check to avoid IllegalStateException
        Provider p = new Provider("TestProvider", 1.0, "Test Info") {
            private static final long serialVersionUID = 1L;

            // Override check to simulate initialized state
            @Override
            protected void check(String key) {
                // do nothing to simulate initialized
            }

            // Override implPutAll to verify properties loaded
            boolean implPutAllCalled = false;
            Properties lastProps = null;

            @Override
            protected void implPutAll(Properties props) {
                implPutAllCalled = true;
                lastProps = props;
            }
        };

        // Prepare properties input stream
        String propsString = "key1=value1\nkey2=value2\n";
        ByteArrayInputStream bais = new ByteArrayInputStream(propsString.getBytes("ISO-8859-1"));

        p.load(bais);

        // Check that implPutAll was called and properties contain expected keys
        try {
            java.lang.reflect.Field fCalled = p.getClass().getDeclaredField("implPutAllCalled");
            fCalled.setAccessible(true);
            boolean called = fCalled.getBoolean(p);
            if (!called) {
                throw new RuntimeException("implPutAll was not called by load");
            }

            java.lang.reflect.Field fProps = p.getClass().getDeclaredField("lastProps");
            fProps.setAccessible(true);
            Properties props = (Properties) fProps.get(p);
            if (!"value1".equals(props.getProperty("key1")) || !"value2".equals(props.getProperty("key2"))) {
                throw new RuntimeException("Properties loaded incorrectly");
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            // Reflection used only because implPutAll is protected and no public getter
            throw new RuntimeException("Reflection failure", e);
        }
    }
}
