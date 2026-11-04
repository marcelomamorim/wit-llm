/*
 * @test
 * @summary Test visitFieldAttribute method of Textifier for basic delegation
 * @run main TextifierWitupTest
 */

import jdk.internal.org.objectweb.asm.Attribute;
import jdk.internal.org.objectweb.asm.util.Textifier;

public class TextifierWitupTest {

    public static void main(String[] args) {
        Textifier textifier = new Textifier();

        // Create a minimal Attribute subclass for testing
        Attribute testAttr = new Attribute("Test") {
            @Override
            protected Attribute read(ClassReader cr, int off, int len, char[] buf, int codeOff, Label[] labels) {
                return this;
            }

            @Override
            protected ByteVector write(ClassWriter cw, byte[] code, int len, int maxStack, int maxLocals) {
                return new ByteVector();
            }
        };

        // Call visitFieldAttribute with a non-null attribute to test delegation
        textifier.visitFieldAttribute(testAttr);

        // Call visitFieldAttribute with null to test null handling
        textifier.visitFieldAttribute(null);

        // If no exceptions thrown, test passes
        System.out.println("Test passed");
    }

    // Minimal stubs to satisfy compilation
    static class ClassReader {}
    static class Label {}
    static class ClassWriter {}
    static class ByteVector {}
}
