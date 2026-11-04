/*
 * @test
 * @summary Test SAXParser.isXIncludeAware() throws UnsupportedOperationException
 * @run main SAXParserWitupTest
 */

import jdk.internal.util.xml.SAXParser;

public class SAXParserWitupTest {

    public static void main(String[] args) {
        SAXParser parser = new SAXParser() { /* anonymous subclass to instantiate abstract class */ };
        try {
            parser.isXIncludeAware();
            throw new RuntimeException("Expected UnsupportedOperationException not thrown");
        } catch (UnsupportedOperationException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.contains("This parser does not support specification")) {
                throw new RuntimeException("Exception message unexpected: " + msg);
            }
        }
    }
}
