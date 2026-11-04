/*
 * @test
 * @summary Test java.util.Properties.load(InputStream) for null input and normal usage
 * @run main java.util.PropertiesWitupTest
 */

import java.util.Properties;
import java.io.InputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Objects;

public class PropertiesWitupTest {
    public static void main(String[] args) throws Exception {
        testLoadNullInputStream();
        testLoadValidInputStream();
    }

    private static void testLoadNullInputStream() {
        Properties props = new Properties();
        try {
            props.load(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException npe) {
            // expected
            if (!"inStream parameter is null".equals(npe.getMessage())) {
                throw new RuntimeException("Unexpected NullPointerException message: " + npe.getMessage());
            }
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception type", e);
        }
    }

    private static void testLoadValidInputStream() throws IOException {
        Properties props = new Properties();
        String propString = "key1=value1\nkey2=value2\n";
        InputStream in = new ByteArrayInputStream(propString.getBytes("ISO-8859-1"));
        props.load(in);
        if (!"value1".equals(props.getProperty("key1"))) {
            throw new RuntimeException("Property key1 not loaded correctly");
        }
        if (!"value2".equals(props.getProperty("key2"))) {
            throw new RuntimeException("Property key2 not loaded correctly");
        }
    }
}
