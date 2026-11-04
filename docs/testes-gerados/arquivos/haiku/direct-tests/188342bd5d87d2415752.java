/*
 * @test
 * @summary Verify that CheckModuleAdapter.visitUse forwards a valid service name to the delegate ModuleVisitor
 * @modules java.base/jdk.internal.org.objectweb.asm java.base/jdk.internal.org.objectweb.asm.util
 * @run main CheckModuleAdapterWitupTest
 */

import jdk.internal.org.objectweb.asm.ModuleVisitor;
import jdk.internal.org.objectweb.asm.Opcodes;
import jdk.internal.org.objectweb.asm.util.CheckModuleAdapter;

public class CheckModuleAdapterWitupTest {

    public static void main(String[] args) {
        final String[] recorded = new String[1];
        final int[] calls = new int[1];

        ModuleVisitor delegate = new ModuleVisitor(Opcodes.ASM6, null) {
            @Override
            public void visitUse(String service) {
                recorded[0] = service;
                calls[0]++;
            }
        };

        CheckModuleAdapter adapter = new CheckModuleAdapter(delegate, false);

        adapter.visitUse("java/lang/Runnable");

        if (calls[0] != 1) {
            throw new AssertionError("expected 1 delegate call, got " + calls[0]);
        }
        if (!"java/lang/Runnable".equals(recorded[0])) {
            throw new AssertionError("unexpected service: " + recorded[0]);
        }

        adapter.visitUse("java/util/function/Supplier");

        if (calls[0] != 2) {
            throw new AssertionError("expected 2 delegate calls, got " + calls[0]);
        }
        if (!"java/util/function/Supplier".equals(recorded[0])) {
            throw new AssertionError("unexpected service after second call: " + recorded[0]);
        }
    }
}
