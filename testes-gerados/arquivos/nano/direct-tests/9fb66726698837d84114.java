/*
 * @test
 * @summary Test CheckClassAdapter.visitSource method for correct behavior.
 * @run main CheckClassAdapterWitupTest
 */

import java.io.IOException;
import jdk.internal.org.objectweb.asm.util.CheckClassAdapter;

public class CheckClassAdapterWitupTest {

    public static void main(String[] args) {
        testVisitSourceCalledOnce();
        testVisitSourceMultipleCalls();
    }

    private static void testVisitSourceCalledOnce() {
        CheckClassAdapter adapter = new CheckClassAdapter(new DummyClassVisitor());
        try {
            adapter.visitSource("sourceFile.java", "debugInfo");
            System.out.println("testVisitSourceCalledOnce passed.");
        } catch (Exception e) {
            System.err.println("testVisitSourceCalledOnce failed: " + e);
        }
    }

    private static void testVisitSourceMultipleCalls() {
        CheckClassAdapter adapter = new CheckClassAdapter(new DummyClassVisitor());
        try {
            adapter.visitSource("sourceFile.java", "debugInfo");
            adapter.visitSource("anotherSource.java", "moreDebug");
            System.err.println("testVisitSourceMultipleCalls failed: no exception thrown.");
        } catch (IllegalStateException e) {
            System.out.println("testVisitSourceMultipleCalls passed.");
        } catch (Exception e) {
            System.err.println("testVisitSourceMultipleCalls failed: " + e);
        }
    }

    private static class DummyClassVisitor extends jdk.internal.org.objectweb.asm.ClassVisitor {
        public DummyClassVisitor() {
            super(0);
        }
    }
}
