/*
 * @test
 * @summary Test the parse method of ParserSAX for null InputSource
 * @run main ParserSAXWitupTest
 */

import java.io.IOException;
import jdk.internal.util.xml.impl.ParserSAX;
import jdk.internal.org.xml.sax.InputSource;
import jdk.internal.org.xml.sax.SAXException;

public class ParserSAXWitupTest {

    public static void main(String[] args) {
        testParseWithNullInputSource();
        testParseWithValidInputSource();
    }

    public static void testParseWithNullInputSource() {
        ParserSAX parser = new ParserSAX();
        boolean exceptionThrown = false;
        try {
            parser.parse((InputSource) null);
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        } catch (IOException | SAXException e) {
            // Not expected in this test
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected IllegalArgumentException for null InputSource");
        }
    }

    public static void testParseWithValidInputSource() {
        ParserSAX parser = new ParserSAX();
        InputSource inputSource = new InputSource();
        boolean exceptionOccurred = false;
        try {
            parser.parse(inputSource);
        } catch (IOException | SAXException e) {
            exceptionOccurred = true;
        }
        // Since the parse method does not do actual parsing, no exception is expected
        if (exceptionOccurred) {
            throw new AssertionError("Unexpected exception during parse with valid InputSource");
        }
    }
}
