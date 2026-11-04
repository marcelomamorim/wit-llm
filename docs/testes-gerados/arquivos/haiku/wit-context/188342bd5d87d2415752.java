/*
 * @test
 * @summary Checks visitUse argument validation and end-state behavior of CheckModuleAdapter
 * @modules java.base/jdk.internal.org.objectweb.asm.util java.base/jdk.internal.org.objectweb.asm
 * @run main CheckModuleAdapterWitupTest
 */

import jdk.internal.org.objectweb.asm.util.CheckModuleAdapter;

public class CheckModuleAdapterWitupTest {

    public static void main(String[] args) {
        testValidServiceIsAccepted();
        testNullServiceRejected();
        testEmptyServiceRejected();
        testUseAfterEndRejected();
        testUseAfterEndWithNullServiceReportsStateFirst();
    }

    static void testValidServiceIsAccepted() {
        CheckModuleAdapter adapter = new CheckModuleAdapter(null, false);
        adapter.visitUse("com/example/Service");
    }

    static void testNullServiceRejected() {
        final CheckModuleAdapter adapter = new CheckModuleAdapter(null, false);
        expectThrows(IllegalArgumentException.class, () -> adapter.visitUse(null));
    }

    static void testEmptyServiceRejected() {
        final CheckModuleAdapter adapter = new CheckModuleAdapter(null, false);
        expectThrows(IllegalArgumentException.class, () -> adapter.visitUse(""));
    }

    static void testUseAfterEndRejected() {
        final CheckModuleAdapter adapter = new CheckModuleAdapter(null, false);
        adapter.visitEnd();
        expectThrows(IllegalStateException.class,
                () -> adapter.visitUse("com/example/Service"));
    }

    static void testUseAfterEndWithNullServiceReportsStateFirst() {
        final CheckModuleAdapter adapter = new CheckModuleAdapter(null, false);
        adapter.visitEnd();
        expectThrows(IllegalStateException.class, () -> adapter.visitUse(null));
    }

    static void expectThrows(Class<? extends Throwable> type, Runnable r) {
        try {
            r.run();
        } catch (Throwable t) {
            if (type.isInstance(t)) {
                return;
            }
            throw new RuntimeException("Unexpected exception: " + t, t);
        }
        throw new RuntimeException("Expected " + type.getName() + " was not thrown");
    }
}
