import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;

public class Board
{
   private final int[][] blocks;
   private int N;

   // construct a board from an N-by-N array of blocks
   // (where blocks[i][j] = block in row i, column j)
   public Board(int[][] blocks)
   {
      this.blocks = blocks;
   }

   // board dimension N
   public int dimension()
   {

   }

   // number of blocks out of place
   public int hamming()
   {

   }

   // sum of Manhattan distances between blocks and goal
   public int manhattan()
   {

   }

   // is this board the goal board?
   public boolean isGoal()
   {
      N = blocks.length;
      for (int i = 0; i < N; i++)
      {
         for (int j = 0; j < N; j++)
         {
            if (blocks[i][j] != (i*N + j))
            {
               return false;
            }
         }
      }

      return true;
   }

   // a board obtained by exchanging two adjacent blocks in the same row
   public Board twin()
   {
      int[][] twin = Arrays.copyOf(blocks, blocks.length);

      for (int[] row : twin)
      {
         int prev = 0;
         for (int i = 0; i < row.length; i++)
         {
            int current = row[i];

            if (prev != 0 && current != 0)
            {
               row[i - 1] = current;
               row[i] = prev;
               break;
            }

            prev = current;
         }
      }

      return new Board(twin);
   }

   // all neighboring boards
   public Iterable<Board> neighbors()
   {
      Set<Board> neighbours = new TreeSet<Board>();

//      return neighbours;
   }

   // does this board equal y?
   @Override
   public boolean equals(Object y)
   {
      if (y == null) return false;

      if (Board.class != y.getClass()) return false;

      Board that = (Board) y;
      return Arrays.equals(blocks, that.blocks);
   }

   // string representation of the board (in the output format specified below)
   @Override
   public String toString()
   {
      StringBuilder s = new StringBuilder(blocks.length + "\n");

      for (int[] row : blocks)
      {
         for (int block : row)
         {
            s.append(" ").append(block);
         }

         s.append("\n");
      }

      return s.toString();
   }
}