/*
 * @test
 * @summary Checks that Textifier.visitLabel completes for new and repeated labels
 * @modules java.base/jdk.internal.org.objectweb.asm
 * @modules java.base/jdk.internal.org.objectweb.asm.util
 * @run main TextifierWitupTest
 */

import jdk.internal.org.objectweb.asm.Label;
import jdk.internal.org.objectweb.asm.util.Textifier;

public class TextifierWitupTest {

    public static void main(String[] args) {
        Textifier t = new Textifier();

        Label first = new Label();
        t.visitLabel(first);
        t.visitLabel(first);

        Label second = new Label();
        t.visitLabel(second);
        t.visitLabel(first);
    }
}
