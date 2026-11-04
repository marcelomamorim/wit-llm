/*
 * @test
 * @summary Test visitSource method of CheckClassAdapter for correct state handling and exception
 * @run main CheckClassAdapterWitupTest
 */

import jdk.internal.org.objectweb.asm.util.CheckClassAdapter;
import jdk.internal.org.objectweb.asm.ClassVisitor;
import jdk.internal.org.objectweb.asm.Opcodes;

public class CheckClassAdapterWitupTest {

    static class DummyClassVisitor extends ClassVisitor {
        boolean visitedSource = false;
        String visitedFile = null;
        String visitedDebug = null;

        DummyClassVisitor() {
            super(Opcodes.ASM7);
        }

        @Override
        public void visitSource(String file, String debug) {
            visitedSource = true;
            visitedFile = file;
            visitedDebug = debug;
        }
    }

    public static void main(String[] args) {
        DummyClassVisitor dummy = new DummyClassVisitor();
        CheckClassAdapter cca = new CheckClassAdapter(dummy);

        // First call to visitSource should succeed and delegate to super
        cca.visitSource("TestFile.java", "DebugInfo");
        if (!dummy.visitedSource) {
            throw new AssertionError("visitSource was not delegated to super class visitor");
        }
        if (!"TestFile.java".equals(dummy.visitedFile)) {
            throw new AssertionError("visitSource file argument not passed correctly");
        }
        if (!"DebugInfo".equals(dummy.visitedDebug)) {
            throw new AssertionError("visitSource debug argument not passed correctly");
        }

        // Second call to visitSource should throw IllegalStateException
        boolean threw = false;
        try {
            cca.visitSource("AnotherFile.java", "AnotherDebug");
        } catch (IllegalStateException e) {
            threw = true;
            String msg = e.getMessage();
            if (msg == null || !msg.contains("visitSource can be called only once")) {
                throw new AssertionError("IllegalStateException message unexpected: " + msg);
            }
        }
        if (!threw) {
            throw new AssertionError("Expected IllegalStateException on second visitSource call");
        }
    }
}
