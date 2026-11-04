/*
 * @test
 * @summary Test visitRequire method of CheckModuleAdapter for valid and invalid inputs
 * @run main CheckModuleAdapterWitupTest
 */

import jdk.internal.org.objectweb.asm.util.CheckModuleAdapter;
import jdk.internal.org.objectweb.asm.ModuleVisitor;
import jdk.internal.org.objectweb.asm.Opcodes;

public class CheckModuleAdapterWitupTest {

    static class DummyModuleVisitor extends ModuleVisitor {
        boolean visited = false;
        String visitedModule = null;
        int visitedAccess = -1;
        String visitedVersion = null;

        public DummyModuleVisitor() {
            super(Opcodes.ASM6);
        }

        @Override
        public void visitRequire(String module, int access, String version) {
            visited = true;
            visitedModule = module;
            visitedAccess = access;
            visitedVersion = version;
        }
    }

    public static void main(String[] args) {
        testValidVisitRequire();
        testVisitRequireNullModule();
        testVisitRequireDuplicateModule();
        testVisitRequireInvalidAccess();
    }

    private static void testValidVisitRequire() {
        DummyModuleVisitor dmv = new DummyModuleVisitor();
        CheckModuleAdapter cma = new CheckModuleAdapter(dmv, false);
        String moduleName = "java.base";
        int access = Opcodes.ACC_STATIC_PHASE;
        String version = "1.0";

        cma.visitRequire(moduleName, access, version);

        if (!dmv.visited) {
            throw new RuntimeException("visitRequire was not delegated to super visitor");
        }
        if (!moduleName.equals(dmv.visitedModule)) {
            throw new RuntimeException("Module name mismatch");
        }
        if (access != dmv.visitedAccess) {
            throw new RuntimeException("Access flags mismatch");
        }
        if (!version.equals(dmv.visitedVersion)) {
            throw new RuntimeException("Version mismatch");
        }
    }

    private static void testVisitRequireNullModule() {
        DummyModuleVisitor dmv = new DummyModuleVisitor();
        CheckModuleAdapter cma = new CheckModuleAdapter(dmv, false);
        try {
            cma.visitRequire(null, 0, null);
            throw new RuntimeException("Expected IllegalArgumentException for null module");
        } catch (IllegalArgumentException e) {
            if (!"require cannot be null".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected exception message: " + e.getMessage());
            }
        }
    }

    private static void testVisitRequireDuplicateModule() {
        DummyModuleVisitor dmv = new DummyModuleVisitor();
        CheckModuleAdapter cma = new CheckModuleAdapter(dmv, false);
        String moduleName = "java.sql";
        int access = Opcodes.ACC_STATIC_PHASE;

        cma.visitRequire(moduleName, access, null);

        try {
            cma.visitRequire(moduleName, access, null);
            throw new RuntimeException("Expected IllegalStateException for duplicate module");
        } catch (IllegalStateException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.contains("already declared")) {
                throw new RuntimeException("Unexpected exception message: " + msg);
            }
        }
    }

    private static void testVisitRequireInvalidAccess() {
        DummyModuleVisitor dmv = new DummyModuleVisitor();
        CheckModuleAdapter cma = new CheckModuleAdapter(dmv, false);
        String moduleName = "java.xml";
        int invalidAccess = Opcodes.ACC_PUBLIC; // Not allowed according to checkAccess

        try {
            cma.visitRequire(moduleName, invalidAccess, null);
            throw new RuntimeException("Expected IllegalArgumentException for invalid access flags");
        } catch (IllegalArgumentException e) {
            // Expected
        }
    }
}
