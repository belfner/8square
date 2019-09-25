import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Solver
{
    private int width;
    private int height;
    private SortedList open;
    private Map<String, Board> closed;
    private Board start;
    private Board goal;
    private Board finalBoard;
    private Boolean pathFound;
    private int currID;
    private String sortingMethod;
    private int boardLength;


    public Solver(int[] start, int[] goal, int width, int height, String sortingMethod)
    {
        this.width = width;
        this.height = height;
        boardLength = width * height;
        this.sortingMethod = sortingMethod;
        this.start = new Board(start, width, height);
        this.goal = new Board(goal, width, height);
        pathFound = false;
        currID = 0;
        open = new SortedList();
        closed = new HashMap<>();
        addToOpen(this.start);

    }

    //pulls the next board from the opened list
    private Board getNext()
    {
        Board next = open.next();
        //since there are no checks for duplicate boards, each board is checked to see if it is in the closed list
        //if it is, then the next board is selected
        while (closed.containsKey(next.hash()))
        {
            next = open.next();
        }
        return next;
    }

    //preforms one iteration of the search algorithm
    public boolean step()
    {
        //the next unchecked board is pulled from the open list
        Board b = getNext();

        //the next possible board states are found
        Board[] n = b.getNextStates(currID);
        currID += 4;

        //each new board is sent to the new board function to be evaluated and added to the open list
        for (Board nb : n)
        {
            if (nb != null)
            {
                addToOpen(nb);
            }
        }

        //adds current board to the closed list
        closed.put(b.hash(), b);

        //returns weather a path has been found
        return pathFound;
    }

    //calculates hamming distance
    private int boardComparison1(Board b0, Board b1)
    {
        int score = 0;
        int[] a0 = b0.getBoard();
        int[] a1 = b1.getBoard();
        for (int x = 0; x < boardLength; x++)
        {
            if (a1[x] != 0 && a1[x] != a0[x])
            {
                if (a1[x] > 9)
                {
                    score += 2;
                } else
                {
                    score++;
                }
            }
        }
        return score;
    }
    // calculates manhattan distance
    private int boardComparison2(Board b0, Board b1)
        {
            int score = 0;
            int[] a0 = b0.getBoard();
            int[] a1 = b1.getBoard();
            int dist;
            int y, x;
            for (x = 0; x < boardLength; x++)
            {

                if (a1[x] != 0)
                {
                    dist = 0;
                    for (y = 0; y < boardLength; y++)
                    {
                        if (a0[y] == a1[x])
                        {
                            break;
                        }
                    }
                    dist += Math.abs(y % width - x % width);
                    dist += Math.abs(y / width - x / width);
                    if (a1[x] > 9)
                    {
                        score += 2 * dist;
                    } else
                    {
                        score += dist;
                    }
                }


            }
            return score;
    }

    private int boardComparison3(Board b0, Board b1)
    {
        int score = 0;
        int[] a0 = b0.getBoard();
        int[] a1 = b1.getBoard();
        int dist;
        int y, x;
        for (x = 0; x < boardLength; x++)
        {

            if (a1[x] != 0)
            {
                dist = 0;
                for (y = 0; y < boardLength; y++)
                {
                    if (a0[y] == a1[x])
                    {
                        break;
                    }
                }
                dist += Math.abs(y % width - x % width);
                dist += Math.abs(y / width - x / width);
                score+=dist;
            }


        }
        return score;
    }

    //calculates priority value, checks for equality for the
    private void addToOpen(Board b)
    {
        //checks for type of search method then calculates priority value along with other required values for the search type
        if (sortingMethod.equals("Bfs"))
        {
            b.setPriorityValue(b.getDepth());
        } else if (sortingMethod.equals("A*1"))
        {
            b.setHn(boardComparison1(goal, b));
            b.setFnAndPriority(b.getGn() + b.getHn());
        } else if (sortingMethod.equals("A*2"))
        {
            b.setHn(boardComparison2(goal, b));
            b.setFnAndPriority(b.getGn() + b.getHn());
        }
        else if (sortingMethod.equals("A*3"))
        {
            b.setHn(boardComparison3(goal, b));
            b.setFnAndPriority(b.getDepth() + b.getHn());
        }

        //checks for equality with goal state
        if (b.equals(goal))
        {
            pathFound = true;
            finalBoard = b;
            return;
        }
        open.add(b);
    }

    //prints path found
    public void printPath()
    {
        ArrayList<Board> solutionPath = new ArrayList<>();
        solutionPath.add(finalBoard);
        //since each board has a reference to its parent the previous step can always be found
        Board parent = finalBoard.getParentBoard();

        //since the initial board's parent is null each board is check for that value to indicate when to stop
        while (parent != null)
        {
            solutionPath.add(0, parent);
            parent = parent.getParentBoard();
        }

        System.out.println("START");
        for (Board b : solutionPath)
        {
            System.out.println(b);
        }
        System.out.println("Solution length: " + (solutionPath.size() - 1));
        System.out.println("Items added to open list: " + (open.size()+closed.size()));
        System.out.println("Items added to closed list: " + closed.size());
    }

    public void printOpened()
    {
        while (open.size() > 0)
        {
            System.out.println(open.next());
        }
    }
}
