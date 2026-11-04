/*
 * @test
 * @summary Test java.util.Properties.load(Reader) for null argument and normal usage
 * @run main java.util.PropertiesWitupTest
 */

import java.util.Properties;
import java.io.Reader;
import java.io.StringReader;
import java.io.IOException;
import java.util.Objects;

public class PropertiesWitupTest {
    public static void main(String[] args) throws IOException {
        testLoadNullReader();
        testLoadValidReader();
    }

    private static void testLoadNullReader() {
        Properties props = new Properties();
        try {
            props.load(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException npe) {
            // Expected exception
            if (!"reader parameter is null".equals(npe.getMessage())) {
                throw new RuntimeException("Unexpected NullPointerException message: " + npe.getMessage());
            }
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception type: " + e);
        }
    }

    private static void testLoadValidReader() throws IOException {
        Properties props = new Properties();
        StringReader sr = new StringReader("key=value\nanotherKey=anotherValue");
        props.load(sr);
        if (!"value".equals(props.getProperty("key"))) {
            throw new RuntimeException("Property 'key' was not loaded correctly");
        }
        if (!"anotherValue".equals(props.getProperty("anotherKey"))) {
            throw new RuntimeException("Property 'anotherKey' was not loaded correctly");
        }
    }
}
