/*
 * @test
 * @summary Test java.util.Properties.load(Reader) method behavior
 * @run main java.util.PropertiesWitupTest
 */

import java.util.Properties;
import java.io.Reader;
import java.io.StringReader;
import java.io.IOException;
import java.util.Objects;

public class PropertiesWitupTest {
    public static void main(String[] args) throws Exception {
        testLoadNullReader();
        testLoadEmptyReader();
        testLoadSimpleKeyValue();
    }

    private static void testLoadNullReader() {
        Properties props = new Properties();
        try {
            props.load(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException npe) {
            if (!"reader parameter is null".equals(npe.getMessage())) {
                throw new RuntimeException("NullPointerException message mismatch", npe);
            }
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception", e);
        }
    }

    private static void testLoadEmptyReader() throws IOException {
        Properties props = new Properties();
        Reader r = new StringReader("");
        props.load(r);
        // No properties should be loaded
        if (!props.isEmpty()) {
            throw new RuntimeException("Properties should be empty after loading empty input");
        }
    }

    private static void testLoadSimpleKeyValue() throws IOException {
        Properties props = new Properties();
        String input = "key=value\n";
        Reader r = new StringReader(input);
        props.load(r);
        String v = props.getProperty("key");
        if (!"value".equals(v)) {
            throw new RuntimeException("Property value mismatch: expected 'value' but got '" + v + "'");
        }
    }
}
