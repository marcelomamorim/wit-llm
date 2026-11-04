/*
 * @test
 * @summary Test ASMifier.visitFieldAttribute method behavior
 * @run main ASMifierWitupTest
 */

import jdk.internal.org.objectweb.asm.util.ASMifier;
import jdk.internal.org.objectweb.asm.Attribute;

public class ASMifierWitupTest {

    public static void main(String[] args) {
        ASMifier asmifier = new ASMifier();

        // Test with a null Attribute reference
        // Expect no exception and no observable effect
        asmifier.visitFieldAttribute(null);

        // Test with a dummy Attribute instance
        Attribute dummyAttr = new Attribute("Dummy") {
            @Override
            protected Attribute read(final ClassReader cr, final int off, final int len, final char[] buf, final int codeOff, final Label[] labels) {
                return null;
            }

            @Override
            protected ByteVector write(final ClassWriter cw, final byte[] code, final int len, final int maxStack, final int maxLocals) {
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

        asmifier.visitFieldAttribute(dummyAttr);

        // If no exceptions thrown, test passes
        System.out.println("PASS");
    }
}
