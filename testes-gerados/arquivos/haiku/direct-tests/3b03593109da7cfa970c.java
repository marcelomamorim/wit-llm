/*
 * @test
 * @summary Verify observable output of Textifier.visitAnnotation
 * @modules java.base/jdk.internal.org.objectweb.asm.util
 * @run main TextifierWitupTest
 */

import jdk.internal.org.objectweb.asm.util.Textifier;

public class TextifierWitupTest {

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException("Unexpected output: " + msg);
        }
    }

    public static void main(String[] args) {
        Textifier parent = new Textifier();
        Textifier child = parent.visitAnnotation("myName", "Ljava/lang/Deprecated;");
        check(child != null, "child is null");
        check(child != parent, "child must be a distinct Textifier");
        String s = String.valueOf(parent.getText());
        check(s.contains("myName=@Ljava/lang/Deprecated;("), s);
        check(s.contains(")"), s);

        Textifier parent2 = new Textifier();
        Textifier child2 = parent2.visitAnnotation(null, "LFoo;");
        check(child2 != null, "child2 is null");
        String s2 = String.valueOf(parent2.getText());
        check(s2.contains("@LFoo;("), s2);
        check(!s2.contains("null="), s2);
    }
}
