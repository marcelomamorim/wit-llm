/*
 * @test
 * @summary Test Textifier.visitFieldAttribute method with a sample Attribute
 * @run main TextifierWitupTest
 */

import jdk.internal.org.objectweb.asm.util.Textifier;
import jdk.internal.org.objectweb.asm.Attribute;

public class TextifierWitupTest {
    public static void main(String[] args) {
        Textifier textifier = new Textifier();
        Attribute attr = new Attribute("TestAttribute") {
            @Override
            protected Attribute read(ClassReader cr, int off, int len, char[] buf, int codeOff, Label[] labels) {
                return this;
            }

            @Override
            protected ByteVector write(ClassWriter cw, byte[] code, int len, int maxStack, int maxLocals) {
                return null;
            }

            @Override
            public boolean isUnknown() {
                return true;
            }

            @Override
            public boolean isCodeAttribute() {
                return false;
            }
        };

        // Call visitFieldAttribute with a non-null Attribute
        textifier.visitFieldAttribute(attr);

        // Call visitFieldAttribute with null to check no exception is thrown
        textifier.visitFieldAttribute(null);
    }
}
