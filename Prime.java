class Prime
{
    protected  class Concurrency implements Runnable
    {
        public Boolean result = null;
        public Integer number = null;
        public Boolean isPrime(Integer number)
        {
            if (number == 0)
            {
                System.err.println("WARNING: isPrime() called on zero (0). Returned false.");
                return false;
            }
            if (number > 2)
            {
                int limit = (int) Math.sqrt((double)number);
                limit += 1;
                for (int i= 2; i < limit; i++)
                {
                    if ((number % i) == 0)
                    {
                        return false;
                    }
                }
            }
            return true;
        }
        @Override
        public void run()
        {
            result = isPrime(number);
            Broadcaster b = Broadcaster.getInstance();
            b.add(this);
        }
        public Concurrency(int number)
        {
            this.number = number;
        }
    }
    public void printResultConcurrency(Concurrency number)
    {
        if (number.result)
        {
            System.out.println("The number: "+ number.number +" is Prime.");
        }
        else if (number.number == 0)
        {
            System.out.println("The number: 0 is Neither Prime nor Composite.");
        }
        else
        {
            System.out.println("The number: "+ number.number +" is not Prime.");
        }
    }
    public void isPrime(int[] numbers)
    {
        for (int number : numbers)
        {
            Thread c = new Thread(new Concurrency(number));
            c.start();
        }
        int count = numbers.length;
        Broadcaster instance = Broadcaster.getInstance();
        Concurrency temp;
        while (true)
        {
            if (count > 0)
            {
                temp = instance.get();
                printResultConcurrency(temp);
                --count;
            }
            else
            {
                break;
            }
        }
    }
}