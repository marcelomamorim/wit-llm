/*
 * @test
 * @summary Verifies that Attrs.isDeclared throws IllegalArgumentException when no attribute can be found, including after setLength(0).
 * @modules java.base/jdk.internal.util.xml.impl java.base/jdk.internal.org.xml.sax
 * @run main AttrsWitupTest
 */

import jdk.internal.util.xml.impl.Attrs;

public class AttrsWitupTest {

    public static void main(String[] args) {
        testEmptyAttrsThrows();
        testZeroLengthThrows();
        testRepeatedCallsThrow();
    }

    private static void testEmptyAttrsThrows() {
        Attrs attrs = new Attrs();
        expectIllegalArgument(attrs, "missing");
    }

    private static void testZeroLengthThrows() {
        Attrs attrs = new Attrs();
        attrs.setLength((char) 0);
        expectIllegalArgument(attrs, "missing");
    }

    private static void testRepeatedCallsThrow() {
        Attrs attrs = new Attrs();
        expectIllegalArgument(attrs, "a");
        expectIllegalArgument(attrs, "b");
    }

    private static void expectIllegalArgument(Attrs attrs, String qName) {
        try {
            attrs.isDeclared(qName);
        } catch (IllegalArgumentException expected) {
            return;
        }
        throw new RuntimeException("Expected IllegalArgumentException for qName: " + qName);
    }
}
