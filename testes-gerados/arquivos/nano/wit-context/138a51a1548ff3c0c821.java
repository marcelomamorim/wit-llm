/* @test    @summary Test loadThis method for static method exception scenario    @run main GeneratorAdapterWitupTest */

import jdk.internal.org.objectweb.asm.Opcodes;
import jdk.internal.org.objectweb.asm.MethodVisitor;
import jdk.internal.org.objectweb.asm.Type;
import jdk.internal.org.objectweb.asm.commons.GeneratorAdapter;

public class GeneratorAdapterWitupTest {

    public static void main(String[] args) {
        testLoadThisStaticMethodThrows();
    }

    private static void testLoadThisStaticMethodThrows() {
        MethodVisitor mv = null; // Placeholder, as actual MethodVisitor is not needed for this test
        int access = Opcodes.ACC_STATIC;
        GeneratorAdapter ga = new GeneratorAdapter(access, null, null, null, null);
        boolean exceptionThrown = false;
        try {
            ga.loadThis();
        } catch (IllegalStateException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected IllegalStateException was not thrown.");
        }
    }
}
