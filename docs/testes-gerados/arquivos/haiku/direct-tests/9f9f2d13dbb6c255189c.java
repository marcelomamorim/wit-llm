/*
 * @test
 * @summary Verify that RemappingClassAdapter.visitModule always throws a RuntimeException with the deprecation message
 * @modules java.base/jdk.internal.org.objectweb.asm.commons
 * @run main RemappingClassAdapterWitupTest
 */

import jdk.internal.org.objectweb.asm.commons.RemappingClassAdapter;

public class RemappingClassAdapterWitupTest {

    private static final String EXPECTED_MESSAGE =
            "RemappingClassAdapter is deprecated, use ClassRemapper instead";

    public static void main(String[] args) {
        testVisitModuleThrowsWithDeprecationMessage();
        testVisitModuleThrowsForNullName();
        testVisitModuleThrowsForZeroFlagsAndNullVersion();
        System.out.println("PASSED");
    }

    private static void testVisitModuleThrowsWithDeprecationMessage() {
        RemappingClassAdapter adapter = new RemappingClassAdapter(null, null);
        RuntimeException ex = null;
        try {
            adapter.visitModule("com/example/mod", 0, "1.0");
        } catch (RuntimeException e) {
            ex = e;
        }
        if (ex == null) {
            throw new AssertionError("visitModule did not throw RuntimeException");
        }
        if (!EXPECTED_MESSAGE.equals(ex.getMessage())) {
            throw new AssertionError("Unexpected message: " + ex.getMessage());
        }
    }

    private static void testVisitModuleThrowsForNullName() {
        RemappingClassAdapter adapter = new RemappingClassAdapter(null, null);
        RuntimeException ex = null;
        try {
            adapter.visitModule(null, 0, null);
        } catch (RuntimeException e) {
            ex = e;
        }
        if (ex == null) {
            throw new AssertionError("visitModule with null arguments did not throw");
        }
        if (!EXPECTED_MESSAGE.equals(ex.getMessage())) {
            throw new AssertionError("Unexpected message: " + ex.getMessage());
        }
    }

    private static void testVisitModuleThrowsForZeroFlagsAndNullVersion() {
        RemappingClassAdapter adapter = new RemappingClassAdapter(null, null);
        boolean thrown = false;
        try {
            adapter.visitModule("m", 0x8000, null);
        } catch (RuntimeException e) {
            thrown = EXPECTED_MESSAGE.equals(e.getMessage());
        }
        if (!thrown) {
            throw new AssertionError("visitModule with flags/null version did not throw expected exception");
        }
    }
}
