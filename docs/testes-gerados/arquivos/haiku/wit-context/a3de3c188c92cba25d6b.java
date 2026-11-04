/*
 * @test
 * @summary Witup-generated tests for ConstantBootstraps.primitiveClass
 * @run main ConstantBootstrapsWitupTest
 */
import java.lang.invoke.ConstantBootstraps;
import java.lang.invoke.MethodHandles;

public class ConstantBootstrapsWitupTest {
    interface Action { Object run() throws Throwable; }

    public static void main(String[] args) throws Throwable {
        MethodHandles.Lookup lookup = MethodHandles.lookup();

        expectThrows(NullPointerException.class, () -> ConstantBootstraps.primitiveClass(lookup, null, Class.class));
        expectThrows(NullPointerException.class, () -> ConstantBootstraps.primitiveClass(lookup, "I", null));
        expectThrows(NullPointerException.class, () -> ConstantBootstraps.primitiveClass(lookup, null, null));
        expectThrows(IllegalArgumentException.class, () -> ConstantBootstraps.primitiveClass(lookup, "I", String.class));
        expectThrows(IllegalArgumentException.class, () -> ConstantBootstraps.primitiveClass(lookup, "", Class.class));
        expectThrows(IllegalArgumentException.class, () -> ConstantBootstraps.primitiveClass(lookup, "int", Class.class));

        check(int.class, ConstantBootstraps.primitiveClass(lookup, "I", Class.class));
        check(boolean.class, ConstantBootstraps.primitiveClass(lookup, "Z", Class.class));
        check(long.class, ConstantBootstraps.primitiveClass(lookup, "J", Class.class));
    }

    static void check(Object expected, Object actual) {
        if (expected != actual) {
            throw new RuntimeException("expected " + expected + " but was " + actual);
        }
    }

    static void expectThrows(Class<? extends Throwable> type, Action action) {
        try {
            action.run();
        } catch (Throwable t) {
            if (type.isInstance(t)) {
                return;
            }
            throw new RuntimeException("unexpected exception: " + t, t);
        }
        throw new RuntimeException("expected " + type.getName() + " was not thrown");
    }
}
