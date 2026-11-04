/*
 * @test
 * @summary Test for ChoiceCallback.setSelectedIndexes behavior
 * @run main ChoiceCallbackWitupTest
 */

import javax.security.auth.callback.ChoiceCallback;

public class ChoiceCallbackWitupTest {

    public static void main(String[] args) {
        testSetSelectedIndexesWithMultipleAllowed();
        testSetSelectedIndexesWithoutMultipleAllowed();
    }

    private static void testSetSelectedIndexesWithMultipleAllowed() {
        String prompt = "Select options";
        String[] choices = {"Option1", "Option2"};
        int defaultChoice = 0;
        boolean multipleSelectionsAllowed = true;
        ChoiceCallback callback = new ChoiceCallback(prompt, choices, defaultChoice, multipleSelectionsAllowed);
        int[] selections = {0, 1};
        callback.setSelectedIndexes(selections);
        // No exception expected
        System.out.println("testSetSelectedIndexesWithMultipleAllowed passed");
    }

    private static void testSetSelectedIndexesWithoutMultipleAllowed() {
        String prompt = "Select one option";
        String[] choices = {"Option1", "Option2"};
        int defaultChoice = 0;
        boolean multipleSelectionsAllowed = false;
        ChoiceCallback callback = new ChoiceCallback(prompt, choices, defaultChoice, multipleSelectionsAllowed);
        int[] selections = {1};
        try {
            callback.setSelectedIndexes(selections);
            // Expecting UnsupportedOperationException
            System.err.println("testSetSelectedIndexesWithoutMultipleAllowed failed: exception not thrown");
        } catch (UnsupportedOperationException e) {
            System.out.println("testSetSelectedIndexesWithoutMultipleAllowed passed");
        }
    }
}
