package algorithm.sort;
/**
 * 直接插入排序
 * @author saz
 */
public class StraightInsertSort {

	public static void main(String[] args) {
		int[] a = new int[]{5,12,8,2,4,9,1,7};
		print(a);
		sort(a);
		print(a);
	}
	public static void print(int[] a) {
		for (int temp : a) {
			System.out.print(temp + " ");
		}
		System.out.println("");
	}
	public static void sort(int[] a) {
		// 把第一个当做已经排好序的
		for (int i = 1; i < a.length; i++) {
			if (a[i] < a[i - 1]) {
				int j;
				int temp = a[i];
				a[i] = a[i - 1];
				for (j = i - 1; j >= 0 && temp < a[j]; j--) {
					//通过循环，逐个找到要插入的位置
					a[j+1]=a[j];
				}
				a[j+1]=temp;
			}
		}
	}
}
