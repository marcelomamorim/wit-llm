/*
 * @test
 * @summary Test getOption method of AsynchronousSocketChannelImpl for null and unsupported options
 * @run main AsynchronousSocketChannelImplWitupTest
 */

import java.io.IOException;
import java.net.SocketOption;
import java.net.StandardSocketOptions;

public class AsynchronousSocketChannelImplWitupTest {

    public static void main(String[] args) throws IOException {
        testGetOptionNull();
        testGetOptionUnsupported();
    }

    static void testGetOptionNull() {
        try {
            // Instantiate a dummy object with minimal setup
            sun.nio.ch.AsynchronousSocketChannelImpl channel = createDummyChannel();
            // Expect NullPointerException when passing null
            try {
                channel.getOption(null);
                System.err.println("Failed: Expected NullPointerException not thrown");
            } catch (NullPointerException e) {
                System.out.println("Passed: NullPointerException thrown as expected");
            } catch (Throwable t) {
                System.err.println("Failed: Unexpected exception: " + t);
            }
        } catch (IOException e) {
            System.err.println("Setup failed: " + e);
        }
    }

    static void testGetOptionUnsupported() {
        try {
            sun.nio.ch.AsynchronousSocketChannelImpl channel = createDummyChannel();
            // Use a dummy unsupported SocketOption
            SocketOption<Object> unsupportedOption = new DummySocketOption();
            // Expect UnsupportedOperationException
            try {
                channel.getOption(unsupportedOption);
                System.err.println("Failed: Expected UnsupportedOperationException not thrown");
            } catch (UnsupportedOperationException e) {
                System.out.println("Passed: UnsupportedOperationException thrown as expected");
            } catch (Throwable t) {
                System.err.println("Failed: Unexpected exception: " + t);
            }
        } catch (IOException e) {
            System.err.println("Setup failed: " + e);
        }
    }

    static sun.nio.ch.AsynchronousSocketChannelImpl createDummyChannel() throws IOException {
        // Create a minimal dummy instance for testing
        // Since constructor is protected, use reflection or a subclass
        return new DummyAsynchronousSocketChannelImpl();
    }

    static class DummySocketOption implements SocketOption<Object> {
        @Override
        public String name() {
            return "DUMMY_OPTION";
        }
        @Override
        public Class<Object> type() {
            return Object.class;
        }
    }

    static class DummyAsynchronousSocketChannelImpl extends sun.nio.ch.AsynchronousSocketChannelImpl {
        DummyAsynchronousSocketChannelImpl() throws IOException {
            super(new sun.nio.ch.AsynchronousChannelGroupImpl(Executors.newSingleThreadExecutor()), null, null);
        }
        @Override
        public <T> T getOption(SocketOption<T> name) throws IOException {
            return super.getOption(name);
        }
    }
}
