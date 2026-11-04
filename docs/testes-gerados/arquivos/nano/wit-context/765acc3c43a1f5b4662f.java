/* @test    @summary Test for Collections.synchronizedCollection with null input    @run main CollectionsWitupTest */

import java.util.Collection;
import java.util.Collections;

public class CollectionsWitupTest {

    public static void main(String[] args) {
        testSynchronizedCollectionNull();
    }

    public static void testSynchronizedCollectionNull() {
        try {
            Collection<?> result = Collections.synchronizedCollection(null);
            // Se o método não lançar exceção, o resultado deve ser null ou uma coleção sincronizada
            // Como o método deve lançar NullPointerException, essa linha não deve ser atingida
            System.out.println("Unexpected result: " + result);
        } catch (NullPointerException e) {
            System.out.println("Caught expected NullPointerException");
        }
    }
}
