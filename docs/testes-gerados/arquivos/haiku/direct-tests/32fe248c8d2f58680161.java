/*
 * @test
 * @summary Verify Signal constructor accepts known names and rejects null, SIG-prefixed and unknown names
 * @modules java.base/jdk.internal.misc
 * @run main SignalWitupTest
 */

import jdk.internal.misc.Signal;

public class SignalWitupTest {

    public static void main(String[] args) {
        Signal s = new Signal("INT");
        check("INT".equals(s.getName()), "getName should return INT");
        check(s.getNumber() > 0, "getNumber should be positive for INT");

        Signal s2 = new Signal("INT");
        check(s.getNumber() == s2.getNumber(), "same name should give same number");

        expectIllegalArgument("SIGINT");
        expectIllegalArgument("NOSUCHSIGNAL");
        expectIllegalArgument("");

        boolean npe = false;
        try {
            new Signal(null);
        } catch (NullPointerException e) {
            npe = true;
        }
        check(npe, "null name should throw NullPointerException");
    }

    private static void expectIllegalArgument(String name) {
        boolean thrown = false;
        try {
            new Signal(name);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        check(thrown, "name '" + name + "' should throw IllegalArgumentException");
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
