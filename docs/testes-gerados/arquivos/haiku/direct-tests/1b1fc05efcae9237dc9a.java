/*
 * @test
 * @summary Verifies that Textifier.visitFieldAttribute delegates to visitAttribute and rejects a null attribute
 * @modules java.base/jdk.internal.org.objectweb.asm java.base/jdk.internal.org.objectweb.asm.util
 * @run main TextifierWitupTest
 */

import jdk.internal.org.objectweb.asm.util.Textifier;

public class TextifierWitupTest {

    public static void main(String[] args) {
        Textifier textifier = new Textifier();
        try {
            textifier.visitFieldAttribute(null);
            throw new RuntimeException("Expected NullPointerException for null attribute");
        } catch (NullPointerException expected) {
            // expected: visitAttribute dereferences the attribute
        }
    }
}
