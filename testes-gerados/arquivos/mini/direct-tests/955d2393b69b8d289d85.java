/*
 * @test
 * @summary Test visitModule method of ClassVisitor for API version and delegation
 * @run main ClassVisitorWitupTest
 */

import jdk.internal.org.objectweb.asm.ClassVisitor;
import jdk.internal.org.objectweb.asm.ModuleVisitor;
import jdk.internal.org.objectweb.asm.Opcodes;

public class ClassVisitorWitupTest {

    public static void main(String[] args) {
        testVisitModuleThrowsForApiLessThanASM6();
        testVisitModuleDelegatesToCv();
        testVisitModuleReturnsNullWhenNoCv();
    }

    private static void testVisitModuleThrowsForApiLessThanASM6() {
        ClassVisitor cv = new ClassVisitor(Opcodes.ASM5) {
            @Override
            public ModuleVisitor visitModule(String name, int access, String version) {
                return super.visitModule(name, access, version);
            }
        };

        try {
            cv.visitModule("mod", 0, "1.0");
            throw new RuntimeException("Expected RuntimeException for api < ASM6");
        } catch (RuntimeException e) {
            // expected
        }
    }

    private static void testVisitModuleDelegatesToCv() {
        final boolean[] called = new boolean[1];
        ClassVisitor delegate = new ClassVisitor(Opcodes.ASM6) {
            @Override
            public ModuleVisitor visitModule(String name, int access, String version) {
                called[0] = true;
                if (!"mod".equals(name) || access != 123 || !"1.0".equals(version)) {
                    throw new RuntimeException("Arguments mismatch");
                }
                return new ModuleVisitor(Opcodes.ASM6) {};
            }
        };

        ClassVisitor cv = new ClassVisitor(Opcodes.ASM6, delegate) {};

        ModuleVisitor mv = cv.visitModule("mod", 123, "1.0");
        if (!called[0]) {
            throw new RuntimeException("visitModule was not delegated to cv");
        }
        if (mv == null) {
            throw new RuntimeException("visitModule returned null but delegate returned non-null");
        }
    }

    private static void testVisitModuleReturnsNullWhenNoCv() {
        ClassVisitor cv = new ClassVisitor(Opcodes.ASM6) {};
        ModuleVisitor mv = cv.visitModule("mod", 0, "1.0");
        if (mv != null) {
            throw new RuntimeException("Expected null when cv is null");
        }
    }

    // Minimal stub for ModuleVisitor to allow compilation
    public static abstract class ModuleVisitor {
        protected final int api;
        public ModuleVisitor(int api) {
            this.api = api;
        }
    }
}
