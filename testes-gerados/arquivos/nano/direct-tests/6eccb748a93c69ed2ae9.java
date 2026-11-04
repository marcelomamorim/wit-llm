/* @test    @summary Test CheckFieldAdapter.visitEnd() method for correct behavior    @run main CheckFieldAdapterWitupTest */

import jdk.internal.org.objectweb.asm.FieldVisitor;
import jdk.internal.org.objectweb.asm.util.CheckFieldAdapter;

public class CheckFieldAdapterWitupTest {

    public static void main(String[] args) {
        testVisitEndSetsEndFlag();
        testVisitEndCallsSuperVisitEnd();
    }

    private static void testVisitEndSetsEndFlag() {
        // Create a dummy FieldVisitor to pass to CheckFieldAdapter
        FieldVisitor dummyVisitor = new FieldVisitor(0) {
            boolean visitedEnd = false;
            @Override
            public void visitEnd() {
                visitedEnd = true;
            }
        };
        CheckFieldAdapter adapter = new CheckFieldAdapter(dummyVisitor);
        adapter.visitEnd();
        // Verify that the end flag is set to true after visitEnd()
        if (!adapter.end) {
            throw new AssertionError("visitEnd() did not set end flag to true");
        }
    }

    private static void testVisitEndCallsSuperVisitEnd() {
        final boolean[] superVisited = {false};
        FieldVisitor dummyVisitor = new FieldVisitor(0) {
            @Override
            public void visitEnd() {
                superVisited[0] = true;
            }
        };
        CheckFieldAdapter adapter = new CheckFieldAdapter(dummyVisitor);
        adapter.visitEnd();
        if (!superVisited[0]) {
            throw new AssertionError("Super visitEnd() was not called");
        }
    }
}
