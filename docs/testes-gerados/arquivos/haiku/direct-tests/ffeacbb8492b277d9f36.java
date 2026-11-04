/*
 * @test
 * @summary Tests ConstantBootstraps.enumConstant for valid names, unknown names and null arguments
 * @run main ConstantBootstrapsWitupTest
 */

import java.lang.invoke.ConstantBootstraps;
import java.lang.invoke.MethodHandles;

public class ConstantBootstrapsWitupTest {

    public enum Color { RED, GREEN, BLUE }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void expectException(Class<? extends Throwable> expected, Runnable action, String message) {
        try {
            action.run();
        } catch (Throwable t) {
            if (expected.isInstance(t)) {
                return;
            }
            throw new RuntimeException(message + ": unexpected " + t, t);
        }
        throw new RuntimeException(message + ": no exception thrown");
    }

    public static void main(String[] args) {
        MethodHandles.Lookup lookup = MethodHandles.lookup();

        Color red = ConstantBootstraps.enumConstant(lookup, "RED", Color.class);
        check(red == Color.RED, "RED not resolved to Color.RED");

        Color blue = ConstantBootstraps.enumConstant(lookup, "BLUE", Color.class);
        check(blue == Color.BLUE, "BLUE not resolved to Color.BLUE");

        Color green = ConstantBootstraps.enumConstant(lookup, "GREEN", Color.class);
        check(green == Color.GREEN, "GREEN not resolved to Color.GREEN");

        expectException(IllegalArgumentException.class,
                () -> ConstantBootstraps.enumConstant(lookup, "PURPLE", Color.class),
                "unknown name");

        expectException(NullPointerException.class,
                () -> ConstantBootstraps.enumConstant(null, "RED", Color.class),
                "null lookup");

        expectException(NullPointerException.class,
                () -> ConstantBootstraps.enumConstant(lookup, null, Color.class),
                "null name");

        expectException(NullPointerException.class,
                () -> ConstantBootstraps.enumConstant(lookup, "RED", null),
                "null type");
    }
}
