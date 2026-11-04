/* @test    @summary Test the Signal constructor with valid and null input    @run main SignalWitupTest */

import java.util.Objects;
import jdk.internal.misc.Signal;

public class SignalWitupTest {

    public static void main(String[] args) {
        testValidSignalName();
        testNullSignalName();
    }

    private static void testValidSignalName() {
        String validName = "INT";
        Signal signal = new Signal(validName);
        assert signal.getName().equals(validName) : "Expected name to be " + validName;
        assert signal.getNumber() >= 0 : "Expected non-negative signal number";
    }

    private static void testNullSignalName() {
        try {
            new Signal(null);
            assert false : "Expected NullPointerException when name is null";
        } catch (NullPointerException e) {
            assert e.getMessage().equals("name") : "Expected message 'name'";
        }
    }
}
