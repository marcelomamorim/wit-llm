/*
 * @test
 * @summary Witup-generated behavioral checks for ClassVisitor.visitModule API version guard and delegation
 * @modules java.base/jdk.internal.org.objectweb.asm
 * @run main ClassVisitorWitupTest
 */

import jdk.internal.org.objectweb.asm.ClassVisitor;
import jdk.internal.org.objectweb.asm.ModuleVisitor;
import jdk.internal.org.objectweb.asm.Opcodes;

public class ClassVisitorWitupTest {

    static class RecordingVisitor extends ClassVisitor {
        int calls;
        String lastName;
        int lastAccess;
        String lastVersion;

        RecordingVisitor(int api) {
            super(api);
        }

        @Override
        public ModuleVisitor visitModule(String name, int access, String version) {
            calls++;
            lastName = name;
            lastAccess = access;
            lastVersion = version;
            return null;
        }
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testOldApiThrowsWithoutDelegate() {
        ClassVisitor v = new ClassVisitor(Opcodes.ASM5) {};
        boolean thrown = false;
        try {
            v.visitModule("m", 0, "1.0");
        } catch (RuntimeException e) {
            thrown = true;
        }
        check(thrown, "visitModule must throw RuntimeException when api < ASM6");
    }

    static void testOldApiThrowsEvenWithDelegate() {
        RecordingVisitor delegate = new RecordingVisitor(Opcodes.ASM6);
        ClassVisitor v = new ClassVisitor(Opcodes.ASM5, delegate) {};
        boolean thrown = false;
        try {
            v.visitModule("m", 0, "1.0");
        } catch (RuntimeException e) {
            thrown = true;
        }
        check(thrown, "visitModule must throw RuntimeException when api < ASM6 even with delegate");
        check(delegate.calls == 0, "delegate must not be called when guard throws");
    }

    static void testCurrentApiWithoutDelegateReturnsNull() {
        ClassVisitor v = new ClassVisitor(Opcodes.ASM6) {};
        ModuleVisitor mv = v.visitModule("m", 0, "1.0");
        check(mv == null, "visitModule without delegate must return null");
    }

    static void testCurrentApiDelegatesArguments() {
        RecordingVisitor delegate = new RecordingVisitor(Opcodes.ASM6);
        ClassVisitor v = new ClassVisitor(Opcodes.ASM6, delegate) {};
        ModuleVisitor mv = v.visitModule("mod.name", 42, "2.5");
        check(mv == null, "delegate returned null, so result must be null");
        check(delegate.calls == 1, "visitModule must delegate exactly once");
        check("mod.name".equals(delegate.lastName), "delegate received wrong name");
        check(delegate.lastAccess == 42, "delegate received wrong access");
        check("2.5".equals(delegate.lastVersion), "delegate received wrong version");
    }

    public static void main(String[] args) {
        testOldApiThrowsWithoutDelegate();
        testOldApiThrowsEvenWithDelegate();
        testCurrentApiWithoutDelegateReturnsNull();
        testCurrentApiDelegatesArguments();
    }
}
