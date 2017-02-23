
package sort;

/**
 * 冒泡排序
 */
public class BubbleSort
{
    
    public static void main(String[] args)
    {
        BubbleSort bubbleSort = new BubbleSort();
        int[] a = new int[] {3, 1, 5, 7, 2, 4, 9, 6, 10, 8};
        bubbleSort.bubbleSort2(a, a.length);
        bubbleSort.print(a);
    }
    
    public void print(int[] a)
    {
        for (int anA : a)
        {
            System.out.print(anA + " ");
        }
    }
    
    /**
     * 普通冒泡排序
     */
    public void bubbleSort(int[] a, int n)
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
    
    /**
     * 冒泡排序方法2： 对冒泡排序常见的改进方法是加入一标志性变量exchange，用于标志某一趟排序过程中是否有数据交换。
     * 如果进行某一趟排序时并没有进行数据交换, 则说明数据已经按要求排列好，可立即结束排序，避免不必要的比较过程。
     * 设置一标志性变量pos,用于记录每趟排序中最后一次进行交换的位置。
     * 由于pos位置之后的记录均已交换到位,故在进行下一趟排序时只要扫描到pos位置即可。
     */
    public void bubbleSort1(int[] a, int n)
    {
        int i = n - 1;
        while (i > 0)
        {
            int pos = 0;
            for (int j = 0; j < i; j++)
            {
                if (a[j] > a[j + 1])
                {
                    pos = j;
                    int temp = a[j];
                    a[j] = a[j + 1];
                    a[j + 1] = temp;
                }
            }
            i = pos;
        }
    }
    
    /**
     * 冒泡排序方法三 传统冒泡排序中每一趟排序操作只能找到一个最大值或最小值,
     * 我们考虑利用在每趟排序中进行正向和反向两遍冒泡的方法一次可以得到两个最终值(最大者和最小者) ,
     * 从而使排序趟数几乎减少了一半。
     */
    public void bubbleSort2(int[] a, int n)
    {
        int low = 0;
        int high = n - 1;
        int temp, j;
        while (low < high)
        {
            for (j = low; j < high; j++)
            { // 正向冒泡，找到最大者
                if (a[j] > a[j + 1])
                {
                    temp = a[j];
                    a[j] = a[j + 1];
                    a[j + 1] = temp;
                }
            }
            --high;
            for (j = high; j > low; j--)
            {
                if (a[j] < a[j - 1])
                {
                    temp = a[j];
                    a[j] = a[j - 1];
                    a[j - 1] = temp;
                }
            }
            low++;
        }
    }
    
}
