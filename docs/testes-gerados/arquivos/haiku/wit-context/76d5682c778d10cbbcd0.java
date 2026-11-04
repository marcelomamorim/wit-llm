/*
 * @test
 * @summary Witup-derived tests for Attrs.isSpecified bounds checking and default value
 * @modules java.base/jdk.internal.util.xml.impl
 * @run main AttrsWitupTest
 */

public class AttrsWitupTest {

    public static void main(String[] args) {
        testDefaultSpecifiedWithinLength();
        testNegativeIndexThrows();
        testIndexEqualToLengthThrows();
        testEmptyAttrsThrowsForZero();
    }

    private static void testDefaultSpecifiedWithinLength() {
        Attrs attrs = new Attrs();
        attrs.setLength((char) 1);
        if (!attrs.isSpecified(0)) {
            throw new RuntimeException("expected isSpecified(0) to be true when no declared flag is stored");
        }
    }

    private static void testNegativeIndexThrows() {
        Attrs attrs = new Attrs();
        attrs.setLength((char) 1);
        expectArrayIndexOutOfBounds(attrs, -1);
    }

    private static void testIndexEqualToLengthThrows() {
        Attrs attrs = new Attrs();
        attrs.setLength((char) 1);
        expectArrayIndexOutOfBounds(attrs, 1);
    }

    private static void testEmptyAttrsThrowsForZero() {
        Attrs attrs = new Attrs();
        expectArrayIndexOutOfBounds(attrs, 0);
    }

    private static void expectArrayIndexOutOfBounds(Attrs attrs, int index) {
        try {
            attrs.isSpecified(index);
        } catch (ArrayIndexOutOfBoundsException e) {
            return;
        }
        throw new RuntimeException("expected ArrayIndexOutOfBoundsException for index " + index);
    }
}
