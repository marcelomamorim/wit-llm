/* @test    @summary Test for Sink.ChainedInt constructor null downstream argument    @run main SinkWitupTest */

import java.util.stream.Sink;

public class SinkWitupTest {

    public static void main(String[] args) {
        testChainedIntConstructorNullDownstream();
    }

    public static void testChainedIntConstructorNullDownstream() {
        try {
            new Sink.ChainedInt(null);
            System.err.println("FAIL: Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown as expected");
        } catch (Throwable t) {
            System.err.println("FAIL: Unexpected exception type: " + t);
        }
    }
}
