public class Main
{
    public static void main(String[] args)
    {
        int[] h = {2,1,0,3,4,5};
        Board b = new Board(0, 0, h,3, 2);
        Board[] n = b.getNextStates(0);
        System.out.println(b);
        for(int x = 0; x< 4; x++)
        {
            if(n[x] != null)
            {
                System.out.println(x);
                System.out.println(n[x]);
                System.out.println();
            }
        }


    }

}
