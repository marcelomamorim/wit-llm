/*
 * @test
 * @summary PropertiesDefaultHandler.error rethrows the SAXParseException it receives
 * @modules java.base/jdk.internal.util.xml java.base/jdk.internal.org.xml.sax
 * @run main PropertiesDefaultHandlerWitupTest
 */

import jdk.internal.org.xml.sax.SAXParseException;
import jdk.internal.util.xml.PropertiesDefaultHandler;

public class PropertiesDefaultHandlerWitupTest {

    public static void main(String[] args) throws Exception {
        PropertiesDefaultHandler handler = new PropertiesDefaultHandler();
        SAXParseException original = new SAXParseException("boom", null, "sys", 1, 2);

        SAXParseException caught = null;
        try {
            handler.error(original);
        } catch (SAXParseException e) {
            caught = e;
        }

        if (caught == null) {
            throw new RuntimeException("error() did not throw");
        }
        if (caught != original) {
            throw new RuntimeException("error() threw a different exception instance");
        }
        if (caught.getLineNumber() != 1 || caught.getColumnNumber() != 2) {
            throw new RuntimeException("location information was altered");
        }
        if (!"boom".equals(caught.getMessage())) {
            throw new RuntimeException("message was altered");
        }
    }
}
