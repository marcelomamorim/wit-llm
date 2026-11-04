/*
 * @test
 * @summary Verify that SAXParser.isXIncludeAware() throws UnsupportedOperationException
 * @modules java.base/jdk.internal.util.xml
 * @run main SAXParserWitupTest
 */

import jdk.internal.util.xml.SAXParser;

public class SAXParserWitupTest {

    static class TestParser extends SAXParser {
    }

    public static void main(String[] args) throws Exception {
        SAXParser parser = new TestParser();
        boolean thrown = false;
        try {
            parser.isXIncludeAware();
        } catch (UnsupportedOperationException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected UnsupportedOperationException from isXIncludeAware()");
        }
    }
}
