class PrimeStack
{
    private class Node
    {
        Prime.Concurrency data = null;
        Node next = null;

        public Node(Prime.Concurrency data)
        {
            this.data = data;
        }
    }

    volatile Node head = null;
    volatile int count = 0;

    synchronized public Prime.Concurrency pop()
    {
        if (head != null)
        {
            Prime.Concurrency temp = head.data;

            head = head.next;

            --count;

            return temp;

        }

        return null;
    }

    synchronized public void push(Prime.Concurrency obj)
    {
        if (obj == null)
        {
            return;
        }

        if (head == null)
        {
            head = new Node(obj);
        }
        else
        {
            Node temp = new Node(obj);
            temp.next = head;
            head = temp;
        }
        ++count;
    }

    synchronized public int getCount()
    {
        return count;
    }
}

class Broadcaster {

    private class Singleton
    {
        public static Broadcaster instance = new Broadcaster();
        public static PrimeStack stack = new PrimeStack();
    }

    public static Broadcaster getInstance()
    {
        return Singleton.instance;
    }

    

    public boolean isQueued()
    {
        return (Singleton.stack.getCount() > 0);
    }

    public void add(Prime.Concurrency obj)
    {
        Singleton.stack.push(obj);
    }

    public Prime.Concurrency get()
    {
        return Singleton.stack.pop();
    }
    
}