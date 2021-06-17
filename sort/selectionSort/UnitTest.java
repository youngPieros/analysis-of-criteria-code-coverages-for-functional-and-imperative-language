package sort.selectionSort;

import org.junit.jupiter.api.Test;

public class UnitTest {
    @Test
    void testcase_1() {
        int[] numbers = new int[] {5, 0, 1, 2, 3, 4, 9, 8, 7, 6};
        SelectionSort.sort(numbers);
    }
}
