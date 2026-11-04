/* @test    @summary Test for Signal.raise method to verify NullPointerException when sig is null    @run main SignalWitupTest */

import java.util.Objects;
import jdk.internal.misc.Signal;

public class SignalWitupTest {

    public static void main(String[] args) {
        testRaiseWithNullSignal();
        testRaiseWithValidSignal();
    }

    private static void testRaiseWithNullSignal() {
        try {
            Signal.raise(null);
            System.out.println("FAIL: Expected NullPointerException was not thrown.");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown as expected.");
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception type: " + t);
        }
    }

    private static void testRaiseWithValidSignal() {
        Signal sig = new Signal("INT");
        try {
            Signal.raise(sig);
            System.out.println("PASS: Signal.raise executed without exception for valid signal.");
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception for valid signal: " + t);
        }
    }
}
