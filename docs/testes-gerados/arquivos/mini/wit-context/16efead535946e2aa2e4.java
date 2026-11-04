/*
 * @test
 * @summary Test sun.net.ftp.impl.FtpClient.structureMount(String) for expected behavior and exception
 * @run main FtpClientWitupTest
 */

import sun.net.ftp.impl.FtpClient;
import sun.net.ftp.FtpProtocolException;
import java.io.IOException;

public class FtpClientWitupTest {
    public static void main(String[] args) throws Exception {
        testStructureMountThrowsWhenServerNull();
        testStructureMountReturnsThisOnSuccess();
    }

    private static void testStructureMountThrowsWhenServerNull() {
        FtpClient client = FtpClient.create();
        // forcibly set server to null via subclass to simulate condition (server is private, no setter)
        // but no reflection allowed; so we create a subclass to override server to null
        FtpClient clientWithNullServer = new FtpClient() {
            @Override
            protected void checkServer() {
                // no-op to keep server null
            }

            @Override
            public FtpClient structureMount(String struct) throws FtpProtocolException, IOException {
                // simulate server == null by not initializing server
                if (null == null) { // simulate server == null
                    throw new IllegalStateException("server is null");
                }
                return super.structureMount(struct);
            }
        };

        // Since we cannot set server to null directly and no reflection allowed,
        // we test the behavior by calling structureMount on a fresh client without connect
        // The real create() returns a connected client, so we create a new instance via protected constructor
        FtpClient freshClient = new FtpClient() {
            // no connect called, so server remains null
        };

        try {
            freshClient.structureMount("ANY");
            throw new RuntimeException("Expected IllegalStateException not thrown");
        } catch (IllegalStateException e) {
            // expected
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception type", e);
        }
    }

    private static void testStructureMountReturnsThisOnSuccess() throws Exception {
        FtpClient client = FtpClient.create();
        // The create() method returns a connected client with server != null
        FtpClient result = client.structureMount("F");
        if (result != client) {
            throw new RuntimeException("structureMount did not return this");
        }
    }
}
