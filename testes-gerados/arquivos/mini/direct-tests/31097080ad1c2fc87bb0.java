/*
 * @test
 * @summary Test ParserSAX.getFeature throws UnsupportedOperationException
 * @run main ParserSAXWitupTest
 */

import jdk.internal.util.xml.impl.ParserSAX;

public class ParserSAXWitupTest {
    public static void main(String[] args) {
        ParserSAX parser = new ParserSAX();
        try {
            parser.getFeature("anyFeature");
            throw new RuntimeException("Expected UnsupportedOperationException not thrown");
        } catch (UnsupportedOperationException e) {
            // expected
        }
    }
}
