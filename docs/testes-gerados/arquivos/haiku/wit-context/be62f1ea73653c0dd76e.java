/*
 * @test
 * @summary Verifies that Textifier.visitLineNumber does not throw for valid and boundary line numbers
 * @modules java.base/jdk.internal.org.objectweb.asm
 * @modules java.base/jdk.internal.org.objectweb.asm.util
 * @run main TextifierWitupTest
 */

import jdk.internal.org.objectweb.asm.Label;
import jdk.internal.org.objectweb.asm.util.Textifier;

public class TextifierWitupTest {

    public static void main(String[] args) {
        Textifier t = new Textifier();
        t.visitLineNumber(1, new Label());
        t.visitLineNumber(0, new Label());
        t.visitLineNumber(-1, new Label());
        t.visitLineNumber(Integer.MAX_VALUE, new Label());
        t.visitLineNumber(Integer.MIN_VALUE, new Label());

        Textifier t2 = new Textifier();
        for (int i = 0; i < 100; i++) {
            t2.visitLineNumber(i, new Label());
        }
    }
}
