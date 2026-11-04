/*
 * @test
 * @summary Tests CheckAnnotationAdapter.visitArray public behavior
 * @modules java.base/jdk.internal.org.objectweb.asm
 * @modules java.base/jdk.internal.org.objectweb.asm.util
 * @run main CheckAnnotationAdapterWitupTest
 */

import jdk.internal.org.objectweb.asm.AnnotationVisitor;
import jdk.internal.org.objectweb.asm.Opcodes;
import jdk.internal.org.objectweb.asm.util.CheckAnnotationAdapter;

public class CheckAnnotationAdapterWitupTest {

    public static void main(String[] args) {
        testReturnsAdapterForNamedArray();
        testNullNameThrowsIllegalArgument();
        testAfterEndThrowsIllegalState();
        testUnnamedChildAcceptsNullName();
        testDelegateReceivesArrayName();
    }

    static void testReturnsAdapterForNamedArray() {
        CheckAnnotationAdapter cv = new CheckAnnotationAdapter(null);
        AnnotationVisitor child = cv.visitArray("values");
        if (child == null) {
            throw new RuntimeException("visitArray returned null");
        }
    }

    static void testNullNameThrowsIllegalArgument() {
        CheckAnnotationAdapter cv = new CheckAnnotationAdapter(null);
        boolean thrown = false;
        try {
            cv.visitArray(null);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("expected IllegalArgumentException for null name");
        }
    }

    static void testAfterEndThrowsIllegalState() {
        CheckAnnotationAdapter cv = new CheckAnnotationAdapter(null);
        cv.visitEnd();
        boolean thrown = false;
        try {
            cv.visitArray("values");
        } catch (IllegalStateException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("expected IllegalStateException after visitEnd");
        }
    }

    static void testUnnamedChildAcceptsNullName() {
        CheckAnnotationAdapter cv = new CheckAnnotationAdapter(null);
        AnnotationVisitor child = cv.visitArray("values");
        if (child == null) {
            throw new RuntimeException("visitArray returned null");
        }
        child.visitArray(null);
    }

    static void testDelegateReceivesArrayName() {
        final String[] seen = new String[1];
        AnnotationVisitor delegate = new AnnotationVisitor(Opcodes.ASM6) {
            @Override
            public AnnotationVisitor visitArray(final String name) {
                seen[0] = name;
                return null;
            }
        };
        CheckAnnotationAdapter cv = new CheckAnnotationAdapter(delegate);
        cv.visitArray("vals");
        if (!"vals".equals(seen[0])) {
            throw new RuntimeException("delegate did not receive name, got: " + seen[0]);
        }
    }
}
