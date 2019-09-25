import java.util.AbstractList;
import java.util.ArrayList;

public class SortedList
{
    private ArrayList<Board> internalList;

    SortedList(){internalList = new ArrayList<>();}

    //add function is overwritten to use binary insertion rather than linear insertion
    public void add(Board b) {

        int left, right, mid;

        left = 0;
        right = internalList.size();


        while(left< right)  {
            mid = (left + right)/2;
            int result = internalList.get(mid).compareTo(b);


            if(result > 0) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }

        internalList.add(left, b);

    }

    public Board get(int i) {
        return internalList.get(i);
    }

    public Board next() {
        return internalList.remove(0);
    }

    public int size() {
        return internalList.size();
    }



}