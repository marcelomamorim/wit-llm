/*
 * @test
 * @summary Checks CheckModuleAdapter.visitRequire argument validation, state checks and valid delegation
 * @modules java.base/jdk.internal.org.objectweb.asm java.base/jdk.internal.org.objectweb.asm.util
 * @run main CheckModuleAdapterWitupTest
 */

import jdk.internal.org.objectweb.asm.ModuleVisitor;
import jdk.internal.org.objectweb.asm.Opcodes;
import jdk.internal.org.objectweb.asm.util.CheckModuleAdapter;

public class CheckModuleAdapterWitupTest {

    public static void main(String[] args) {
        testNullModuleThrowsIllegalArgumentException();
        testRequireAfterVisitEndThrowsIllegalStateException();
        testValidRequireIsAccepted();
    }

    private static CheckModuleAdapter newAdapter() {
        ModuleVisitor delegate = new ModuleVisitor(Opcodes.ASM6, null) {};
        return new CheckModuleAdapter(delegate, false);
    }

    private static void testNullModuleThrowsIllegalArgumentException() {
        CheckModuleAdapter adapter = newAdapter();
        boolean thrown = false;
        try {
            adapter.visitRequire(null, 0, null);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("expected IllegalArgumentException for null module");
        }
    }

    private static void testRequireAfterVisitEndThrowsIllegalStateException() {
        CheckModuleAdapter adapter = newAdapter();
        adapter.visitEnd();
        boolean thrown = false;
        try {
            adapter.visitRequire("java.base", 0, null);
        } catch (IllegalStateException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("expected IllegalStateException after visitEnd");
        }
    }

    private static void testValidRequireIsAccepted() {
        CheckModuleAdapter adapter = newAdapter();
        adapter.visitRequire("java.base", 0, null);
    }
}
