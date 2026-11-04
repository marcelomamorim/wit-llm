/*
 * @test
 * @summary Test getFeature method of ParserSAX class for supported features.
 * @run main ParserSAXWitupTest
 */

import jdk.internal.util.xml.impl.ParserSAX;

public class ParserSAXWitupTest {

    public static void main(String[] args) {
        testGetFeatureNamespaces();
        testGetFeaturePrefixes();
    }

    private static void testGetFeatureNamespaces() {
        ParserSAX parser = new ParserSAX();
        boolean result = false;
        try {
            result = parser.getFeature("http://xml.org/sax/features/namespaces");
        } catch (UnsupportedOperationException e) {
            // Expected exception, do nothing
        }
        System.out.println("getFeature(" + "http://xml.org/sax/features/namespaces" + ") = " + result);
    }

    private static void testGetFeaturePrefixes() {
        ParserSAX parser = new ParserSAX();
        boolean result = false;
        try {
            result = parser.getFeature("http://xml.org/sax/features/namespace-prefixes");
        } catch (UnsupportedOperationException e) {
            // Expected exception, do nothing
        }
        System.out.println("getFeature(" + "http://xml.org/sax/features/namespace-prefixes" + ") = " + result);
    }
}
