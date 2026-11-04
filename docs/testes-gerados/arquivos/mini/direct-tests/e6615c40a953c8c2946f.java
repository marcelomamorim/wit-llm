/*
 * @test
 * @summary Test ParserSAX.setProperty throws UnsupportedOperationException
 * @run main ParserSAXWitupTest
 */

public class ParserSAXWitupTest {
    public static void main(String[] args) {
        jdk.internal.util.xml.impl.ParserSAX parser = new jdk.internal.util.xml.impl.ParserSAX();
        try {
            parser.setProperty("anyName", new Object());
            throw new Error("Expected UnsupportedOperationException not thrown");
        } catch (UnsupportedOperationException e) {
            // expected
        }
    }
}
