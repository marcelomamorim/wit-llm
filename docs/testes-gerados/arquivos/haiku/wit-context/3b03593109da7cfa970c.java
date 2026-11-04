/*
 * @test
 * @summary Checks that Textifier.visitAnnotation returns a non-null nested Textifier for valid inputs
 * @modules java.base/jdk.internal.org.objectweb.asm.util
 * @run main TextifierWitupTest
 */

import jdk.internal.org.objectweb.asm.util.Textifier;

public class TextifierWitupTest {

    public static void main(String[] args) {
        Textifier t = new Textifier();

        Textifier r = t.visitAnnotation("name", "Ljava/lang/Deprecated;");
        check(r != null, "visitAnnotation with name must return non-null");
        check(r != t, "visitAnnotation must return a nested Textifier, not the receiver");

        Textifier r2 = t.visitAnnotation(null, "Ljava/lang/Override;");
        check(r2 != null, "visitAnnotation with null name must return non-null");

        Textifier r3 = r.visitAnnotation("value", "I");
        check(r3 != null, "nested visitAnnotation must return non-null");

        for (int i = 0; i < 50; i++) {
            Textifier ri = t.visitAnnotation("n" + i, "LTest;");
            check(ri != null, "repeated visitAnnotation must return non-null at iteration " + i);
        }

        Textifier fresh = new Textifier().visitAnnotation("a", "LX;");
        check(fresh != null, "visitAnnotation on fresh Textifier must return non-null");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
