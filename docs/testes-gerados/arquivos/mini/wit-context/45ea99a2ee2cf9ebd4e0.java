/*
 * @test
 * @summary Test visitMethodAttribute method of ASMifier for exception safety and normal behavior
 * @run main ASMifierWitupTest
 */

import jdk.internal.org.objectweb.asm.util.ASMifier;
import jdk.internal.org.objectweb.asm.Attribute;

public class ASMifierWitupTest {

    public static void main(String[] args) {
        ASMifier asmifier = new ASMifier();

        // Test with null attribute: should not throw and just call visitAttribute
        try {
            asmifier.visitMethodAttribute(null);
        } catch (Throwable t) {
            throw new AssertionError("visitMethodAttribute(null) threw " + t);
        }

        // Test with a minimal custom Attribute subclass instance
        Attribute dummyAttr = new Attribute("Dummy") {
            @Override
            protected Attribute read(ClassReader cr, int off, int len, char[] buf, int codeOff, Label[] labels) {
                return this;
            }

            @Override
            public byte[] write(ClassWriter cw, byte[] code, int len, int maxStack, int maxLocals) {
                return new byte[0];
            }
        };

        try {
            asmifier.visitMethodAttribute(dummyAttr);
        } catch (Throwable t) {
            throw new AssertionError("visitMethodAttribute(dummyAttr) threw " + t);
        }
    }
}
