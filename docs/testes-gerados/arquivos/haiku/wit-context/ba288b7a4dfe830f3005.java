/*
 * @test
 * @summary Checks CheckClassAdapter.visitEnd state validation and delegation
 * @modules java.base/jdk.internal.org.objectweb.asm.util
 * @run main CheckClassAdapterWitupTest
 */

import jdk.internal.org.objectweb.asm.ClassVisitor;
import jdk.internal.org.objectweb.asm.Opcodes;
import jdk.internal.org.objectweb.asm.util.CheckClassAdapter;

public class CheckClassAdapterWitupTest {

    static int endCount = 0;

    static ClassVisitor newDelegate() {
        return new ClassVisitor(Opcodes.ASM5) {
            @Override
            public void visitEnd() {
                endCount++;
            }
        };
    }

    static void checkVisitEndWithoutVisit() {
        CheckClassAdapter cca = new CheckClassAdapter(newDelegate());
        try {
            cca.visitEnd();
            throw new RuntimeException("visitEnd before visit must throw IllegalStateException");
        } catch (IllegalStateException expected) {
        }
    }

    static void checkVisitEndAfterVisit() {
        endCount = 0;
        CheckClassAdapter cca = new CheckClassAdapter(newDelegate());
        cca.visit(Opcodes.V1_8, Opcodes.ACC_PUBLIC, "Foo", null, "java/lang/Object", null);
        cca.visitEnd();
        if (endCount != 1) {
            throw new RuntimeException("delegate visitEnd not called exactly once: " + endCount);
        }
    }

    static void checkVisitEndTwice() {
        CheckClassAdapter cca = new CheckClassAdapter(newDelegate());
        cca.visit(Opcodes.V1_8, Opcodes.ACC_PUBLIC, "Foo", null, "java/lang/Object", null);
        cca.visitEnd();
        try {
            cca.visitEnd();
            throw new RuntimeException("second visitEnd must throw IllegalStateException");
        } catch (IllegalStateException expected) {
        }
    }

    public static void main(String[] args) {
        checkVisitEndWithoutVisit();
        checkVisitEndAfterVisit();
        checkVisitEndTwice();
    }
}
