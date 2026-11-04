/*
 * @test
 * @summary Test for jdk.internal.util.xml.SAXParser.isXIncludeAware()
 * @run main SAXParserWitupTest
 */

import java.io.IOException;
import jdk.internal.util.xml.SAXParser;

public class SAXParserWitupTest {

    public static void main(String[] args) {
        testIsXIncludeAwareThrows();
    }

    public static void testIsXIncludeAwareThrows() {
        SAXParser parser = new SAXParser() {
            @Override
            public boolean isXIncludeAware() {
                throw new UnsupportedOperationException("This parser does not support specification \""
                        + this.getClass().getPackage().getSpecificationTitle()
                        + "\" version \""
                        + this.getClass().getPackage().getSpecificationVersion()
                        + "\"");
            }
        };
        try {
            parser.isXIncludeAware();
            // If no exception, test fails
            System.err.println("Expected UnsupportedOperationException was not thrown.");
        } catch (UnsupportedOperationException e) {
            // Expected exception, test passes
            System.out.println("Caught expected UnsupportedOperationException.");
        } catch (Exception e) {
            System.err.println("Unexpected exception: " + e);
        }
    }
}
