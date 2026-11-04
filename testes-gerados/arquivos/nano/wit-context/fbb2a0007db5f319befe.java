/*
 * @test
 * @summary Testa o método load(Reader) de java.util.Properties para comportamento esperado.
 * @run main PropertiesWitupTest
 */

import java.util.Properties;
import java.io.StringReader;
import java.io.IOException;

public class PropertiesWitupTest {

    public static void main(String[] args) {
        testLoadWithNullReader();
        testLoadWithValidReader();
    }

    private static void testLoadWithNullReader() {
        Properties props = new Properties();
        boolean exceptionThrown = false;
        try {
            props.load(null);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected NullPointerException when loading with null reader");
        }
    }

    private static void testLoadWithValidReader() {
        Properties props = new Properties();
        String data = "key1=value1\nkey2=value2";
        try {
            props.load(new StringReader(data));
        } catch (IOException e) {
            throw new AssertionError("Unexpected IOException during load", e);
        }
        if (!"value1".equals(props.getProperty("key1")) || !"value2".equals(props.getProperty("key2"))) {
            throw new AssertionError("Properties did not load expected key-value pairs");
        }
    }
}
