/*
 * @test
 * @summary Verifies that the AnnotationVisitor constructors store the api version and delegate visitor.
 * @modules java.base/jdk.internal.org.objectweb.asm
 * @run main AnnotationVisitorWitupTest
 */

import jdk.internal.org.objectweb.asm.AnnotationVisitor;
import jdk.internal.org.objectweb.asm.Opcodes;

public class AnnotationVisitorWitupTest {

    static class TestVisitor extends AnnotationVisitor {
        TestVisitor(int api) {
            super(api);
        }

        TestVisitor(int api, AnnotationVisitor av) {
            super(api, av);
        }

        int apiValue() {
            return api;
        }

        AnnotationVisitor delegate() {
            return av;
        }
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) {
        TestVisitor v1 = new TestVisitor(Opcodes.ASM5);
        check(v1.apiValue() == Opcodes.ASM5, "api must be stored by single-arg constructor");
        check(v1.delegate() == null, "single-arg constructor must set av to null");

        TestVisitor v2 = new TestVisitor(Opcodes.ASM4);
        check(v2.apiValue() == Opcodes.ASM4, "api must be stored for ASM4");
        check(v2.delegate() == null, "av must be null for ASM4 single-arg constructor");

        TestVisitor delegate = new TestVisitor(Opcodes.ASM5);
        TestVisitor v3 = new TestVisitor(Opcodes.ASM5, delegate);
        check(v3.apiValue() == Opcodes.ASM5, "api must be stored by two-arg constructor");
        check(v3.delegate() == delegate, "two-arg constructor must store the delegate visitor");

        TestVisitor v4 = new TestVisitor(Opcodes.ASM5, null);
        check(v4.delegate() == null, "explicit null delegate must be stored as null");
    }
}
