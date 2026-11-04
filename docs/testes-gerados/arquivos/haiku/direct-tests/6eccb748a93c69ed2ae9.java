/*
 * @test
 * @summary Verifies that CheckFieldAdapter.visitEnd() delegates visitEnd to the wrapped FieldVisitor
 * @modules java.base/jdk.internal.org.objectweb.asm.util java.base/jdk.internal.org.objectweb.asm
 * @run main CheckFieldAdapterWitupTest
 */

import jdk.internal.org.objectweb.asm.FieldVisitor;
import jdk.internal.org.objectweb.asm.Opcodes;
import jdk.internal.org.objectweb.asm.util.CheckFieldAdapter;

public class CheckFieldAdapterWitupTest {

    static class RecordingFieldVisitor extends FieldVisitor {
        int endCount = 0;

        RecordingFieldVisitor() {
            super(Opcodes.ASM5, null);
        }

        @Override
        public void visitEnd() {
            endCount++;
        }
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testVisitEndDelegates() {
        RecordingFieldVisitor delegate = new RecordingFieldVisitor();
        CheckFieldAdapter adapter = new CheckFieldAdapter(delegate);
        adapter.visitEnd();
        check(delegate.endCount == 1, "visitEnd must be delegated exactly once, got " + delegate.endCount);
    }

    public static void main(String[] args) {
        testVisitEndDelegates();
    }
}
