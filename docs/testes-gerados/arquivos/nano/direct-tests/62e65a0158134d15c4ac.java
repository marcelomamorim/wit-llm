/* @test    @summary Test CheckModuleAdapter's visitRequire method for null module argument    @run main CheckModuleAdapterWitupTest */

import jdk.internal.org.objectweb.asm.util.CheckModuleAdapter;
import jdk.internal.org.objectweb.asm.ModuleVisitor;
import jdk.internal.org.objectweb.asm.Opcodes;

public class CheckModuleAdapterWitupTest {

    public static void main(String[] args) {
        testVisitRequireNullModule();
        testVisitRequireValidModule();
    }

    static void testVisitRequireNullModule() {
        ModuleVisitor dummyVisitor = new ModuleVisitor(Opcodes.ASM6) {};
        CheckModuleAdapter adapter = new CheckModuleAdapter(dummyVisitor, false);
        boolean exceptionThrown = false;
        try {
            adapter.visitRequire(null, Opcodes.ACC_STATIC_PHASE, "1.0");
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected IllegalArgumentException for null module");
        }
    }

    static void testVisitRequireValidModule() {
        class DummyVisitor extends ModuleVisitor {
            String visitedModule;
            int visitedAccess;
            String visitedVersion;
            DummyVisitor() {
                super(Opcodes.ASM6);
            }
            @Override
            public void visitRequire(String module, int access, String version) {
                this.visitedModule = module;
                this.visitedAccess = access;
                this.visitedVersion = version;
            }
        }
        DummyVisitor dummy = new DummyVisitor();
        CheckModuleAdapter adapter = new CheckModuleAdapter(dummy, false);
        String moduleName = "com.example.module";
        String version = "2.0";
        adapter.visitRequire(moduleName, Opcodes.ACC_TRANSITIVE, version);
        if (!moduleName.equals(dummy.visitedModule)) {
            throw new AssertionError("Module name mismatch");
        }
        if (dummy.visitedAccess != Opcodes.ACC_TRANSITIVE) {
            throw new AssertionError("Access flags mismatch");
        }
        if (!version.equals(dummy.visitedVersion)) {
            throw new AssertionError("Version mismatch");
        }
    }
}
