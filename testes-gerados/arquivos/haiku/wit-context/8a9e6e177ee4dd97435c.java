/*
 * @test
 * @summary Verifies that TraceModuleVisitor.visitEnd() fails when no Printer is configured
 * @modules java.base/jdk.internal.org.objectweb.asm.util
 * @run main TraceModuleVisitorWitupTest
 */

import jdk.internal.org.objectweb.asm.util.TraceModuleVisitor;

public class TraceModuleVisitorWitupTest {

    public static void main(String[] args) {
        TraceModuleVisitor withoutDelegate = new TraceModuleVisitor(null, null);
        boolean thrown = false;
        try {
            withoutDelegate.visitEnd();
        } catch (NullPointerException expected) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("visitEnd() with null Printer must throw NullPointerException");
        }

        TraceModuleVisitor singleArg = new TraceModuleVisitor(null);
        thrown = false;
        try {
            singleArg.visitEnd();
        } catch (NullPointerException expected) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("visitEnd() with null Printer (single-arg constructor) must throw NullPointerException");
        }
    }
}
