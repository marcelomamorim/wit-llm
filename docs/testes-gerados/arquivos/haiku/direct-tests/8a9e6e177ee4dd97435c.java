/*
 * @test
 * @summary Verify TraceModuleVisitor.visitEnd dereferences the printer and fails with NPE when the printer is null
 * @modules java.base/jdk.internal.org.objectweb.asm
 * @modules java.base/jdk.internal.org.objectweb.asm.util
 * @run main TraceModuleVisitorWitupTest
 */

import jdk.internal.org.objectweb.asm.util.Printer;
import jdk.internal.org.objectweb.asm.util.TraceModuleVisitor;

public class TraceModuleVisitorWitupTest {

    public static void main(String[] args) {
        testSingleArgConstructorStoresNullPrinter();
        testTwoArgConstructorStoresNullPrinter();
    }

    static void testSingleArgConstructorStoresNullPrinter() {
        TraceModuleVisitor tmv = new TraceModuleVisitor((Printer) null);
        if (tmv.p != null) {
            throw new RuntimeException("Expected public field p to be null");
        }
        try {
            tmv.visitEnd();
            throw new RuntimeException("Expected NullPointerException from visitEnd");
        } catch (NullPointerException expected) {
            // p.visitModuleEnd() dereferences the null printer
        }
    }

    static void testTwoArgConstructorStoresNullPrinter() {
        TraceModuleVisitor tmv = new TraceModuleVisitor(null, (Printer) null);
        if (tmv.p != null) {
            throw new RuntimeException("Expected public field p to be null");
        }
        try {
            tmv.visitEnd();
            throw new RuntimeException("Expected NullPointerException from visitEnd");
        } catch (NullPointerException expected) {
            // p.visitModuleEnd() dereferences the null printer
        }
    }
}
