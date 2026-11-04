/*
 * @test
 * @summary Test SAXParser.isXIncludeAware() throws UnsupportedOperationException with correct message
 * @run main SAXParserWitupTest
 */

import jdk.internal.util.xml.SAXParser;

public class SAXParserWitupTest {

    static class MySAXParser extends SAXParser {
        protected MySAXParser() {
            super();
        }
    }

    public static void main(String[] args) {
        SAXParser parser = new MySAXParser();
        try {
            parser.isXIncludeAware();
            throw new RuntimeException("Expected UnsupportedOperationException not thrown");
        } catch (UnsupportedOperationException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.contains("This parser does not support specification")) {
                throw new RuntimeException("Exception message unexpected: " + msg);
            }
            // Test passed
        }
    }
}
