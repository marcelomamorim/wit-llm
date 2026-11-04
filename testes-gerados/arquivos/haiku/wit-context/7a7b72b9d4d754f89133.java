/*
 * @test
 * @summary Verifies that setContentHandlerFactory accepts the first factory and throws Error when a factory is already defined
 * @run main URLConnectionWitupTest
 */

import java.net.ContentHandlerFactory;
import java.net.URLConnection;

public class URLConnectionWitupTest {

    public static void main(String[] args) throws Exception {
        ContentHandlerFactory first = mime -> null;
        URLConnection.setContentHandlerFactory(first);

        boolean thrownForSecond = false;
        try {
            URLConnection.setContentHandlerFactory(mime -> null);
        } catch (Error e) {
            thrownForSecond = true;
            if (!"factory already defined".equals(e.getMessage())) {
                throw new RuntimeException("unexpected message: " + e.getMessage());
            }
        }
        if (!thrownForSecond) {
            throw new RuntimeException("expected Error when factory already defined");
        }

        boolean thrownForNull = false;
        try {
            URLConnection.setContentHandlerFactory(null);
        } catch (Error e) {
            thrownForNull = true;
        }
        if (!thrownForNull) {
            throw new RuntimeException("expected Error on repeated call with null");
        }
    }
}
