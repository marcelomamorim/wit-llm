/*
 * @test
 * @summary Tests for jdk.internal.org.objectweb.asm.util.ASMifier
 * @run main ASMifierWitupTest
 */

import java.io.PrintWriter;
import jdk.internal.org.objectweb.asm.util.ASMifier;

public class ASMifierWitupTest {

    public static void main(String[] args) {
        testVisitAnnotationNormal();
        testVisitAnnotationNullName();
        testVisitAnnotationNullDesc();
    }

    public static void testVisitAnnotationNormal() {
        ASMifier asmifier = new ASMifier();
        try {
            ASMifier result = asmifier.visitAnnotation("TestName", "TestDesc");
            if (result == null) {
                throw new AssertionError("Expected non-null ASMifier instance");
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in testVisitAnnotationNormal", e);
        }
    }

    public static void testVisitAnnotationNullName() {
        ASMifier asmifier = new ASMifier();
        try {
            ASMifier result = asmifier.visitAnnotation(null, "TestDesc");
            if (result == null) {
                throw new AssertionError("Expected non-null ASMifier instance with null name");
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in testVisitAnnotationNullName", e);
        }
    }

    public static void testVisitAnnotationNullDesc() {
        ASMifier asmifier = new ASMifier();
        try {
            ASMifier result = asmifier.visitAnnotation("TestName", null);
            if (result == null) {
                throw new AssertionError("Expected non-null ASMifier instance with null desc");
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in testVisitAnnotationNullDesc", e);
        }
    }
}
