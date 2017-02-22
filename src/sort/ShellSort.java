package sort;

/**
 * ϣ����������
 * @author saz
 *
 */
public class ShellSort {
	  public static void main(String[] args) {  
	        int a[] = {3,1,5,7,2,4,9,6,10,8};    
	        ShellSort obj = new ShellSort();  
	        System.out.println("��ʼֵ��");  
	        obj.print(a);  
	        obj.shellSort(a);  
	        System.out.println("\n�����");  
	        obj.print(a);  
	  
	    }  
	    private void shellSort(int[] a) {  
	         int dk = a.length/2;   
	         while( dk >= 1  ){    
	            ShellInsertSort(a, dk);    
	            dk = dk/2;  
	         }  
	    }  
	    private void ShellInsertSort(int[] a, int dk) {//���Ʋ�������ֻ�ǲ�������������1������������dk,��1����dk�Ϳ�����  
	        for(int i=dk;i<a.length;i++){  
	            if(a[i]<a[i-dk]){  
	                int j;  
	                int x=a[i];//xΪ������Ԫ��  
	                a[i]=a[i-dk];  
	                for(j=i-dk;  j>=0 && x<a[j];j=j-dk){//ͨ��ѭ�����������һλ�ҵ�Ҫ�����λ�á�  
	                    a[j+dk]=a[j];  
	                }  
	                a[j+dk]=x;//����  
	            }  
	        }  
	    }  
	    public void print(int a[]){  
	        for(int i=0;i<a.length;i++){  
	            System.out.print(a[i]+" ");  
	        }  
	    }  
}
