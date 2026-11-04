/* @test    @summary Test Properties.load(InputStream) method for correct behavior and exception handling    @run main PropertiesWitupTest */

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesWitupTest {

    public static void main(String[] args) {
        testLoadValidInput();
        testLoadNullInput();
        testLoadEmptyInput();
        testLoadMalformedInput();
    }

    static void testLoadValidInput() {
        Properties props = new Properties();
        String data = "key1=value1\nkey2=value2";
        InputStream input = new ByteArrayInputStream(data.getBytes());
        try {
            props.load(input);
            assert "value1".equals(props.getProperty("key1")) : "key1 should be value1";
            assert "value2".equals(props.getProperty("key2")) : "key2 should be value2";
        } catch (IOException e) {
            throw new AssertionError("Unexpected IOException in valid input test", e);
        }
    }

    static void testLoadNullInput() {
        Properties props = new Properties();
        try {
            props.load(null);
            throw new AssertionError("Expected NullPointerException for null input");
        } catch (NullPointerException e) {
            // expected
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type", e);
        }
    }

    static void testLoadEmptyInput() {
        Properties props = new Properties();
        InputStream input = new ByteArrayInputStream(new byte[0]);
        try {
            props.load(input);
            assert props.isEmpty() : "Properties should be empty after loading empty input";
        } catch (IOException e) {
            throw new AssertionError("Unexpected IOException in empty input test", e);
        }
    }

    static void testLoadMalformedInput() {
        Properties props = new Properties();
        String malformedData = "key1=value1\nkey2"; // missing '=' in second line
        InputStream input = new ByteArrayInputStream(malformedData.getBytes());
        try {
            props.load(input);
            // In Java Properties, malformed lines are ignored or treated as keys with no value
            assert "value1".equals(props.getProperty("key1")) : "key1 should be value1";
            assert props.getProperty("key2") == null : "key2 should be null due to malformed line";
        } catch (IOException e) {
            throw new AssertionError("Unexpected IOException in malformed input test", e);
        }
    }
}
