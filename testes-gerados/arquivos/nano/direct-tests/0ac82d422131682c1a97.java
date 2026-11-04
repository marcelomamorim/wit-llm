/* @test    @summary Test PropertyResourceBundle constructor with Reader input    @run main PropertyResourceBundleWitupTest */

import java.io.StringReader;
import java.io.IOException;
import java.util.PropertyResourceBundle;

public class PropertyResourceBundleWitupTest {

    public static void main(String[] args) throws IOException {
        testValidReaderInput();
        testEmptyProperties();
        testInvalidPropertiesFormat();
    }

    static void testValidReaderInput() {
        String propertiesContent = "key1=value1\nkey2=value2";
        try (StringReader reader = new StringReader(propertiesContent)) {
            PropertyResourceBundle bundle = new PropertyResourceBundle(reader);
            assert "value1".equals(bundle.getString("key1")) : "Expected key1 to be 'value1'";
            assert "value2".equals(bundle.getString("key2")) : "Expected key2 to be 'value2'";
        } catch (IOException e) {
            throw new RuntimeException("Unexpected IOException in testValidReaderInput", e);
        }
    }

    static void testEmptyProperties() {
        String emptyProperties = "";
        try (StringReader reader = new StringReader(emptyProperties)) {
            PropertyResourceBundle bundle = new PropertyResourceBundle(reader);
            assert bundle.getKeys().hasMoreElements() == false : "Expected no keys in empty bundle";
        } catch (IOException e) {
            throw new RuntimeException("Unexpected IOException in testEmptyProperties", e);
        }
    }

    static void testInvalidPropertiesFormat() {
        String invalidProperties = "keyWithoutEqualsSign";
        try (StringReader reader = new StringReader(invalidProperties)) {
            new PropertyResourceBundle(reader);
            assert false : "Expected IOException due to invalid properties format";
        } catch (IOException e) {
            // Expected exception due to invalid format
        }
    }
}
