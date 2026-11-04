/*
 * @test
 * @summary Tests the parse(InputSource) method of ParserSAX for null input handling.
 * @run main ParserSAXWitupTest
 */

import java.io.IOException;
import jdk.internal.util.xml.impl.ParserSAX;
import jdk.internal.org.xml.sax.SAXException;
import jdk.internal.org.xml.sax.InputSource;

public class ParserSAXWitupTest {

    public static void main(String[] args) {
        testParseWithNullInput();
    }

    public static void testParseWithNullInput() {
        ParserSAX parser = new ParserSAX();
        try {
            parser.parse(null);
            System.out.println("FAIL: Expected IllegalArgumentException was not thrown.");
        } catch (IllegalArgumentException e) {
            System.out.println("PASS: Caught expected IllegalArgumentException.");
        } catch (Exception e) {
            System.out.println("FAIL: Caught unexpected exception: " + e);
        }
    }
}
