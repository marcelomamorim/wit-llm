/*
 * @test
 * @summary Test visitAnnotationDefault method of CheckMethodAdapter for correct behavior and exception on endMethod
 * @run main CheckMethodAdapterWitupTest
 */

import jdk.internal.org.objectweb.asm.util.CheckMethodAdapter;
import jdk.internal.org.objectweb.asm.MethodVisitor;
import jdk.internal.org.objectweb.asm.AnnotationVisitor;

public class CheckMethodAdapterWitupTest {

    static class DummyMethodVisitor extends MethodVisitor {
        DummyMethodVisitor() {
            super(0);
        }
        @Override
        public AnnotationVisitor visitAnnotationDefault() {
            return null;
        }
    }

    public static void main(String[] args) {
        // Create a CheckMethodAdapter instance with a dummy MethodVisitor
        CheckMethodAdapter cma = new CheckMethodAdapter(new DummyMethodVisitor());

        // The method visitAnnotationDefault calls checkEndMethod which throws IllegalStateException if endMethod is true
        // Initially, endMethod is false, so visitAnnotationDefault should return a non-null AnnotationVisitor
        AnnotationVisitor av = cma.visitAnnotationDefault();
        if (av == null) {
            throw new AssertionError("visitAnnotationDefault returned null when endMethod is false");
        }

        // Now forcibly set endMethod to true via reflection to simulate the guard condition
        // Reflection is used because endMethod is private and no public setter is available
        try {
            java.lang.reflect.Field endMethodField = CheckMethodAdapter.class.getDeclaredField("endMethod");
            endMethodField.setAccessible(true);
            endMethodField.setBoolean(cma, true);
        } catch (Exception e) {
            throw new AssertionError("Failed to set endMethod field via reflection", e);
        }

        // Now visitAnnotationDefault should throw IllegalStateException
        boolean threw = false;
        try {
            cma.visitAnnotationDefault();
        } catch (IllegalStateException ise) {
            threw = true;
        } catch (Throwable t) {
            throw new AssertionError("Unexpected exception type thrown", t);
        }
        if (!threw) {
            throw new AssertionError("visitAnnotationDefault did not throw IllegalStateException when endMethod is true");
        }
    }
}
