/*
 * @test
 * @summary Test RemappingClassAdapter.visitModule throws expected RuntimeException
 * @run main jdk.internal.org.objectweb.asm.commons.RemappingClassAdapterWitupTest
 */

import jdk.internal.org.objectweb.asm.commons.RemappingClassAdapter;
import jdk.internal.org.objectweb.asm.ClassVisitor;
import jdk.internal.org.objectweb.asm.Opcodes;
import jdk.internal.org.objectweb.asm.commons.Remapper;

public class RemappingClassAdapterWitupTest {

    public static void main(String[] args) {
        // Create dummy ClassVisitor and Remapper for constructor
        ClassVisitor cv = new ClassVisitor(Opcodes.ASM6) {};
        Remapper remapper = new Remapper() {};

        RemappingClassAdapter adapter = new RemappingClassAdapter(cv, remapper);

        try {
            adapter.visitModule("TestModule", 0, "1.0");
            throw new RuntimeException("Expected RuntimeException was not thrown");
        } catch (RuntimeException e) {
            String msg = e.getMessage();
            if (!"RemappingClassAdapter is deprecated, use ClassRemapper instead".equals(msg)) {
                throw new RuntimeException("Unexpected exception message: " + msg);
            }
        }
    }
}
