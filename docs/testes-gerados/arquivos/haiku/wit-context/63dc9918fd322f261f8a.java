/*
 * @test
 * @summary Verifies that PropertiesDefaultHandler.warning(SAXParseException) rethrows its argument, which yields NullPointerException when the argument is null
 * @modules java.base/jdk.internal.util.xml
 * @run main PropertiesDefaultHandlerWitupTest
 */

import jdk.internal.util.xml.PropertiesDefaultHandler;

public class PropertiesDefaultHandlerWitupTest {

    public static void main(String[] args) throws Exception {
        PropertiesDefaultHandler handler = new PropertiesDefaultHandler();
        boolean thrown = false;
        try {
            handler.warning(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("warning(null) should throw NullPointerException");
        }
    }
}
