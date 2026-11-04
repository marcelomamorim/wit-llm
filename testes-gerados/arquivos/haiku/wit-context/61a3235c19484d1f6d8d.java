/*
 * @test
 * @summary WITUP-generated checks for PropertiesDefaultHandler.fatalError and its propagation through load
 * @modules java.base/jdk.internal.util.xml
 * @modules java.base/jdk.internal.org.xml.sax
 * @run main PropertiesDefaultHandlerWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;
import jdk.internal.util.xml.PropertiesDefaultHandler;

public class PropertiesDefaultHandlerWitupTest {

    public static void main(String[] args) throws Exception {
        testFatalErrorWithNullThrowsNullPointerException();
        testLoadMalformedXmlThrowsInvalidPropertiesFormatException();
    }

    static void testFatalErrorWithNullThrowsNullPointerException() {
        PropertiesDefaultHandler handler = new PropertiesDefaultHandler();
        boolean thrown = false;
        try {
            handler.fatalError(null);
        } catch (NullPointerException expected) {
            thrown = true;
        } catch (Exception other) {
            throw new RuntimeException("Unexpected exception: " + other, other);
        }
        if (!thrown) {
            throw new RuntimeException("fatalError(null) did not throw NullPointerException");
        }
    }

    static void testLoadMalformedXmlThrowsInvalidPropertiesFormatException() throws Exception {
        PropertiesDefaultHandler handler = new PropertiesDefaultHandler();
        Properties props = new Properties();
        byte[] bytes = "<properties><comment>unterminated".getBytes(StandardCharsets.UTF_8);
        boolean thrown = false;
        try (InputStream in = new ByteArrayInputStream(bytes)) {
            handler.load(props, in);
        } catch (InvalidPropertiesFormatException expected) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Malformed XML did not throw InvalidPropertiesFormatException");
        }
        if (!props.isEmpty()) {
            throw new RuntimeException("Properties were modified despite malformed XML: " + props);
        }
    }
}
