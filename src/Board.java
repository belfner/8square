import java.util.Arrays;
import java.util.Random;


public class Board implements Comparable<Board>
{
    private Random r = new Random();
    private int stateID = 0;
    private int parentID = -1;
    private int[] board;
    private int gn = 0;
    private int hn = 0;
    private int fn = 0;
    private int priorityValue = 0;
    private int width;
    private int height;
    private int boardLen;
    private String hash = null;
    private Board parentBoard = null;
    private int depth = 0;
    private int move = -1;
    private boolean ltd = false;

    //Used for creating initial boards or boards that don't need to be modified
    public Board(int[] board, int width, int height)
    {
        this.board = board;
        this.width = width;
        this.height = height;
        boardLen = width * height;
        ltd = true;
    }

    public Board(int stateID, int parentID, int[] board, int width, int height, int gn, int depth, Board parent, int move)
    {
        this.stateID = stateID;
        this.parentID = parentID;
        this.board = board;
        this.width = width;
        this.height = height;
        this.gn = gn;
        this.depth = depth;
        this.move = move;
        boardLen = width * height;
        parentBoard = parent;


    }

    //used to find a numbers position on the board array
    public int getIndex(int n)
    {
        int x;
        for (x = 0; x < this.boardLen; x++)
        {
            if (this.board[x] == n)
            {
                break;
            }
        }
        return x;
    }

    public Board[] getNextStates(int nextID)
    {
        //gets the position of the blank square
        int ind = getIndex(0);

        //get x and y position of blank square based on position
        int x = ind % this.width;
        int y = ind / this.width;

        //move directions placed in a constant order
        Board[] nexMoves = {null, null, null, null};

        //each of these if blocks checks for possible moves
        //checks if left move is possible
        if (x != 0)
        {
            // next ID is iterated to give each new board a new and unique id
            nextID += 1;

            int[] newBoard = this.board.clone();

            //positions of tile to be moved and the blank are swapped
            newBoard[ind] = newBoard[ind - 1];
            newBoard[ind - 1] = 0;

            //checks if a tile 10 or greater was moved
            if (newBoard[ind] > 9)
            {                                                                                           //If it was 2 is added to the next square's g(n)
                nexMoves[0] = new Board(nextID, this.stateID, newBoard, this.width, this.height, this.gn + 2, depth + 1, this,0);
            } else
            {                                                                                          //If not, 1 is added to the next square's g(n)
                nexMoves[0] = new Board(nextID, this.stateID, newBoard, this.width, this.height, this.gn + 1, depth + 1, this,0);
            }
        }

        //Checks if right move is possible
        if (x != this.width - 1)
        {
            nextID += 1;

            int[] newBoard = this.board.clone();

            newBoard[ind] = newBoard[ind + 1];
            newBoard[ind + 1] = 0;

            if (newBoard[ind] > 9)
            {
                nexMoves[2] = new Board(nextID, this.stateID, newBoard, this.width, this.height, this.gn + 2, depth + 1, this,2);
            } else
            {
                nexMoves[2] = new Board(nextID, this.stateID, newBoard, this.width, this.height, this.gn + 1, depth + 1, this,2);
            }
        }
        //Checks if down move is possible
        if (y != 0)
        {
            nextID += 1;

            int[] newBoard = this.board.clone();

            newBoard[ind] = newBoard[ind - this.width];
            newBoard[ind - this.width] = 0;

            if (newBoard[ind] > 9)
            {
                nexMoves[1] = new Board(nextID, this.stateID, newBoard, this.width, this.height, this.gn + 2, depth + 1, this,1);
            } else
            {
                nexMoves[1] = new Board(nextID, this.stateID, newBoard, this.width, this.height, this.gn + 1, depth + 1, this,1);
            }
        }

        //Checks if up move is possible
        if (y != this.height - 1)
        {
            nextID += 1;

            int[] newBoard = this.board.clone();

            newBoard[ind] = newBoard[ind + this.width];
            newBoard[ind + this.width] = 0;

            if (newBoard[ind] > 9)
            {
                nexMoves[3] = new Board(nextID, this.stateID, newBoard, this.width, this.height, this.gn + 2, depth + 1, this,3);
            } else
            {
                nexMoves[3] = new Board(nextID, this.stateID, newBoard, this.width, this.height, this.gn + 1, depth + 1, this,3);
            }
        }

        return nexMoves;
    }

    //shuffles the board n times where each move will not cancel the previous
    public void shuffle(int numberOfShuffles)
    {
        int prevMove = -1;
        Board[] next;
        for (int x = 0; x < numberOfShuffles; x++)
        {
            next = this.getNextStates(0);
            int n = this.r.nextInt(4);
            while (n == prevMove || next[n] == null)
            {
                n = this.r.nextInt(4);
            }
            if (n == 0)
            {
                prevMove = 2;
            } else if (n == 1)
            {
                prevMove = 3;
            } else if (n == 2)
            {
                prevMove = 0;
            } else
            {
                prevMove = 1;
            }
            this.board = next[n].board;
        }
    }

    public void setGn(int gn)
    {
        this.gn = gn;
    }

    public void setHn(int hn)
    {
        this.hn = hn;
    }

    //for both A* heuristics the value of f(n) an the priority value is the same
    public void setFnAndPriority(int v)
    {
        fn = v;
        priorityValue = v;
    }

    public void setPriorityValue(int priorityValue)
    {
        this.priorityValue = priorityValue;
    }

    public int getStateID()
    {
        return stateID;
    }

    public int getParentID()
    {
        return parentID;
    }

    public int[] getBoard()
    {
        return board;
    }


    public int getGn()
    {
        return gn;
    }

    public int getHn()
    {
        return hn;
    }

    public int getFn()
    {
        return fn;
    }

    public int getDepth()
    {
        return depth;
    }


    public int getPriorityValue()
    {
        return priorityValue;
    }

    public Board getParentBoard()
    {
        return parentBoard;
    }

    @Override
    public String toString()
    {
        String ret = "Move: ";

        if(move == 0)
        {
            ret+="LEFT\n";
        }
        else if(move == 2)
        {
            ret+="RIGHT\n";
        }
        else if(move == 1)
        {
            ret+="DOWN\n";
        }
        else if(move == 3)
        {
            ret+="UP\n";
        }
        ret+=("ID: " +stateID);
        if(!ltd)
        {
            ret+=("\nParent ID: "+parentID);
        }
        ret+="\n";
        for (int y = 0; y < this.height; y++)
        {
            for (int x = 0; x < this.width; x++)
            {
                ret += (String.format("%2s", Integer.toString(this.board[x + y * this.width])) + " ");
            }
            ret = ret.substring(0, ret.length() - 1) + "\n";
        }
        if(!ltd)
        {
            ret += ("Depth: " + Integer.toString(depth) + "\n");
            ret += ("G(n): " + Integer.toString(gn) + "\n");
            ret += ("H(n): " + Integer.toString(hn) + "\n");
            ret += ("F(n): " + Integer.toString(fn) + "\n");
            ret += ("Priority Value: " + Integer.toString(priorityValue) + "\n");
        }
        ret+="--------------------------";
        return ret;
    }


    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (!(o instanceof Board)) return false;
        Board board1 = (Board) o;
        return Arrays.equals(getBoard(), board1.getBoard());
    }
    //used with hash array
    //turns board into a string and returns it
    //hash is saved for successive calls
    public String hash()
    {
        if (hash != null)
        {
            return hash;
        }
        String s = "";
        for (int x = 0; x < width * height; x++)
        {
            s += Integer.toString(board[x]);
        }
        hash = s;
        return s;
    }

    @Override
    public int compareTo(Board b)
    {
        if (priorityValue > b.getPriorityValue())
        {
            return 1;
        } else if (priorityValue < b.getPriorityValue())
        {
            return -1;
        } else
        {
            return 0;
        }
    }


}
