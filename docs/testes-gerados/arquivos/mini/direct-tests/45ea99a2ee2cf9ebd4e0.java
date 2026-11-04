/*
 * @test
 * @summary Test ASMifier.visitMethodAttribute(Attribute) method
 * @run main ASMifierWitupTest
 */

import jdk.internal.org.objectweb.asm.util.ASMifier;
import jdk.internal.org.objectweb.asm.Attribute;

public class ASMifierWitupTest {

    public static void main(String[] args) {
        ASMifier asmifier = new ASMifier();

        // Test with a null Attribute (allowed if no exception expected)
        try {
            asmifier.visitMethodAttribute(null);
        } catch (Exception e) {
            throw new RuntimeException("visitMethodAttribute(null) threw unexpected exception", e);
        }

        // Test with a simple custom Attribute instance
        Attribute attr = new Attribute("TestAttr") {
            @Override
            protected Attribute read(final ClassReader cr, final int off, final int len, final char[] buf, final int codeOff, final Label[] labels) {
                return this;
            }

            @Override
            protected ByteVector write(final ClassWriter cw, final byte[] code, final int len, final int maxStack, final int maxLocals) {
                return null;
            }
        };

        try {
            asmifier.visitMethodAttribute(attr);
        } catch (Exception e) {
            throw new RuntimeException("visitMethodAttribute(custom Attribute) threw unexpected exception", e);
        }
    }
}
