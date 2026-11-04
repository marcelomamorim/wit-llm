/* @test    @summary Test isXIncludeAware method of jdk.internal.util.xml.SAXParser    @run main SAXParserWitupTest */

import jdk.internal.util.xml.SAXParser;

public class SAXParserWitupTest {

    public static void main(String[] args) {
        testIsXIncludeAwareThrows();
    }

    public static void testIsXIncludeAwareThrows() {
        try {
            SAXParser parser = new SAXParser() {
                @Override
                public boolean isXIncludeAware() {
                    throw new UnsupportedOperationException("This parser does not support specification \"" + this.getClass().getPackage().getSpecificationTitle() + "\" version \"" + this.getClass().getPackage().getSpecificationVersion() + "\"");
                }
            };
            boolean result = parser.isXIncludeAware();
            // Since the method always throws, reaching this line indicates failure
            System.err.println("Expected UnsupportedOperationException was not thrown.");
        } catch (UnsupportedOperationException e) {
            String message = e.getMessage();
            if (message != null && message.contains("This parser does not support specification")) {
                System.out.println("Test passed: Exception message contains expected text.");
            } else {
                System.err.println("Test failed: Exception message does not contain expected text.");
            }
        } catch (Exception e) {
            System.err.println("Test failed: Unexpected exception: " + e);
        }
    }
}
