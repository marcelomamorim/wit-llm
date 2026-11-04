/*
 * @test
 * @summary Verify PropertyResourceBundle.handleGetObject lookup behavior for null, present, missing and case-sensitive keys
 * @run main PropertyResourceBundleWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.util.PropertyResourceBundle;

public class PropertyResourceBundleWitupTest {

    public static void main(String[] args) throws IOException {
        testNullKeyThrowsNPE();
        testExistingKeyReturnsValue();
        testMissingKeyReturnsNull();
        testKeyIsCaseSensitive();
        testInputStreamConstructor();
    }

    static PropertyResourceBundle fromReader(String text) throws IOException {
        return new PropertyResourceBundle(new StringReader(text));
    }

    static void testNullKeyThrowsNPE() throws IOException {
        PropertyResourceBundle b = fromReader("a=1\n");
        try {
            b.handleGetObject(null);
            throw new RuntimeException("Expected NullPointerException for null key");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testExistingKeyReturnsValue() throws IOException {
        PropertyResourceBundle b = fromReader("a=1\nb=two\n");
        check("1".equals(b.handleGetObject("a")), "value for 'a' should be '1'");
        check("two".equals(b.handleGetObject("b")), "value for 'b' should be 'two'");
    }

    static void testMissingKeyReturnsNull() throws IOException {
        PropertyResourceBundle b = fromReader("a=1\n");
        check(b.handleGetObject("missing") == null, "missing key should return null");
    }

    static void testKeyIsCaseSensitive() throws IOException {
        PropertyResourceBundle b = fromReader("a=1\n");
        check(b.handleGetObject("A") == null, "lookup of 'A' should not match key 'a'");
        check("1".equals(b.handleGetObject("a")), "lookup of 'a' should still succeed");
    }

    static void testInputStreamConstructor() throws IOException {
        byte[] data = "k=v\n".getBytes(StandardCharsets.ISO_8859_1);
        PropertyResourceBundle b = new PropertyResourceBundle(new ByteArrayInputStream(data));
        check("v".equals(b.handleGetObject("k")), "value for 'k' via InputStream should be 'v'");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
