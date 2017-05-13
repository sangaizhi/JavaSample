package org.sangaizhi.thread;

public class RunnableThread implements Runnable
{
    private int countDown = 10;
    
    private static int taskCount = 0;
    
    private final int id = taskCount++;
    
    public RunnableThread()
    {
        // TODO Auto-generated constructor stub
    }
    
    public RunnableThread(int countDown)
    {
        this.countDown = countDown;
    }
    
    public String status()
    {
        return "#" + id + "(" + (countDown > 0 ? countDown : "RunnableThread") + ")";
        
    }
    
    public void run()
    {
        try {
            while (countDown-- > 0)
            {
                System.out.println(status());
            }
            Thread.sleep(100L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
    
    public static void main(String[] args)
    {
        RunnableThread t = new RunnableThread();
        t.run();
    }
    
}
