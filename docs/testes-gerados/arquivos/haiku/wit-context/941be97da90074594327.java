/*
 * @test
 * @summary Verifies CheckModuleAdapter.visitEnd succeeds once and rejects a second call with IllegalStateException
 * @modules java.base/jdk.internal.org.objectweb.asm java.base/jdk.internal.org.objectweb.asm.util
 * @run main CheckModuleAdapterWitupTest
 */

import jdk.internal.org.objectweb.asm.util.CheckModuleAdapter;

public class CheckModuleAdapterWitupTest {

    public static void main(String[] args) {
        testVisitEndOnceClosed();
        testVisitEndOnceOpen();
        testVisitEndTwiceThrowsClosed();
        testVisitEndTwiceThrowsOpen();
    }

    static void testVisitEndOnceClosed() {
        CheckModuleAdapter cma = new CheckModuleAdapter(null, false);
        cma.visitEnd();
    }

    static void testVisitEndOnceOpen() {
        CheckModuleAdapter cma = new CheckModuleAdapter(null, true);
        cma.visitEnd();
    }

    static void testVisitEndTwiceThrowsClosed() {
        CheckModuleAdapter cma = new CheckModuleAdapter(null, false);
        cma.visitEnd();
        expectIllegalState(cma);
    }

    static void testVisitEndTwiceThrowsOpen() {
        CheckModuleAdapter cma = new CheckModuleAdapter(null, true);
        cma.visitEnd();
        expectIllegalState(cma);
    }

    static void expectIllegalState(CheckModuleAdapter cma) {
        try {
            cma.visitEnd();
        } catch (IllegalStateException expected) {
            return;
        }
        throw new RuntimeException("Expected IllegalStateException on second visitEnd()");
    }
}
