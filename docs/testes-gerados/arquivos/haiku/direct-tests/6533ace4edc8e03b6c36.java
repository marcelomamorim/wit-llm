/*
 * @test
 * @summary Verifies that Label.getOffset() throws IllegalStateException while the label is unresolved
 * @modules java.base/jdk.internal.org.objectweb.asm
 * @run main LabelWitupTest
 */

import jdk.internal.org.objectweb.asm.Label;

public class LabelWitupTest {

    public static void main(String[] args) {
        checkUnresolved(new Label());
        checkUnresolved(new Label());

        Label label = new Label();
        checkUnresolved(label);
        checkUnresolved(label);
    }

    private static void checkUnresolved(Label label) {
        try {
            int offset = label.getOffset();
            throw new AssertionError("Expected IllegalStateException but got offset " + offset);
        } catch (IllegalStateException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.contains("not been resolved")) {
                throw new AssertionError("Unexpected message: " + msg, e);
            }
        }
    }
}
