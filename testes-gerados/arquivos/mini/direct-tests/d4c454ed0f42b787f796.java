/*
 * @test
 * @summary Test java.util.Properties.load(InputStream) method behavior
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
        testLoadEmptyInputStream();
        testLoadSimpleKeyValue();
    }

    private static void testLoadNullInputStream() {
        Properties props = new Properties();
        try {
            props.load(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException npe) {
            if (!"inStream parameter is null".equals(npe.getMessage())) {
                throw new RuntimeException("NullPointerException message mismatch: " + npe.getMessage());
            }
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception type", e);
        }
    }

    private static void testLoadEmptyInputStream() throws IOException {
        Properties props = new Properties();
        byte[] emptyData = new byte[0];
        InputStream emptyStream = new ByteArrayInputStream(emptyData);
        props.load(emptyStream);
        // After loading empty stream, properties should be empty
        if (!props.isEmpty()) {
            throw new RuntimeException("Properties should be empty after loading empty stream");
        }
    }

    private static void testLoadSimpleKeyValue() throws IOException {
        Properties props = new Properties();
        String data = "key=value\n";
        InputStream stream = new ByteArrayInputStream(data.getBytes("ISO-8859-1"));
        props.load(stream);
        String val = props.getProperty("key");
        if (!"value".equals(val)) {
            throw new RuntimeException("Expected property 'key' to have value 'value', but got: " + val);
        }
    }
}
