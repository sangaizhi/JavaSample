/********************************************
 * 文件名称: BubbleSort
 * 系统名称: JavaSample
 * 模块名称:
 * 软件版权: 深圳中云创科技有限公司
 * 功能说明:
 * 系统版本: 1.0.0.0
 * 开发人员: saz
 * 开发时间: 2017/2/22
 * 审核人员:
 * 相关文档:
 * 修改记录: 修改日期    修改人员    修改说明
 **/
package sort;

public class BubbleSort
{

    public static void main(String[] args) {
        BubbleSort bubbleSort = new BubbleSort();
        int[] a = new int[]{3, 1, 5, 7, 2, 4, 9, 6, 10, 8};
        bubbleSort.print(a);
        bubbleSort.bubbleSort(a, a.length);
        bubbleSort.print(a);
    }

    void print(int[] a) {
        for (int i = 0; i < a.length; i++) {
            System.out.print(a[i] + " ");
        }
    }

    void bubbleSort(int a[], int n)
    {
        for (int i = 0; i < n - 1; ++i)
        {
            for (int j = 0; j < n - i - 1; ++j)
            {
                if (a[j] > a[j + 1])
                {
                    int tmp = a[j];
                    a[j] = a[j + 1];
                    a[j + 1] = tmp;
                }
            }
        }
    }
}
