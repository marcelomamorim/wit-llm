/*
 * @test
 * @summary Verifies Attrs.isDeclared throws IllegalArgumentException when the qName is not present in an empty attribute list
 * @modules java.base/jdk.internal.util.xml.impl
 * @run main AttrsWitupTest
 */

public class AttrsWitupTest {

    public static void main(String[] args) {
        testEmptyAttrsThrowsForAnyName();
        testEmptyAttrsThrowsForEmptyName();
        testSetLengthZeroStillThrows();
    }

    private static void testEmptyAttrsThrowsForAnyName() {
        jdk.internal.util.xml.impl.Attrs attrs = new jdk.internal.util.xml.impl.Attrs();
        expectIllegalArgument(attrs, "missing");
    }

    private static void testEmptyAttrsThrowsForEmptyName() {
        jdk.internal.util.xml.impl.Attrs attrs = new jdk.internal.util.xml.impl.Attrs();
        expectIllegalArgument(attrs, "");
    }

    private static void testSetLengthZeroStillThrows() {
        jdk.internal.util.xml.impl.Attrs attrs = new jdk.internal.util.xml.impl.Attrs();
        attrs.setLength((char) 0);
        if (attrs.getLength() != 0) {
            throw new RuntimeException("Expected length 0, got " + attrs.getLength());
        }
        expectIllegalArgument(attrs, "attr");
    }

    private static void expectIllegalArgument(jdk.internal.util.xml.impl.Attrs attrs, String name) {
        try {
            boolean result = attrs.isDeclared(name);
            throw new RuntimeException("Expected IllegalArgumentException for qName '"
                    + name + "' but got result " + result);
        } catch (IllegalArgumentException expected) {
            // expected behavior
        }
    }
}
