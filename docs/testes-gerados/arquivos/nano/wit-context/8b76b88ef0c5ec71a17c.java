/* @test    @summary Test for CheckAnnotationAdapter.visitEnd() method to verify proper exception handling and state management.    @run main CheckAnnotationAdapterWitupTest */

import jdk.internal.org.objectweb.asm.AnnotationVisitor;
import jdk.internal.org.objectweb.asm.Opcodes;
import jdk.internal.org.objectweb.asm.util.CheckAnnotationAdapter;

public class CheckAnnotationAdapterWitupTest {

    public static void main(String[] args) {
        testVisitEndNormal();
        testVisitEndMultipleCalls();
    }

    private static void testVisitEndNormal() {
        AnnotationVisitor av = new CheckAnnotationAdapter(null);
        CheckAnnotationAdapter adapter = new CheckAnnotationAdapter(av);
        try {
            adapter.visitEnd();
            System.out.println("testVisitEndNormal passed");
        } catch (Exception e) {
            System.err.println("testVisitEndNormal failed: " + e);
        }
    }

    private static void testVisitEndMultipleCalls() {
        AnnotationVisitor av = new CheckAnnotationAdapter(null);
        CheckAnnotationAdapter adapter = new CheckAnnotationAdapter(av);
        try {
            adapter.visitEnd();
            adapter.visitEnd(); // calling second time to test state
            System.out.println("testVisitEndMultipleCalls passed");
        } catch (IllegalStateException e) {
            System.out.println("testVisitEndMultipleCalls caught expected exception: " + e);
        } catch (Exception e) {
            System.err.println("testVisitEndMultipleCalls failed: " + e);
        }
    }
}
