/*
 * @test
 * @summary Test CheckAnnotationAdapter's visitArray method for correct behavior.
 * @run main CheckAnnotationAdapterWitupTest
 */

import jdk.internal.org.objectweb.asm.AnnotationVisitor;
import jdk.internal.org.objectweb.asm.Opcodes;
import jdk.internal.org.objectweb.asm.Type;
import jdk.internal.org.objectweb.asm.util.CheckAnnotationAdapter;

public class CheckAnnotationAdapterWitupTest {

    public static void main(String[] args) {
        testVisitArrayWithNull();
        testVisitArrayWithName();
        testVisitArrayWithEmptyName();
    }

    private static void testVisitArrayWithNull() {
        AnnotationVisitor av = null;
        CheckAnnotationAdapter adapter = new CheckAnnotationAdapter(av);
        try {
            AnnotationVisitor result = adapter.visitArray(null);
            if (result == null) {
                System.out.println("testVisitArrayWithNull passed");
            } else {
                System.out.println("testVisitArrayWithNull failed: result should be null");
            }
        } catch (Exception e) {
            System.out.println("testVisitArrayWithNull failed with exception: " + e);
        }
    }

    private static void testVisitArrayWithName() {
        AnnotationVisitor av = new AnnotationVisitor(Opcodes.ASM6) {};
        CheckAnnotationAdapter adapter = new CheckAnnotationAdapter(av);
        String name = "testName";
        try {
            AnnotationVisitor result = adapter.visitArray(name);
            if (result != null) {
                System.out.println("testVisitArrayWithName passed");
            } else {
                System.out.println("testVisitArrayWithName failed: result should not be null");
            }
        } catch (Exception e) {
            System.out.println("testVisitArrayWithName failed with exception: " + e);
        }
    }

    private static void testVisitArrayWithEmptyName() {
        AnnotationVisitor av = new AnnotationVisitor(Opcodes.ASM6) {};
        CheckAnnotationAdapter adapter = new CheckAnnotationAdapter(av);
        String name = "";
        try {
            AnnotationVisitor result = adapter.visitArray(name);
            if (result != null) {
                System.out.println("testVisitArrayWithEmptyName passed");
            } else {
                System.out.println("testVisitArrayWithEmptyName failed: result should not be null");
            }
        } catch (Exception e) {
            System.out.println("testVisitArrayWithEmptyName failed with exception: " + e);
        }
    }
}
