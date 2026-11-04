/*
 * @test
 * @summary CheckMethodAdapter.visitCode rejects abstract methods and delegates for non-abstract methods
 * @modules java.base/jdk.internal.org.objectweb.asm java.base/jdk.internal.org.objectweb.asm.util
 * @run main CheckMethodAdapterWitupTest
 */

import java.util.HashMap;
import java.util.Map;

import jdk.internal.org.objectweb.asm.Label;
import jdk.internal.org.objectweb.asm.MethodVisitor;
import jdk.internal.org.objectweb.asm.Opcodes;
import jdk.internal.org.objectweb.asm.util.CheckMethodAdapter;

public class CheckMethodAdapterWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }

    static final class RecordingVisitor extends MethodVisitor {
        boolean codeVisited = false;

        RecordingVisitor() {
            super(Opcodes.ASM5);
        }

        @Override
        public void visitCode() {
            codeVisited = true;
        }
    }

    static void testAbstractMethodThrows() {
        RecordingVisitor rv = new RecordingVisitor();
        Map<Label, Integer> labels = new HashMap<Label, Integer>();
        CheckMethodAdapter cma = new CheckMethodAdapter(
                Opcodes.ACC_ABSTRACT, "m", "()V", rv, labels);
        String message = null;
        try {
            cma.visitCode();
        } catch (RuntimeException e) {
            message = e.getMessage();
        }
        check(message != null, "expected RuntimeException for abstract method");
        check("Abstract methods cannot have code".equals(message),
              "unexpected message: " + message);
        check(!rv.codeVisited, "delegate must not be visited for abstract method");
    }

    static void testAbstractWithOtherFlagsThrows() {
        RecordingVisitor rv = new RecordingVisitor();
        Map<Label, Integer> labels = new HashMap<Label, Integer>();
        int access = Opcodes.ACC_PUBLIC | Opcodes.ACC_ABSTRACT;
        CheckMethodAdapter cma = new CheckMethodAdapter(
                access, "m", "()V", rv, labels);
        boolean thrown = false;
        try {
            cma.visitCode();
        } catch (RuntimeException e) {
            thrown = true;
        }
        check(thrown, "expected RuntimeException when ACC_ABSTRACT is combined with other flags");
        check(!rv.codeVisited, "delegate must not be visited for abstract method");
    }

    static void testNonAbstractDelegates() {
        RecordingVisitor rv = new RecordingVisitor();
        Map<Label, Integer> labels = new HashMap<Label, Integer>();
        CheckMethodAdapter cma = new CheckMethodAdapter(
                Opcodes.ACC_PUBLIC, "m", "()V", rv, labels);
        cma.visitCode();
        check(rv.codeVisited, "visitCode must be delegated for non-abstract method");
    }

    public static void main(String[] args) {
        testAbstractMethodThrows();
        testAbstractWithOtherFlagsThrows();
        testNonAbstractDelegates();
    }
}
