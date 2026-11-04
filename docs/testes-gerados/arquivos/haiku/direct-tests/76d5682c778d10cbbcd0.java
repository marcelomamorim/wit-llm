/*
 * @test
 * @summary Verify Attrs.isSpecified bounds checking and default specified behavior
 * @modules java.base/jdk.internal.util.xml.impl java.base/jdk.internal.org.xml.sax
 * @run main AttrsWitupTest
 */

import jdk.internal.util.xml.impl.Attrs;

public class AttrsWitupTest {

    public static void main(String[] args) {
        Attrs attrs = new Attrs();
        attrs.setLength((char) 3);

        for (int i = 0; i < 3; i++) {
            if (!attrs.isSpecified(i)) {
                throw new RuntimeException("Expected isSpecified(" + i + ") to be true");
            }
        }

        expectOutOfBounds(attrs, -1);
        expectOutOfBounds(attrs, 3);

        Attrs empty = new Attrs();
        expectOutOfBounds(empty, 0);

        Attrs big = new Attrs();
        big.setLength((char) 20);
        if (!big.isSpecified(19)) {
            throw new RuntimeException("Expected isSpecified(19) to be true");
        }
        expectOutOfBounds(big, 20);
    }

    static void expectOutOfBounds(Attrs a, int idx) {
        try {
            a.isSpecified(idx);
        } catch (ArrayIndexOutOfBoundsException e) {
            return;
        }
        throw new RuntimeException("Expected ArrayIndexOutOfBoundsException for index " + idx);
    }
}
