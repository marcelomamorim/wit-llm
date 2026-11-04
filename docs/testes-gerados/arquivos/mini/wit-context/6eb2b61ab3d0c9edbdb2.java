/*
 * @test
 * @summary Test visitFieldAttribute method of ASMifier for exception safety and normal behavior
 * @run main ASMifierWitupTest
 */

import jdk.internal.org.objectweb.asm.util.ASMifier;
import jdk.internal.org.objectweb.asm.Attribute;

public class ASMifierWitupTest {

    public static void main(String[] args) {
        ASMifier asmifier = new ASMifier();

        // Test with null attribute - should not throw and should call visitAttribute
        try {
            asmifier.visitFieldAttribute(null);
        } catch (Exception e) {
            throw new RuntimeException("visitFieldAttribute(null) threw unexpected exception", e);
        }

        // Test with a minimal custom Attribute subclass instance
        Attribute testAttr = new Attribute("Test") {
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
            asmifier.visitFieldAttribute(testAttr);
        } catch (Exception e) {
            throw new RuntimeException("visitFieldAttribute(custom Attribute) threw unexpected exception", e);
        }

        // No direct exception throwing expected from visitFieldAttribute itself,
        // so no assertThrows tests are added.

        System.out.println("ASMifierWitupTest passed");
    }
}
