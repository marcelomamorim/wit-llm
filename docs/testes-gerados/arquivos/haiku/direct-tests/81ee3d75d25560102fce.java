/*
 * @test
 * @summary CheckFieldAdapter.visitAttribute must reject a null attribute with IllegalArgumentException
 * @modules java.base/jdk.internal.org.objectweb.asm
 * @modules java.base/jdk.internal.org.objectweb.asm.util
 * @run main CheckFieldAdapterWitupTest
 */

import jdk.internal.org.objectweb.asm.FieldVisitor;
import jdk.internal.org.objectweb.asm.util.CheckFieldAdapter;

public class CheckFieldAdapterWitupTest {
    public static void main(String[] args) {
        FieldVisitor delegate = null;
        CheckFieldAdapter cfa = new CheckFieldAdapter(delegate);
        try {
            cfa.visitAttribute(null);
            throw new RuntimeException("Expected IllegalArgumentException was not thrown");
        } catch (IllegalArgumentException e) {
            String expected = "Invalid attribute (must not be null)";
            if (!expected.equals(e.getMessage())) {
                throw new RuntimeException("Unexpected message: " + e.getMessage());
            }
        }
    }
}
