/*
 * @test
 * @summary WITUP-generated checks for ClassVisitor constructor API version validation
 * @modules java.base/jdk.internal.org.objectweb.asm
 * @run main ClassVisitorWitupTest
 */

import jdk.internal.org.objectweb.asm.ClassVisitor;
import jdk.internal.org.objectweb.asm.Opcodes;

public class ClassVisitorWitupTest {

    static final class Probe extends ClassVisitor {
        Probe(int api, ClassVisitor cv) {
            super(api, cv);
        }

        int apiValue() {
            return this.api;
        }

        ClassVisitor delegate() {
            return this.cv;
        }
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void expectIllegalArgument(int api, ClassVisitor cv, String label) {
        try {
            new Probe(api, cv);
        } catch (IllegalArgumentException e) {
            return;
        }
        throw new RuntimeException("expected IllegalArgumentException: " + label);
    }

    public static void main(String[] args) {
        expectIllegalArgument(Opcodes.ASM4 - 1, null, "api below ASM4");
        expectIllegalArgument(Opcodes.ASM6 + 1, null, "api above ASM6");
        expectIllegalArgument(Opcodes.ASM4 - 1, new Probe(Opcodes.ASM4, null), "api below ASM4 with delegate");
        expectIllegalArgument(Opcodes.ASM6 + 1, new Probe(Opcodes.ASM6, null), "api above ASM6 with delegate");

        Probe atLowerBound = new Probe(Opcodes.ASM4, null);
        check(atLowerBound.apiValue() == Opcodes.ASM4, "ASM4 api not stored");
        check(atLowerBound.delegate() == null, "null delegate not preserved");

        Probe atUpperBound = new Probe(Opcodes.ASM6, null);
        check(atUpperBound.apiValue() == Opcodes.ASM6, "ASM6 api not stored");

        Probe delegating = new Probe(Opcodes.ASM5, atUpperBound);
        check(delegating.apiValue() == Opcodes.ASM5, "ASM5 api not stored");
        check(delegating.delegate() == atUpperBound, "delegate visitor not preserved");
    }
}
