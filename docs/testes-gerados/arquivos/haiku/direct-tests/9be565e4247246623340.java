/*
 * @test
 * @summary Verify that CheckMethodAdapter.visitEnd completes normally for a fresh adapter
 * @modules java.base/jdk.internal.org.objectweb.asm.util
 * @run main CheckMethodAdapterWitupTest
 */

import java.util.HashMap;
import java.util.Map;

import jdk.internal.org.objectweb.asm.Label;
import jdk.internal.org.objectweb.asm.util.CheckMethodAdapter;

public class CheckMethodAdapterWitupTest {

    static int failures = 0;

    static void check(boolean condition, String message) {
        if (!condition) {
            failures++;
            System.err.println("FAILED: " + message);
        }
    }

    static void testVisitEndWithNullDelegate() {
        CheckMethodAdapter cma = new CheckMethodAdapter(null);
        try {
            cma.visitEnd();
        } catch (RuntimeException e) {
            check(false, "visitEnd with null delegate threw " + e);
        }
    }

    static void testVisitEndWithEmptyLabelMap() {
        Map<Label, Integer> labels = new HashMap<>();
        CheckMethodAdapter cma = new CheckMethodAdapter(null, labels);
        try {
            cma.visitEnd();
        } catch (RuntimeException e) {
            check(false, "visitEnd with empty label map threw " + e);
        }
    }

    public static void main(String[] args) {
        testVisitEndWithNullDelegate();
        testVisitEndWithEmptyLabelMap();
        if (failures > 0) {
            throw new RuntimeException(failures + " check(s) failed");
        }
    }
}
