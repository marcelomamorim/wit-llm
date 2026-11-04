/*
 * @test
 * @summary Verifies Sink.ChainedReference constructor null-check and delegation of end() to downstream
 * @run main SinkWitupTest
 */

import java.util.stream.Sink;

public class SinkWitupTest {

    static class RecordingSink implements Sink<String> {
        int endCalls = 0;

        @Override
        public void accept(String s) {
        }

        @Override
        public void end() {
            endCalls++;
        }
    }

    static class Forwarding extends Sink.ChainedReference<String, String> {
        Forwarding(Sink<? super String> downstream) {
            super(downstream);
        }

        @Override
        public void accept(String s) {
        }
    }

    static void fail(String msg) {
        throw new RuntimeException(msg);
    }

    public static void main(String[] args) {
        boolean npe = false;
        try {
            new Forwarding(null);
        } catch (NullPointerException e) {
            npe = true;
        }
        if (!npe) {
            fail("Expected NullPointerException for null downstream");
        }

        RecordingSink downstream = new RecordingSink();
        Forwarding f = new Forwarding(downstream);
        if (!(f instanceof Sink)) {
            fail("ChainedReference should be a Sink");
        }
        f.end();
        if (downstream.endCalls != 1) {
            fail("end() should be delegated to downstream exactly once, was " + downstream.endCalls);
        }
    }
}
