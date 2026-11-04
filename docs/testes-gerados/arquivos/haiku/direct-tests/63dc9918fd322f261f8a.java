/*
 * @test
 * @summary PropertiesDefaultHandler.warning rethrows the reported SAXParseException; a null argument produces NullPointerException from the throw statement
 * @modules java.base/jdk.internal.util.xml java.base/jdk.internal.org.xml.sax
 * @run main PropertiesDefaultHandlerWitupTest
 */

import jdk.internal.org.xml.sax.SAXParseException;
import jdk.internal.util.xml.PropertiesDefaultHandler;

public class PropertiesDefaultHandlerWitupTest {
    public static void main(String[] args) throws Exception {
        PropertiesDefaultHandler handler = new PropertiesDefaultHandler();
        try {
            handler.warning((SAXParseException) null);
        } catch (NullPointerException expected) {
            return;
        }
        throw new AssertionError();
    }
}
