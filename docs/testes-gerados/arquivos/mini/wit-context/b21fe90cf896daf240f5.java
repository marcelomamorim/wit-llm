/*
 * @test
 * @summary Test visitLabel method of Textifier for basic functionality and no exception
 * @run main TextifierWitupTest
 */

import jdk.internal.org.objectweb.asm.util.Textifier;
import jdk.internal.org.objectweb.asm.Label;

public class TextifierWitupTest {

    public static void main(String[] args) {
        Textifier textifier = new Textifier();
        Label label = new Label();

        // Call visitLabel with a normal Label instance
        try {
            textifier.visitLabel(label);
        } catch (Exception e) {
            throw new RuntimeException("visitLabel threw unexpected exception", e);
        }

        // The method does not return a value and does not expose internal state,
        // so we only verify that no exception is thrown for a valid Label.

        // No further assertions possible without reflection or internal access.
    }
}
