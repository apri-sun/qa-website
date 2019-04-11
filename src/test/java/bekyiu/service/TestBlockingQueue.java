package bekyiu.service;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;


public class TestBlockingQueue
{
    public static void main(String[] args)
    {
        BlockingQueue<String> queue = new ArrayBlockingQueue<>(10);
        new Thread(new Producer(queue)).start();
        new Thread(new Consumer(queue)).start();
//        new Thread(new Consumer(queue)).start();
//        new Thread(new Consumer(queue)).start();
    }

}

class Producer implements Runnable
{
    private BlockingQueue<String> queue;

    public Producer(BlockingQueue<String> queue)
    {
        this.queue = queue;
    }

    public void run()
    {
        try
        {
            for (int i = 0; i < 100; i++)
            {
                Thread.sleep(10 * 6);
                queue.put(String.valueOf(i));
                System.out.println(i);
            }
        }
        catch (Exception e)
        {

            e.printStackTrace();
        }
    }
}

class Consumer implements Runnable
{
    private BlockingQueue<String> queue;

    public Consumer(BlockingQueue<String> queue)
    {
        this.queue = queue;
    }

    @Override
    public void run()
    {
        try
        {
            while (true)
            {
                Thread.sleep(1000 * 3);
                System.out.println(Thread.currentThread().getName() + ": " + queue.take());
            }
        }
        catch (Exception e)
        {

            e.printStackTrace();
        }
    }
}
