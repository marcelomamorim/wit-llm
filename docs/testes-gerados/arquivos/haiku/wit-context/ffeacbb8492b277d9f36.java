/*
 * @test
 * @summary Witup-derived checks for ConstantBootstraps.enumConstant argument validation
 * @run main ConstantBootstrapsWitupTest
 */

import java.lang.invoke.ConstantBootstraps;
import java.lang.invoke.MethodHandles;

public class ConstantBootstrapsWitupTest {

    public enum Color { RED, GREEN }

    interface Action {
        void run() throws Exception;
    }

    public static void main(String[] args) throws Exception {
        MethodHandles.Lookup lookup = MethodHandles.lookup();

        Object c = ConstantBootstraps.enumConstant(lookup, "GREEN", Color.class);
        if (c != Color.GREEN) {
            throw new AssertionError("expected GREEN but got " + c);
        }

        expectNpe(() -> ConstantBootstraps.enumConstant(null, "RED", Color.class));
        expectNpe(() -> ConstantBootstraps.enumConstant(lookup, null, Color.class));
        expectNpe(() -> ConstantBootstraps.enumConstant(lookup, "RED", null));

        expectIae(() -> ConstantBootstraps.enumConstant(lookup, "BLUE", Color.class));
    }

    static void expectNpe(Action a) {
        try {
            a.run();
        } catch (NullPointerException e) {
            return;
        } catch (Exception e) {
            throw new AssertionError("unexpected exception: " + e, e);
        }
        throw new AssertionError("expected NullPointerException");
    }

    static void expectIae(Action a) {
        try {
            a.run();
        } catch (IllegalArgumentException e) {
            return;
        } catch (Exception e) {
            throw new AssertionError("unexpected exception: " + e, e);
        }
        throw new AssertionError("expected IllegalArgumentException");
    }
}
