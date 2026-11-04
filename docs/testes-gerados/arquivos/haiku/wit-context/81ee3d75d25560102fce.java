/*
 * @test
 * @summary Witup-generated checks for CheckFieldAdapter.visitAttribute exception paths
 * @modules java.base/jdk.internal.org.objectweb.asm java.base/jdk.internal.org.objectweb.asm.util
 * @run main CheckFieldAdapterWitupTest
 */

import jdk.internal.org.objectweb.asm.FieldVisitor;
import jdk.internal.org.objectweb.asm.util.CheckFieldAdapter;

public class CheckFieldAdapterWitupTest {

    public static void main(String[] args) {
        testNullAttributeBeforeEndThrowsIllegalArgument();
        testNullAttributeAfterEndThrowsIllegalState();
    }

    private static void testNullAttributeBeforeEndThrowsIllegalArgument() {
        CheckFieldAdapter cfa = new CheckFieldAdapter((FieldVisitor) null);
        try {
            cfa.visitAttribute(null);
        } catch (IllegalArgumentException e) {
            return;
        }
        throw new AssertionError("Expected IllegalArgumentException for null attribute before visitEnd");
    }

    private static void testNullAttributeAfterEndThrowsIllegalState() {
        CheckFieldAdapter cfa = new CheckFieldAdapter((FieldVisitor) null);
        cfa.visitEnd();
        try {
            cfa.visitAttribute(null);
        } catch (IllegalStateException e) {
            return;
        }
        throw new AssertionError("Expected IllegalStateException when visitAttribute is called after visitEnd");
    }
}
