/*
 * @test
 * @summary Verifies CheckClassAdapter.visitSource accepts a first call and rejects a second call with IllegalStateException
 * @modules java.base/jdk.internal.org.objectweb.asm java.base/jdk.internal.org.objectweb.asm.util
 * @run main CheckClassAdapterWitupTest
 */

import jdk.internal.org.objectweb.asm.Opcodes;
import jdk.internal.org.objectweb.asm.util.CheckClassAdapter;

public class CheckClassAdapterWitupTest {

    public static void main(String[] args) {
        testFirstVisitSourceAccepted();
        testSecondVisitSourceRejected();
        testSecondVisitSourceWithDifferentArgsRejected();
    }

    static void testFirstVisitSourceAccepted() {
        CheckClassAdapter cca = new CheckClassAdapter(null, false);
        cca.visit(Opcodes.V1_8, Opcodes.ACC_PUBLIC, "Foo", null, "java/lang/Object", null);
        cca.visitSource("Foo.java", "debug");
    }

    static void testSecondVisitSourceRejected() {
        CheckClassAdapter cca = new CheckClassAdapter(null, false);
        cca.visit(Opcodes.V1_8, Opcodes.ACC_PUBLIC, "Foo", null, "java/lang/Object", null);
        cca.visitSource("Foo.java", null);
        try {
            cca.visitSource("Foo.java", null);
            throw new AssertionError("Expected IllegalStateException on second visitSource");
        } catch (IllegalStateException e) {
            if (!"visitSource can be called only once.".equals(e.getMessage())) {
                throw new AssertionError("Unexpected message: " + e.getMessage());
            }
        }
    }

    static void testSecondVisitSourceWithDifferentArgsRejected() {
        CheckClassAdapter cca = new CheckClassAdapter(null, false);
        cca.visit(Opcodes.V1_8, Opcodes.ACC_PUBLIC, "Bar", null, "java/lang/Object", null);
        cca.visitSource("A.java", "d1");
        try {
            cca.visitSource("B.java", "d2");
            throw new AssertionError("Expected IllegalStateException on second visitSource");
        } catch (IllegalStateException e) {
            // expected
        }
    }
}
