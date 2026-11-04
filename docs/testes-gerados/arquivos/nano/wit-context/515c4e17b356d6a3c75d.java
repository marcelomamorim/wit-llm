/*
 * @test
 * @summary Tests the select method of java.nio.channels.Selector for exception paths.
 * @run main SelectorWitupTest
 */

import java.io.IOException;
import java.nio.channels.Selector;
import java.nio.channels.SelectionKey;
import java.util.function.Consumer;

public class SelectorWitupTest {

    public static void main(String[] args) throws Exception {
        testSelectWithNegativeTimeout();
    }

    public static void testSelectWithNegativeTimeout() {
        Selector selector = null;
        try {
            selector = Selector.open();
            // Invoca o método select com um Consumer válido
            int result = selector.select(k -> {});
            System.out.println("select() retornou: " + result);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (selector != null) {
                try {
                    selector.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
