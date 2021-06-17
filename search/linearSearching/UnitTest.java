package search.linearSearching;

import org.junit.jupiter.api.Test;

public class UnitTest {
    @Test
    void testcase_1() {
        int number = 10;
        int[] numbers = new int[] {};
        int index = LinearSearch.findWithLinearSearchAlgorithm(number,  numbers);
    }

    @Test
    void testcase_2() {
        int number = 5;
        int[] numbers = new int[] {5, 3, 9, 6, 7, 2, 4, 8, 0, 1};
        int index = LinearSearch.findWithLinearSearchAlgorithm(number,  numbers);
    }

    @Test
    void testcase_3() {
        int number = 4;
        int[] numbers = new int[] {5, 3, 9, 6, 7, 2, 4, 8, 0, 1};
        int index = LinearSearch.findWithLinearSearchAlgorithm(number,  numbers);
    }

    @Test
    void testcase_4() {
        int number = 10;
        int[] numbers = new int[] {5, 3, 9, 6, 7, 2, 4, 8, 0, 1};
        int index = LinearSearch.findWithLinearSearchAlgorithm(number,  numbers);
    }
}
