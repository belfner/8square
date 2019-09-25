public class Main
{
    public static void main(String[] args)
    {
        //these if statements check for proper number of arguments
        if (args.length < 1)
        {
            System.out.println("No arguments passed");
            System.exit(1);
        }

        if (args.length < 3)
        {
            System.out.println("Arguments passed are incomplete");
            System.exit(1);
        }
        String sortingMethod = args[0];
        int width = Integer.parseInt(args[1]);
        int height = Integer.parseInt(args[2]);

        if (args.length != (3 + (2 * (width * height))))
        {
            System.out.println("Incorrect amount of tiles");
            System.exit(1);
        }

        int[] start = new int[width * height];
        int[] goal = new int[width * height];

        int startOffset = 3;
        int goalOffset = 3 + width * height;

        //arguments are transformed into lists to be passed to boards
        for (int x = 0; x < width * height; x++)
        {
            start[x] = Integer.parseInt(args[startOffset + x]);
        }

        for (int x = 0; x < width * height; x++)
        {
            goal[x] = Integer.parseInt(args[goalOffset + x]);
        }


        Board b = new Board(start, width, height);
        b.shuffle(60);
        System.out.println("Initial\n" + b);

        Board g = new Board(goal, width, height);
        System.out.println("Goal\n" + g);

        Solver s = new Solver(b.getBoard(), goal, width, height, sortingMethod);

        for(int x=0;x<5000000;x++)
        {
            if(s.step())
            {
                System.out.println("----------Solved----------\n");
                s.printPath();
                break;
            }
        }
    }

}
