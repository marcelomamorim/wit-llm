/*
 * @test
 * @summary Verifies that CheckMethodAdapter.visitAnnotationDefault returns a non-null AnnotationVisitor
 * @modules java.base/jdk.internal.org.objectweb.asm.util
 * @run main CheckMethodAdapterWitupTest
 */

import jdk.internal.org.objectweb.asm.AnnotationVisitor;
import jdk.internal.org.objectweb.asm.MethodVisitor;
import jdk.internal.org.objectweb.asm.util.CheckMethodAdapter;

public class CheckMethodAdapterWitupTest {

    public static void main(String[] args) {
        testReturnsNonNullWithNullDelegate();
        testRepeatedCallsReturnNonNull();
        System.out.println("PASSED");
    }

    static void testReturnsNonNullWithNullDelegate() {
        CheckMethodAdapter cma = new CheckMethodAdapter((MethodVisitor) null);
        AnnotationVisitor av = cma.visitAnnotationDefault();
        if (av == null) {
            throw new RuntimeException("visitAnnotationDefault returned null");
        }
    }

    static void testRepeatedCallsReturnNonNull() {
        CheckMethodAdapter cma = new CheckMethodAdapter((MethodVisitor) null);
        AnnotationVisitor first = cma.visitAnnotationDefault();
        AnnotationVisitor second = cma.visitAnnotationDefault();
        if (first == null || second == null) {
            throw new RuntimeException("repeated visitAnnotationDefault returned null");
        }
    }
}
