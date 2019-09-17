import java.util.Arrays;
import java.util.Random;


public class Board
{
    private Random r = new Random();
    private int stateID;
    private int parentID;
    private int[] board;
    private int gn;
    private int hn;
    private int fn;
    private int priorityValue;
    private int width;
    private int height;
    private int boardLen;

    public Board(int stateID, int parentID, int[] board, int width, int height)
    {
        this.stateID = stateID;
        this.parentID = parentID;
        this.board = board;
        this.width = width;
        this.height = height;
        this.boardLen = width*height;
    }

    public int getIndex(int n)
    {
        int x;
        for(x = 0;x<this.boardLen;x++)
        {
            if(this.board[x] == n)
            {
                break;
            }
        }
        return x;
    }

    public Board[] getNextStates(int nextID)
    {
        int ind = getIndex(0);
        
        int x = ind % this.width;
        int y = ind / this.width;
        Board[] nexMoves = {null,null,null,null};
        if(x != 0)
        {
            nextID+=1;
            int[] newBoard = this.board.clone();
            newBoard[ind] = newBoard[ind-1];
            newBoard[ind-1] = 0;
            nexMoves[0] = new Board(nextID,this.stateID,newBoard,this.width,this.height);
        }

        if(x != this.width-1)
        {
            nextID+=1;
            int[] newBoard = this.board.clone();
            newBoard[ind] = newBoard[ind + 1];
            newBoard[ind + 1] = 0;
            nexMoves[2] = new Board(nextID,this.stateID,newBoard,this.width,this.height);
        }
        if(y != 0)
        {
            nextID+=1;
            int[] newBoard = this.board.clone();
            newBoard[ind] = newBoard[ind-this.width];
            newBoard[ind-this.width] = 0;
            nexMoves[1] = new Board(nextID,this.stateID,newBoard,this.width,this.height);
        }

        if(y != this.height-1)
        {
            nextID+=1;
            int[] newBoard = this.board.clone();
            newBoard[ind] = newBoard[ind+this.width];
            newBoard[ind + this.width] = 0;
            nexMoves[3] = new Board(nextID,this.stateID,newBoard,this.width,this.height);
        }

            return nexMoves;
    }

    public void shuffle(int numberOfShuffles)
    {
        int prevMove = -1;
        Board[] next;
        for(int x = 0;x<numberOfShuffles;x++)
        {
            next = this.getNextStates(0);
            this.r.nextInt(4);

        }
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

    public int getPriorityValue()
    {
        return priorityValue;
    }

    @Override
    public String toString()
    {
        String ret = "";
        for(int y = 0; y<this.height;y++)
        {
            for(int x = 0; x<this.width;x++)
            {
                ret += (String.format("%2s",Integer.toString(this.board[x+y*this.width]))+" ");
            }
            ret = ret.substring(0,ret.length()-1)+"\n";
        }
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

    @Override
    public int hashCode()
    {
        return Arrays.hashCode(this.getBoard());
    }

}
