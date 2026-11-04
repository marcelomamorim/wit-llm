/*
 * @test
 * @summary Test CheckClassAdapter.visitEnd() behavior including IllegalStateException triggers
 * @run main CheckClassAdapterWitupTest
 */

import jdk.internal.org.objectweb.asm.util.CheckClassAdapter;
import jdk.internal.org.objectweb.asm.ClassVisitor;

public class CheckClassAdapterWitupTest {

    static class DummyClassVisitor extends ClassVisitor {
        public boolean visitEndCalled = false;
        public DummyClassVisitor() {
            super(0);
        }
        @Override
        public void visitEnd() {
            visitEndCalled = true;
        }
    }

    public static void main(String[] args) {
        testVisitEndThrowsIfNotStarted();
        testVisitEndThrowsIfAlreadyEndedAndStarted();
        testVisitEndNormalFlow();
    }

    private static void testVisitEndThrowsIfNotStarted() {
        DummyClassVisitor dummy = new DummyClassVisitor();
        CheckClassAdapter cca = new CheckClassAdapter(dummy);
        // forcibly set start to false (default) and end to false (default)
        // visitEnd() should throw IllegalStateException because !start
        try {
            cca.visitEnd();
            throw new AssertionError("Expected IllegalStateException when start is false");
        } catch (IllegalStateException ise) {
            // expected
        }
    }

    private static void testVisitEndThrowsIfAlreadyEndedAndStarted() {
        DummyClassVisitor dummy = new DummyClassVisitor();
        CheckClassAdapter cca = new CheckClassAdapter(dummy);
        // forcibly set start and end to true to simulate end called twice
        // We must use reflection because start and end are private fields
        try {
            java.lang.reflect.Field startField = CheckClassAdapter.class.getDeclaredField("start");
            java.lang.reflect.Field endField = CheckClassAdapter.class.getDeclaredField("end");
            startField.setAccessible(true);
            endField.setAccessible(true);
            startField.setBoolean(cca, true);
            endField.setBoolean(cca, true);
        } catch (Exception e) {
            throw new RuntimeException("Reflection setup failed", e);
        }
        try {
            cca.visitEnd();
            throw new AssertionError("Expected IllegalStateException when end and start are true");
        } catch (IllegalStateException ise) {
            // expected
        }
    }

    private static void testVisitEndNormalFlow() {
        DummyClassVisitor dummy = new DummyClassVisitor();
        CheckClassAdapter cca = new CheckClassAdapter(dummy);
        // forcibly set start to true and end to false
        try {
            java.lang.reflect.Field startField = CheckClassAdapter.class.getDeclaredField("start");
            java.lang.reflect.Field endField = CheckClassAdapter.class.getDeclaredField("end");
            startField.setAccessible(true);
            endField.setAccessible(true);
            startField.setBoolean(cca, true);
            endField.setBoolean(cca, false);
        } catch (Exception e) {
            throw new RuntimeException("Reflection setup failed", e);
        }
        cca.visitEnd();
        if (!dummy.visitEndCalled) {
            throw new AssertionError("Expected super.visitEnd() to be called");
        }
    }
}
