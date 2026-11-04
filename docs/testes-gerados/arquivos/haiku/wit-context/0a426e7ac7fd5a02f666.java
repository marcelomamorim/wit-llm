/*
 * @test
 * @summary Verifies the public ModuleVisitor(int) constructor accepts ASM6 and delegates to the (int, ModuleVisitor) constructor with a null delegate
 * @modules java.base/jdk.internal.org.objectweb.asm
 * @run main ModuleVisitorWitupTest
 */

import jdk.internal.org.objectweb.asm.ModuleVisitor;
import jdk.internal.org.objectweb.asm.Opcodes;

public class ModuleVisitorWitupTest {
    public static void main(String[] args) {
        ModuleVisitor single = new ModuleVisitor(Opcodes.ASM6) {};
        if (single == null) {
            throw new RuntimeException("ModuleVisitor(int) returned null");
        }
        if (!(single instanceof ModuleVisitor)) {
            throw new RuntimeException("Unexpected type for ModuleVisitor(int)");
        }

        ModuleVisitor withDelegate = new ModuleVisitor(Opcodes.ASM6, single) {};
        if (withDelegate == null) {
            throw new RuntimeException("ModuleVisitor(int, ModuleVisitor) returned null");
        }

        ModuleVisitor withNullDelegate = new ModuleVisitor(Opcodes.ASM6, null) {};
        if (withNullDelegate == null) {
            throw new RuntimeException("ModuleVisitor(int, null) returned null");
        }

        single.visitEnd();
        withNullDelegate.visitEnd();
    }
}
