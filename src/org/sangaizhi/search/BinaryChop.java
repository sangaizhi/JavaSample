/********************************************
 * 文件名称: BinaryChop
 * 系统名称: JavaSample
 * 模块名称:
 * 软件版权: 深圳中云创科技有限公司
 * 功能说明:
 * 系统版本: 1.0.0.0
 * 开发人员: saz
 * 开发时间: 2017/2/23
 * 审核人员:
 * 相关文档:
 * 修改记录: 修改日期    修改人员    修改说明
 **/
package org.sangaizhi.search;

import org.sangaizhi.sort.BubbleSort;

/**
 * 二分查找算法 二分査找也称折半査找，其优点是查找速度快，缺点是要求所要査找的数据必须是有序序列。
 * 该算法的基本思想是将所要査找的序列的中间位置的数据与所要査找的元素进行比较，如果相等，则表示査找成功，否则将以该位置为基准将所要査找的序列分为左右两部分。
 * 接下来根据所要査找序列的升降序规律及中间元素与所查找元素的大小关系，来选择所要査找元素可能存在的那部分序列，对其采用同样的方法进行査找，直至能够确定所要查找的元素是否存在，
 */
public class BinaryChop
{
    
    public static void main(String[] args)
    {
        BinaryChop binaryChop = new BinaryChop();
        int[] a = {42, 45, 85, 12, 46, 9, 4, 56, 54, 15};
        BubbleSort bubbleSort = new BubbleSort();
        bubbleSort.bubbleSort(a, a.length);
        bubbleSort.print(a);
        int result = binaryChop.binarySearch(a, 54);
        System.out.println("");
        System.out.println(result);
    }
    
    /**
     * 递归二分查找
     * 
     * @param a 要查找的数组
     * @param key 目标值
     * @param start 查找的起始点
     * @param end 查找的结束点
     * @return
     */
    private int binaryChop(int[] a, int key, int start, int end)
    {
        while (start <= end)
        {
            int middle = (start + end) / 2;
            int temp = a[middle];
            if (temp < key)
            {
                return binaryChop(a, key, middle + 1, end);
            }
            else if (temp > key)
            {
                return binaryChop(a, key, start, middle - 1);
            }
            else
            {
                return middle;
            }
        }
        return -1;
    }

    /**
     * 非递归调用
     * @param a 要查询的数组
     * @param key 目标值
     * @return
     */
    public int binarySearch(int[] a, int key)
    {
        int low = 0;
        int high = a.length - 1;
        while (low <= high)
        {
            int middle = low + ((high - low) >> 2);
            if (key == a[middle])
            {
                return middle;
            }
            else if (key < a[middle])
            {
                high = middle - 1;
            }
            else if (key > a[middle])
            {
                low = middle + 1;
            }
        }
        return -1;
    }
    
}
