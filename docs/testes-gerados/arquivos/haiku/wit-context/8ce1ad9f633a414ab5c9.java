/*
 * @test
 * @summary Verifies that Textifier.visitAttribute rejects a null attribute with NullPointerException
 * @modules java.base/jdk.internal.org.objectweb.asm.util
 * @run main TextifierWitupTest
 */

import jdk.internal.org.objectweb.asm.util.Textifier;

public class TextifierWitupTest {

    public static void main(String[] args) {
        Textifier textifier = new Textifier();
        boolean thrown = false;
        try {
            textifier.visitAttribute(null);
        } catch (NullPointerException expected) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected NullPointerException for null attribute");
        }
    }
}
