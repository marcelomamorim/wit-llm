/*
 * @test
 * @summary Verify ParameterNode constructor field initialization and accept(null) NullPointerException
 * @modules java.base/jdk.internal.org.objectweb.asm.tree java.base/jdk.internal.org.objectweb.asm
 * @run main ParameterNodeWitupTest
 */

import jdk.internal.org.objectweb.asm.tree.ParameterNode;

public class ParameterNodeWitupTest {
    public static void main(String[] args) {
        ParameterNode p = new ParameterNode("arg", 0x0010);
        check("arg".equals(p.name), "name must be stored");
        check(p.access == 0x0010, "access must be stored");

        ParameterNode q = new ParameterNode(null, 0);
        check(q.name == null, "null name must be stored as null");
        check(q.access == 0, "zero access must be stored");

        ParameterNode r = new ParameterNode("", 0x1000);
        check("".equals(r.name), "empty name must be stored");
        check(r.access == 0x1000, "synthetic access must be stored");

        boolean thrown = false;
        try {
            p.accept(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "accept(null) must throw NullPointerException");
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
