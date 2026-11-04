/*
 * @test
 * @summary Deterministic tests for CheckModuleAdapter.visitUse(String) to check exception and normal behavior
 * @run main CheckModuleAdapterWitupTest
 */

import jdk.internal.org.objectweb.asm.util.CheckModuleAdapter;
import jdk.internal.org.objectweb.asm.ModuleVisitor;
import jdk.internal.org.objectweb.asm.Opcodes;

public class CheckModuleAdapterWitupTest {

    static class DummyModuleVisitor extends ModuleVisitor {
        boolean visitedUse = false;
        String lastService = null;

        public DummyModuleVisitor() {
            super(Opcodes.ASM6);
        }

        @Override
        public void visitUse(String service) {
            visitedUse = true;
            lastService = service;
        }
    }

    public static void main(String[] args) {
        testVisitUseThrowsIfEnd();
        testVisitUseThrowsIfNullOrEmpty();
        testVisitUseNormal();
    }

    private static void testVisitUseThrowsIfEnd() {
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

        boolean thrown = false;
        try {
            cma.visitUse("ValidService");
        } catch (IllegalStateException ise) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected IllegalStateException when end is true");
        }
    }

    private static void testVisitUseThrowsIfNullOrEmpty() {
        DummyModuleVisitor dmv = new DummyModuleVisitor();
        CheckModuleAdapter cma = new CheckModuleAdapter(dmv, false);

        // end is false by default

        // Test null service
        boolean thrownNull = false;
        try {
            cma.visitUse(null);
        } catch (IllegalArgumentException iae) {
            thrownNull = true;
        }
        if (!thrownNull) {
            throw new AssertionError("Expected IllegalArgumentException when service is null");
        }

        // Test empty string service
        boolean thrownEmpty = false;
        try {
            cma.visitUse("");
        } catch (IllegalArgumentException iae) {
            thrownEmpty = true;
        }
        if (!thrownEmpty) {
            throw new AssertionError("Expected IllegalArgumentException when service is empty");
        }
    }

    private static void testVisitUseNormal() {
        DummyModuleVisitor dmv = new DummyModuleVisitor();
        CheckModuleAdapter cma = new CheckModuleAdapter(dmv, false);

        String serviceName = "ValidService";
        cma.visitUse(serviceName);

        if (!dmv.visitedUse) {
            throw new AssertionError("Expected visitUse to be called on delegate ModuleVisitor");
        }
        if (!serviceName.equals(dmv.lastService)) {
            throw new AssertionError("Expected service name to be passed to delegate ModuleVisitor");
        }
    }
}
