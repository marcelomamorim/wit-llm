/*
 * @test
 * @summary URLConnection.setContentHandlerFactory accepts the first factory and rejects any later call with Error("factory already defined")
 * @run main URLConnectionWitupTest
 */

import java.net.ContentHandlerFactory;
import java.net.URLConnection;

public class URLConnectionWitupTest {

    public static void main(String[] args) throws Exception {
        ContentHandlerFactory first = name -> null;
        URLConnection.setContentHandlerFactory(first);

        boolean thrown = false;
        try {
            URLConnection.setContentHandlerFactory(name -> null);
        } catch (Error e) {
            thrown = true;
            if (!"factory already defined".equals(e.getMessage())) {
                throw new RuntimeException("unexpected message: " + e.getMessage());
            }
        }
        if (!thrown) {
            throw new RuntimeException("expected Error on second factory registration");
        }

        thrown = false;
        try {
            URLConnection.setContentHandlerFactory(first);
        } catch (Error e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("expected Error when re-registering the same factory");
        }
    }
}
