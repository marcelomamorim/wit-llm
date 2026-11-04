/* @test    @summary Test for Signal.raise method to ensure it throws IllegalArgumentException for unhandled signals and executes without exception for handled signals.    @run main SignalWitupTest */

import java.util.Hashtable;
import java.util.Objects;

public class SignalWitupTest {

    public static void main(String[] args) {
        testRaiseWithHandledSignal();
        testRaiseWithUnhandledSignal();
    }

    public static void testRaiseWithHandledSignal() {
        Signal signal = new Signal("INT");
        // Simulate that the signal is handled by adding to handlers
        Signal.handlers.put(signal, new Signal.Handler() {
            public void handle(Signal sig) {
                // no-op
            }
        });
        try {
            Signal.raise(signal);
        } catch (IllegalArgumentException e) {
            throw new AssertionError("Expected no exception for handled signal", e);
        }
    }

    public static void testRaiseWithUnhandledSignal() {
        Signal signal = new Signal("TERM");
        // Ensure no handler is registered
        Signal.handlers.remove(signal);
        try {
            Signal.raise(signal);
            throw new AssertionError("Expected IllegalArgumentException for unhandled signal");
        } catch (IllegalArgumentException e) {
            // expected
            if (!e.getMessage().contains("Unhandled signal")) {
                throw new AssertionError("Unexpected exception message: " + e.getMessage(), e);
            }
        }
    }
}

// Mock Signal class to facilitate testing
class Signal {
    private static Hashtable<Signal, Signal.Handler> handlers = new Hashtable<>();
    private int number;
    private String name;

    public static final Signal INT = new Signal("INT", 2);
    public static final Signal TERM = new Signal("TERM", 15);

    public Signal(String name) {
        this.name = name;
        this.number = name.equals("INT") ? 2 : name.equals("TERM") ? 15 : -1;
    }

    public Signal(String name, int number) {
        this.name = name;
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    public String getName() {
        return name;
    }

    public static void raise(Signal sig) throws IllegalArgumentException {
        Objects.requireNonNull(sig, "sig");
        if (handlers.get(sig) == null) {
            throw new IllegalArgumentException("Unhandled signal: " + sig);
        }
        // simulate raising signal
        // no actual signal handling in test
    }

    public static class Handler {
        public void handle(Signal sig) {
            // no-op
        }
    }
}
