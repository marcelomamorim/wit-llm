/*
 * @test
 * @summary Verifies ClassVisitor.visitModule API version check and delegation behavior
 * @modules java.base/jdk.internal.org.objectweb.asm
 * @run main ClassVisitorWitupTest
 */

import jdk.internal.org.objectweb.asm.ClassVisitor;
import jdk.internal.org.objectweb.asm.ModuleVisitor;
import jdk.internal.org.objectweb.asm.Opcodes;

public class ClassVisitorWitupTest {

    static class Recording extends ClassVisitor {
        boolean called;
        String seenName;
        int seenAccess;
        String seenVersion;

        Recording() {
            super(Opcodes.ASM6);
        }

        @Override
        public ModuleVisitor visitModule(String name, int access, String version) {
            called = true;
            seenName = name;
            seenAccess = access;
            seenVersion = version;
            return null;
        }
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static boolean throwsRuntimeException(ClassVisitor v) {
        try {
            v.visitModule("m", 0, "1.0");
            return false;
        } catch (RuntimeException e) {
            return true;
        }
    }

    public static void main(String[] args) {
        // api lower than ASM6 without delegate must throw
        check(throwsRuntimeException(new ClassVisitor(Opcodes.ASM5) {}),
              "ASM5 visitor should throw from visitModule");

        // api lower than ASM6 with delegate must still throw (api check is first)
        Recording delegate = new Recording();
        ClassVisitor oldApi = new ClassVisitor(Opcodes.ASM5, delegate) {};
        check(throwsRuntimeException(oldApi),
              "ASM5 visitor with delegate should throw from visitModule");
        check(!delegate.called,
              "delegate must not be called when api check fails");

        // ASM6 without delegate returns null
        ClassVisitor noDelegate = new ClassVisitor(Opcodes.ASM6) {};
        Object result = noDelegate.visitModule("m", 1, "v");
        check(result == null, "visitModule without delegate should return null");

        // ASM6 with delegate forwards arguments and returns delegate result
        Recording target = new Recording();
        ClassVisitor forwarding = new ClassVisitor(Opcodes.ASM6, target) {};
        Object forwarded = forwarding.visitModule("mod", 42, "2.0");
        check(target.called, "delegate visitModule should be invoked");
        check("mod".equals(target.seenName), "name should be forwarded");
        check(target.seenAccess == 42, "access should be forwarded");
        check("2.0".equals(target.seenVersion), "version should be forwarded");
        check(forwarded == null, "delegate returned null, so result should be null");
    }
}
