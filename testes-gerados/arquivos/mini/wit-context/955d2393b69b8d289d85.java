/*
 * @test
 * @summary Test visitModule method of ClassVisitor for API version checks and delegation
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
        // Create a ClassVisitor with api less than Opcodes.ASM6 to trigger exception
        final int api = Opcodes.ASM5; // ASM5 < ASM6
        ClassVisitor cv = new ClassVisitor(api) {
            @Override
            public ModuleVisitor visitModule(String name, int access, String version) {
                return super.visitModule(name, access, version);
            }
        };

        boolean thrown = false;
        try {
            cv.visitModule("TestModule", 0, "1.0");
        } catch (RuntimeException e) {
            thrown = true;
        }

        if (!thrown) {
            throw new AssertionError("Expected RuntimeException for api < ASM6");
        }
    }

    private static void testVisitModuleDelegatesToCv() {
        final int api = Opcodes.ASM6; // ASM6 or higher disables exception

        // Create a delegate ClassVisitor that records if visitModule is called
        class Delegate extends ClassVisitor {
            boolean called = false;
            String nameArg = null;
            int accessArg = -1;
            String versionArg = null;

            Delegate() {
                super(api);
            }

            @Override
            public ModuleVisitor visitModule(String name, int access, String version) {
                called = true;
                nameArg = name;
                accessArg = access;
                versionArg = version;
                return null;
            }
        }

        Delegate delegate = new Delegate();

        // Create ClassVisitor with delegate
        ClassVisitor cv = new ClassVisitor(api, delegate) {
            @Override
            public ModuleVisitor visitModule(String name, int access, String version) {
                return super.visitModule(name, access, version);
            }
        };

        ModuleVisitor result = cv.visitModule("MyModule", 123, "2.0");

        if (result != null) {
            throw new AssertionError("Expected null ModuleVisitor from delegate");
        }
        if (!delegate.called) {
            throw new AssertionError("Delegate visitModule was not called");
        }
        if (!"MyModule".equals(delegate.nameArg) || delegate.accessArg != 123 || !"2.0".equals(delegate.versionArg)) {
            throw new AssertionError("Delegate visitModule called with wrong arguments");
        }
    }

    private static void testVisitModuleReturnsNullWhenNoCv() {
        final int api = Opcodes.ASM6; // ASM6 or higher disables exception

        ClassVisitor cv = new ClassVisitor(api) {
            @Override
            public ModuleVisitor visitModule(String name, int access, String version) {
                return super.visitModule(name, access, version);
            }
        };

        ModuleVisitor result = cv.visitModule("NoDelegate", 0, "1.0");

        if (result != null) {
            throw new AssertionError("Expected null when no delegate ClassVisitor");
        }
    }

}