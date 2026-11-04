/*
 * @test
 * @summary Verifies Textifier.visitAttribute handles non-Textifiable attributes and rejects null
 * @modules java.base/jdk.internal.org.objectweb.asm java.base/jdk.internal.org.objectweb.asm.util
 * @run main TextifierWitupTest
 */

import jdk.internal.org.objectweb.asm.Attribute;
import jdk.internal.org.objectweb.asm.util.Textifier;

public class TextifierWitupTest {

    public static void main(String[] args) {
        Textifier textifier = new Textifier();

        textifier.visitAttribute(new Attribute("witupCustom") {});
        textifier.visitAttribute(new Attribute("witupOther") {});

        boolean thrown = false;
        try {
            textifier.visitAttribute(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("expected NullPointerException for null attribute");
        }
    }
}
