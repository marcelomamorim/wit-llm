/*
 * @test
 * @summary Checks CheckFieldAdapter.visitEnd delegation and repeated-call IllegalStateException
 * @modules java.base/jdk.internal.org.objectweb.asm.util java.base/jdk.internal.org.objectweb.asm
 * @run main CheckFieldAdapterWitupTest
 */

import jdk.internal.org.objectweb.asm.FieldVisitor;
import jdk.internal.org.objectweb.asm.Opcodes;
import jdk.internal.org.objectweb.asm.util.CheckFieldAdapter;

public class CheckFieldAdapterWitupTest {

    static int delegateEndCount = 0;

    public static void main(String[] args) throws Exception {
        testFirstVisitEndDelegates();
        testSecondVisitEndThrows();
        System.out.println("PASSED");
    }

    static void testFirstVisitEndDelegates() {
        delegateEndCount = 0;
        FieldVisitor delegate = new FieldVisitor(Opcodes.ASM5) {
            @Override
            public void visitEnd() {
                delegateEndCount++;
            }
        };
        CheckFieldAdapter adapter = new CheckFieldAdapter(delegate);
        adapter.visitEnd();
        if (delegateEndCount != 1) {
            throw new AssertionError("expected delegate visitEnd once, got " + delegateEndCount);
        }
    }

    static void testSecondVisitEndThrows() {
        delegateEndCount = 0;
        FieldVisitor delegate = new FieldVisitor(Opcodes.ASM5) {
            @Override
            public void visitEnd() {
                delegateEndCount++;
            }
        };
        CheckFieldAdapter adapter = new CheckFieldAdapter(delegate);
        adapter.visitEnd();
        try {
            adapter.visitEnd();
            throw new AssertionError("expected IllegalStateException on second visitEnd");
        } catch (IllegalStateException expected) {
            // expected
        }
        if (delegateEndCount != 1) {
            throw new AssertionError("delegate must not be called after failed visitEnd, count=" + delegateEndCount);
        }
    }
}
