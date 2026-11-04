/*
 * @test
 * @summary Verifies Attrs.isDeclared(int) for valid indices without declared values and for out-of-range indices.
 * @modules java.base/jdk.internal.util.xml.impl
 * @run main AttrsWitupTest
 */

import jdk.internal.util.xml.impl.Attrs;

public class AttrsWitupTest {

    public static void main(String[] args) {
        Attrs attrs = new Attrs();
        attrs.setLength((char) 3);

        if (attrs.getLength() != 3) {
            throw new RuntimeException("Expected length 3");
        }

        for (int i = 0; i < 3; i++) {
            if (attrs.isDeclared(i)) {
                throw new RuntimeException("Expected isDeclared(" + i + ") to be false");
            }
        }

        expectOutOfBounds(attrs, -1);
        expectOutOfBounds(attrs, 3);
        expectOutOfBounds(attrs, 100);

        Attrs empty = new Attrs();
        expectOutOfBounds(empty, 0);

        empty.setLength((char) 0);
        expectOutOfBounds(empty, 0);
    }

    private static void expectOutOfBounds(Attrs attrs, int index) {
        try {
            boolean result = attrs.isDeclared(index);
            throw new RuntimeException("Expected ArrayIndexOutOfBoundsException for index "
                    + index + " but got " + result);
        } catch (ArrayIndexOutOfBoundsException expected) {
            // expected
        }
    }
}
