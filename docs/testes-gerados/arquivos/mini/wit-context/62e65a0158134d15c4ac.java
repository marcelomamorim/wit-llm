/*
 * @test
 * @summary Test visitRequire method of CheckModuleAdapter for exception and normal behavior
 * @run main CheckModuleAdapterWitupTest
 */

import jdk.internal.org.objectweb.asm.util.CheckModuleAdapter;
import jdk.internal.org.objectweb.asm.ModuleVisitor;
import jdk.internal.org.objectweb.asm.Opcodes;

public class CheckModuleAdapterWitupTest {

    static class DummyModuleVisitor extends ModuleVisitor {
        boolean visitedRequire = false;
        String lastModule = null;
        int lastAccess = 0;
        String lastVersion = null;

        public DummyModuleVisitor() {
            super(Opcodes.ASM6);
        }

        @Override
        public void visitRequire(String module, int access, String version) {
            visitedRequire = true;
            lastModule = module;
            lastAccess = access;
            lastVersion = version;
        }
    }

    public static void main(String[] args) {
        testVisitRequireThrowsIfEnd();
        testVisitRequireThrowsIfModuleNull();
        testVisitRequireNormalCall();
    }

    private static void testVisitRequireThrowsIfEnd() {
        DummyModuleVisitor dmv = new DummyModuleVisitor();
        CheckModuleAdapter cma = new CheckModuleAdapter(dmv, false);

        // Use reflection to set private field 'end' to true to simulate end state
        try {
            java.lang.reflect.Field endField = CheckModuleAdapter.class.getDeclaredField("end");
            endField.setAccessible(true);
            endField.setBoolean(cma, true);
        } catch (Exception e) {
            throw new RuntimeException("Reflection failed", e);
        }

        try {
            cma.visitRequire("moduleName", 0, "1.0");
            throw new RuntimeException("Expected IllegalStateException not thrown");
        } catch (IllegalStateException ise) {
            // expected
        }
    }

    private static void testVisitRequireThrowsIfModuleNull() {
        DummyModuleVisitor dmv = new DummyModuleVisitor();
        CheckModuleAdapter cma = new CheckModuleAdapter(dmv, false);

        try {
            cma.visitRequire(null, 0, "1.0");
            throw new RuntimeException("Expected IllegalArgumentException not thrown");
        } catch (IllegalArgumentException iae) {
            // expected
        }
    }

    private static void testVisitRequireNormalCall() {
        DummyModuleVisitor dmv = new DummyModuleVisitor();
        CheckModuleAdapter cma = new CheckModuleAdapter(dmv, false);

        String moduleName = "java.base";
        int access = Opcodes.ACC_TRANSITIVE;
        String version = "11";

        cma.visitRequire(moduleName, access, version);

        if (!dmv.visitedRequire) {
            throw new RuntimeException("visitRequire was not delegated to ModuleVisitor");
        }
        if (!moduleName.equals(dmv.lastModule)) {
            throw new RuntimeException("Module name mismatch");
        }
        if (access != dmv.lastAccess) {
            throw new RuntimeException("Access flags mismatch");
        }
        if (!version.equals(dmv.lastVersion)) {
            throw new RuntimeException("Version mismatch");
        }
    }
}
