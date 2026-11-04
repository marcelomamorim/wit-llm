/*
 * @test
 * @summary Checks that CheckAnnotationAdapter.visitArray delegates to the wrapped visitor and returns a checking adapter
 * @modules java.base/jdk.internal.org.objectweb.asm java.base/jdk.internal.org.objectweb.asm.util
 * @run main CheckAnnotationAdapterWitupTest
 */

import jdk.internal.org.objectweb.asm.AnnotationVisitor;
import jdk.internal.org.objectweb.asm.Opcodes;
import jdk.internal.org.objectweb.asm.util.CheckAnnotationAdapter;

public class CheckAnnotationAdapterWitupTest {

    static class Recorder extends AnnotationVisitor {
        String arrayName;
        Recorder arrayChild;
        String visitedName;
        Object visitedValue;

        Recorder() {
            super(Opcodes.ASM6, null);
        }

        @Override
        public void visit(String name, Object value) {
            visitedName = name;
            visitedValue = value;
        }

        @Override
        public AnnotationVisitor visitArray(String name) {
            arrayName = name;
            arrayChild = new Recorder();
            return arrayChild;
        }
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) {
        Recorder rec = new Recorder();
        CheckAnnotationAdapter ca = new CheckAnnotationAdapter(rec);
        AnnotationVisitor child = ca.visitArray("values");

        check(child != null, "visitArray must return a non-null visitor");
        check(child instanceof CheckAnnotationAdapter, "visitArray must return a CheckAnnotationAdapter");
        check("values".equals(rec.arrayName), "visitArray must delegate the name to the wrapped visitor");
        check(rec.arrayChild != null, "wrapped visitor must return a child visitor");

        child.visit("x", Integer.valueOf(3));
        check(rec.arrayChild.visitedName != null && rec.arrayChild.visitedName.equals("x"),
              "child adapter must delegate visit to the array's delegate");
        check(Integer.valueOf(3).equals(rec.arrayChild.visitedValue),
              "child adapter must delegate the value to the array's delegate");

        CheckAnnotationAdapter nullDelegate = new CheckAnnotationAdapter(null);
        check(nullDelegate.visitArray("a") != null,
              "visitArray with a null delegate must still return a non-null visitor");
    }
}
