package sort;

public class HeapSort {

	public static void main(String[] args) {
		int a[] = { 3, 1, 5, 7, 2, 4, 9, 6, 10, 8 };
		HeapSort obj = new HeapSort();
		System.out.println("��ʼֵ��");
		obj.print(a);
		System.out.println("");
		for (int i = 0; i < a.length; i++) {
			obj.createLittleHeap(a, a.length - 1 - i);// ������,��������С���ѡ�ÿ��ѭ���꣬�������ĸ��ڵ㶼����Сֵ���������ʱ��δ�źò������һ��ֵ����λ��
			obj.swap(a, 0, a.length - 1 - i);// �����һ��ֵ����λ�ã���Сֵ�ҵ���λ��
			obj.print(a);
			System.out.println();

		}
		System.out.println("\n�����");
		obj.print(a);
	}

	/*
	 * ����С���ѣ�˫�׽ڵ�С���ӽڵ��ֵ����Ҷ�ӽڵ㿪ʼ��ֱ�����ڵ㡣���������ĶѶ�λ��Сֵ
	 */
	private void createLittleHeap(int[] data, int last) {
		for (int i = (last - 1) / 2; i >= 0; i--) { // �ҵ����һ��Ҷ�ӽڵ��˫�׽ڵ�
			// ���浱ǰ�����жϵĽڵ�
			int parent = i;
			// ����ǰ�ڵ�����ӽڵ���ڣ����ӽڵ����
			while (2 * parent + 1 <= last) {
				// biggerIndex���Ǽ�¼�ϴ�ڵ��ֵ,�ȸ�ֵΪ��ǰ�жϽڵ�����ӽڵ�
				int bigger = 2 * parent + 1;// biggerָ�����ӽڵ�
				if (bigger < last) { // ˵���������ӽڵ�
					if (data[bigger] > data[bigger + 1]) { // ���ӽڵ�>���ӽڵ�ʱ
						bigger = bigger + 1;
					}
				}
				if (data[parent] > data[bigger]) { // ��˫�׽ڵ�ֵ�����ӽڵ�������
					// ����ǰ�ڵ�ֵ���ӽڵ����ֵС���򽻻�2�ߵ�ֵ��������biggerIndexֵ��ֵ��k
					swap(data, parent, bigger);
					parent = bigger;
				} else {
					break;
				}
			}
		}
	}

	public void print(int a[]) {
		for (int i = 0; i < a.length; i++) {
			System.out.print(a[i] + " ");
		}
	}

	public void swap(int[] data, int i, int j) {
		if (i == j) {
			return;
		}
		data[i] = data[i] + data[j];
		data[j] = data[i] - data[j];
		data[i] = data[i] - data[j];
	}

}
