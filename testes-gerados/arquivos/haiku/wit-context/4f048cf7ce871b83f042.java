/*
 * @test
 * @summary Verifies Attrs.isDeclared bounds checking and default (undeclared) results after setLength
 * @modules java.base/jdk.internal.util.xml.impl
 * @run main AttrsWitupTest
 */

import jdk.internal.util.xml.impl.Attrs;

public class AttrsWitupTest {

    public static void main(String[] args) {
        Attrs attrs = new Attrs();
        attrs.setLength((char) 2);

        check(!attrs.isDeclared(0), "index 0 should not be declared on fresh Attrs");
        check(!attrs.isDeclared(1), "index 1 should not be declared on fresh Attrs");

        expectOutOfBounds(attrs, -1);
        expectOutOfBounds(attrs, 2);
        expectOutOfBounds(attrs, Integer.MAX_VALUE);

        Attrs empty = new Attrs();
        expectOutOfBounds(empty, 0);
        expectOutOfBounds(empty, -1);
    }

    static void expectOutOfBounds(Attrs attrs, int index) {
        try {
            attrs.isDeclared(index);
        } catch (ArrayIndexOutOfBoundsException e) {
            return;
        }
        throw new RuntimeException("expected ArrayIndexOutOfBoundsException for index " + index);
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
