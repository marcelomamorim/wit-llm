/*
 * @test
 * @summary Checks ParameterNode.accept forwards name and access to MethodVisitor.visitParameter and rejects visitors with api below ASM5
 * @modules java.base/jdk.internal.org.objectweb.asm java.base/jdk.internal.org.objectweb.asm.tree
 * @run main ParameterNodeWitupTest
 */

import jdk.internal.org.objectweb.asm.MethodVisitor;
import jdk.internal.org.objectweb.asm.Opcodes;
import jdk.internal.org.objectweb.asm.tree.ParameterNode;

public class ParameterNodeWitupTest {

    static final class RecordingVisitor extends MethodVisitor {
        String recordedName;
        int recordedAccess;
        int calls;

        RecordingVisitor(int api) {
            super(api);
        }

        @Override
        public void visitParameter(String name, int access) {
            this.recordedName = name;
            this.recordedAccess = access;
            this.calls++;
        }
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) {
        ParameterNode node = new ParameterNode("x", Opcodes.ACC_FINAL);
        RecordingVisitor visitor = new RecordingVisitor(Opcodes.ASM5);
        node.accept(visitor);
        check(visitor.calls == 1, "visitParameter should be called once");
        check("x".equals(visitor.recordedName), "name should be forwarded");
        check(visitor.recordedAccess == Opcodes.ACC_FINAL, "access should be forwarded");

        ParameterNode nullNameNode = new ParameterNode(null, 0);
        RecordingVisitor nullVisitor = new RecordingVisitor(Opcodes.ASM5);
        nullNameNode.accept(nullVisitor);
        check(nullVisitor.calls == 1, "visitParameter should be called for null name");
        check(nullVisitor.recordedName == null, "null name should be forwarded as null");
        check(nullVisitor.recordedAccess == 0, "zero access should be forwarded");

        ParameterNode combined = new ParameterNode("p", Opcodes.ACC_SYNTHETIC | Opcodes.ACC_MANDATED);
        RecordingVisitor combinedVisitor = new RecordingVisitor(Opcodes.ASM5);
        combined.accept(combinedVisitor);
        check((combinedVisitor.recordedAccess == (Opcodes.ACC_SYNTHETIC | Opcodes.ACC_MANDATED)),
              "combined access flags should be forwarded");

        boolean thrown = false;
        try {
            node.accept(new RecordingVisitor(Opcodes.ASM4));
        } catch (RuntimeException e) {
            thrown = true;
        }
        check(thrown, "visitor with api below ASM5 should cause RuntimeException");
    }
}
