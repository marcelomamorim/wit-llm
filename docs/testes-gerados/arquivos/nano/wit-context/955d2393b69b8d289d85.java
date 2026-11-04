/* @test    @summary Test visitModule method for ClassVisitor class    @run main ClassVisitorWitupTest */

import jdk.internal.org.objectweb.asm.ClassVisitor;
import jdk.internal.org.objectweb.asm.ModuleVisitor;

public class ClassVisitorWitupTest {

    public static void main(String[] args) {
        testVisitModuleWithApiLessThanASM6();
        testVisitModuleWithApiAtLeastASM6();
    }

    private static void testVisitModuleWithApiLessThanASM6() {
        ClassVisitor visitor = new ClassVisitor(5) {
            @Override
            public ModuleVisitor visitModule(String name, int access, String version) {
                if (api < 6) {
                    try {
                        visitor.visitModule(name, access, version);
                        System.out.println("Test failed: Expected RuntimeException for api < Opcodes.ASM6");
                    } catch (RuntimeException e) {
                        System.out.println("Test passed: RuntimeException thrown as expected for api < Opcodes.ASM6");
                    }
                }
                return null;
            }
        };
        // Trigger the method with api < Opcodes.ASM6
        visitor.visitModule("testModule", 0, "");
    }

    private static void testVisitModuleWithApiAtLeastASM6() {
        ClassVisitor visitor = new ClassVisitor(6) {
            @Override
            public ModuleVisitor visitModule(String name, int access, String version) {
                // Should not throw, just return null
                ModuleVisitor mv = super.visitModule(name, access, version);
                if (mv == null) {
                    System.out.println("Test passed: visitModule returned null for api >= Opcodes.ASM6");
                } else {
                    System.out.println("Test failed: visitModule did not return null for api >= Opcodes.ASM6");
                }
                return mv;
            }
        };
        // Trigger the method with api >= Opcodes.ASM6
        visitor.visitModule("testModule", 0, "");
    }
}
