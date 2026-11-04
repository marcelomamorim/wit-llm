/*
 * @test
 * @summary Test for getFeature method in ParserSAX class
 * @run main ParserSAXWitupTest
 */

import jdk.internal.util.xml.impl.ParserSAX;

public class ParserSAXWitupTest {

    public static void main(String[] args) {
        testGetFeatureThrowsException();
    }

    public static void testGetFeatureThrowsException() {
        ParserSAX parser = new ParserSAX();
        try {
            parser.getFeature("someFeature");
            System.out.println("FAIL: Expected UnsupportedOperationException was not thrown.");
        } catch (UnsupportedOperationException e) {
            System.out.println("PASS: Caught expected UnsupportedOperationException.");
        } catch (Throwable t) {
            System.out.println("FAIL: Caught unexpected exception: " + t);
        }
    }
}
