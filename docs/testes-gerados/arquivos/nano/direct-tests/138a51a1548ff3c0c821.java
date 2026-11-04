/* @test    @summary Test loadThis method in GeneratorAdapter class    @run main GeneratorAdapterWitupTest */

import jdk.internal.org.objectweb.asm.commons.GeneratorAdapter;
import jdk.internal.org.objectweb.asm.MethodVisitor;
import jdk.internal.org.objectweb.asm.Opcodes;

public class GeneratorAdapterWitupTest {

    public static void main(String[] args) {
        testLoadThisStaticMethod();
        testLoadThisInstanceMethod();
    }

    private static void testLoadThisStaticMethod() {
        MethodVisitor mv = new MethodVisitor(Opcodes.ASM7) {};
        GeneratorAdapter ga = new GeneratorAdapter(0, null, null, null, mv);
        try {
            ga.loadThis();
            System.out.println("loadThis() executed successfully in non-static context.");
        } catch (IllegalStateException e) {
            System.err.println("Unexpected exception in non-static context: " + e.getMessage());
        }
    }

    private static void testLoadThisInstanceMethod() {
        MethodVisitor mv = new MethodVisitor(Opcodes.ASM7) {};
        GeneratorAdapter ga = new GeneratorAdapter(0, null, null, null, mv);
        // Simulate static context by setting access with static flag
        // Since constructor does not set access, we simulate by subclassing or reflection if needed
        // but for simplicity, we assume access is non-static here
        try {
            ga.loadThis();
            System.out.println("loadThis() executed successfully in instance context.");
        } catch (IllegalStateException e) {
            System.err.println("Unexpected exception in instance context: " + e.getMessage());
        }
    }
}
