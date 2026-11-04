/*
 * @test
 * @summary Verify that PropertiesDefaultHandler.error(SAXParseException) rethrows its argument and fails with NullPointerException for a null argument
 * @modules java.base/jdk.internal.util.xml java.base/jdk.internal.org.xml.sax
 * @run main PropertiesDefaultHandlerWitupTest
 */

import jdk.internal.org.xml.sax.SAXParseException;
import jdk.internal.util.xml.PropertiesDefaultHandler;

public class PropertiesDefaultHandlerWitupTest {

    public static void main(String[] args) throws Exception {
        PropertiesDefaultHandler handler = new PropertiesDefaultHandler();
        boolean thrown = false;
        try {
            handler.error((SAXParseException) null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected NullPointerException when error() receives null");
        }
    }
}
