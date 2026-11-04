/* @test    @summary Test Properties.load(Reader) with valid and null input    @run main PropertiesWitupTest */

import java.io.StringReader;
import java.io.IOException;
import java.util.Properties;

public class PropertiesWitupTest {

    public static void main(String[] args) {
        testLoadWithValidInput();
        testLoadWithNullInput();
    }

    static void testLoadWithValidInput() {
        Properties props = new Properties();
        String data = "key1=value1\nkey2=value2\nkey3=value3";
        try (StringReader reader = new StringReader(data)) {
            props.load(reader);
            assert "value1".equals(props.getProperty("key1")) : "key1 mismatch";
            assert "value2".equals(props.getProperty("key2")) : "key2 mismatch";
            assert "value3".equals(props.getProperty("key3")) : "key3 mismatch";
        } catch (IOException e) {
            throw new RuntimeException("Unexpected IOException", e);
        }
    }

    static void testLoadWithNullInput() {
        Properties props = new Properties();
        try {
            props.load(null);
            throw new AssertionError("Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            // Expected exception
        }
    }
}
