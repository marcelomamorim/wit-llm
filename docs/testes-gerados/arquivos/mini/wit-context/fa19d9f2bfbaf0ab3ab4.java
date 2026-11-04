/*
 * @test
 * @summary Test ParserSAX.setFeature throws UnsupportedOperationException
 * @run main ParserSAXWitupTest
 */

import jdk.internal.util.xml.impl.ParserSAX;

public class ParserSAXWitupTest {
    public static void main(String[] args) {
        ParserSAX parser = new ParserSAX();
        boolean thrown = false;
        try {
            parser.setFeature("anyFeature", true);
        } catch (UnsupportedOperationException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected UnsupportedOperationException was not thrown by setFeature");
        }
    }
}
