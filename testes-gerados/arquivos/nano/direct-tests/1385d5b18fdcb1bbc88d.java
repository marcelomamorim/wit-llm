/*
 * @test
 * @summary Test visitAnnotationDefault method of CheckMethodAdapter
 * @run main CheckMethodAdapterWitupTest
 */

import jdk.internal.org.objectweb.asm.util.CheckMethodAdapter;
import jdk.internal.org.objectweb.asm.AnnotationVisitor;

public class CheckMethodAdapterWitupTest {

    public static void main(String[] args) {
        testVisitAnnotationDefault();
    }

    public static void testVisitAnnotationDefault() {
        CheckMethodAdapter adapter = new CheckMethodAdapter(0, null, null, null, null, null);
        try {
            AnnotationVisitor result = adapter.visitAnnotationDefault();
            if (result == null) {
                System.out.println("visitAnnotationDefault returned null as expected.");
            } else {
                System.out.println("visitAnnotationDefault returned a non-null value.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}
