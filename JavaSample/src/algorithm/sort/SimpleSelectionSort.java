package algorithm.sort;

/**
 * 简单选择排序
 * 
 * @author saz
 *
 */
public class SimpleSelectionSort {

	public static void main(String[] args) {
		int[] a = new int[] { 5, 12, 8, 2, 4, 9, 1, 7 };
		sort2(a);
		print(a);
	}

	public static void print(int[] a) {
		for (int temp : a) {
			System.out.print(temp + " ");
		}
		System.out.println("");
	}

	public static void sort(int[] a) {
		for (int i = 0; i < a.length; i++) {
			int k = i; // 最小值的下标
			for (int j = i + 1; j < a.length; j++) {
				if (a[k] > a[j]) {
					k = j;
				}
			}
			int temp = a[i];
			a[i] = a[k];
			a[k] = temp;
		}
	}

	public static void sort2(int[] a) {
		int i, j, min, max, temp;
		int n=a.length;
		for (i = 1; i < n / 2; i++) {
			min = i;
			max = i;
			for (j = i + 1; j < n -i; j++) {
				if (a[j] > a[max]) {
					max = j;
				}
				if (a[j] < a[min]) {
					min = j;
				}
			}
			temp = a[i - 1];
			a[i - 1] = a[min];
			a[min] = temp;
			
			temp = a[n - i];
			a[n - i] = a[max];
			a[max] = temp;
		}
	}
}
