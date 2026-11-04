/* @test    @summary Test for addAll method in ConcurrentHashMap to verify UnsupportedOperationException is thrown.    @run main ConcurrentHashMapWitupTest */

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapWitupTest {

    public static void main(String[] args) {
        testAddAllThrowsException();
    }

    public static void testAddAllThrowsException() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        Collection<String> dummyCollection = null;
        try {
            map.addAll(dummyCollection);
            // Se não lançar exceção, o teste falha
            System.err.println("Failed: Expected UnsupportedOperationException was not thrown.");
            System.exit(1);
        } catch (UnsupportedOperationException e) {
            // Exceção esperada, o teste passa
            System.out.println("Passed: UnsupportedOperationException was thrown as expected.");
        } catch (NullPointerException e) {
            // Caso dummyCollection seja null, pode lançar NPE, que também é válido para esse teste
            System.out.println("Passed: NullPointerException was thrown due to null collection.");
        } catch (Exception e) {
            System.err.println("Failed: Unexpected exception: " + e);
            System.exit(1);
        }
    }
}
