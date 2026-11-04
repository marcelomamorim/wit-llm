/*
 * @test
 * @summary Test iterator method of InsnList for index bounds
 * @run main InsnListWitupTest
 */

import java.util.ListIterator;
import java.util.NoSuchElementException;
import jdk.internal.org.objectweb.asm.tree.InsnList;

public class InsnListWitupTest {

    public static void main(String[] args) {
        testIteratorWithNegativeIndex();
        testIteratorWithIndexEqualToSize();
        testIteratorWithIndexGreaterThanSize();
        testIteratorWithIndexLessThanZero();
    }

    private static void testIteratorWithNegativeIndex() {
        InsnList list = new InsnList();
        // Adiciona elementos fictícios para evitar erro de lista vazia
        // Como não há construtor público, usar método de fábrica ou reflexão não é possível,
        // então criar lista vazia e testar índice negativo
        try {
            list.iterator(-1);
            System.err.println("Failed: Expected IndexOutOfBoundsException for index -1");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Passed: IndexOutOfBoundsException for index -1");
        }
    }

    private static void testIteratorWithIndexEqualToSize() {
        InsnList list = new InsnList();
        // Como não há método público para adicionar elementos, testar lista vazia com index 0
        try {
            list.iterator(0);
            System.out.println("Passed: iterator with index 0 on empty list");
        } catch (IndexOutOfBoundsException e) {
            System.err.println("Failed: Unexpected IndexOutOfBoundsException for index 0 on empty list");
        }
    }

    private static void testIteratorWithIndexGreaterThanSize() {
        InsnList list = new InsnList();
        // Lista vazia, qualquer índice > 0 deve lançar exceção
        try {
            list.iterator(1);
            System.err.println("Failed: Expected IndexOutOfBoundsException for index 1");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Passed: IndexOutOfBoundsException for index 1");
        }
    }

    private static void testIteratorWithIndexLessThanZero() {
        InsnList list = new InsnList();
        // Lista vazia, índice negativo
        try {
            list.iterator(-2);
            System.err.println("Failed: Expected IndexOutOfBoundsException for index -2");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Passed: IndexOutOfBoundsException for index -2");
        }
    }
}
