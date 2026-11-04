/*
 * @test
 * @summary Test visitSource method of CheckClassAdapter for correct state checks and exception throwing
 * @run main CheckClassAdapterWitupTest
 */

import jdk.internal.org.objectweb.asm.util.CheckClassAdapter;
import jdk.internal.org.objectweb.asm.ClassVisitor;
import jdk.internal.org.objectweb.asm.Opcodes;

public class CheckClassAdapterWitupTest {

    static class DummyClassVisitor extends ClassVisitor {
        DummyClassVisitor() {
            super(Opcodes.ASM7);
        }

        @Override
        public void visitSource(String file, String debug) {
            // no-op override to allow call
        }
    }

    public static void main(String[] args) {
        // Create CheckClassAdapter instance with dummy ClassVisitor
        CheckClassAdapter cca = new CheckClassAdapter(new DummyClassVisitor());

        // First call to visitSource should succeed without exception
        cca.visitSource("TestFile.java", "DebugInfo");

        // Second call to visitSource should throw IllegalStateException because source is already true
        boolean threw = false;
        try {
            cca.visitSource("AnotherFile.java", "MoreDebug");
        } catch (IllegalStateException e) {
            threw = true;
        }
        if (!threw) {
            throw new RuntimeException("Expected IllegalStateException on second visitSource call");
        }
    }
}
