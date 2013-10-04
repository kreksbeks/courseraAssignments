public class Solver
{
   private final Board initialPosition;
   private Iterable<Board> solution;
   private Boolean solvable;

   // find a solution to the initial board (using the A* algorithm)
   public Solver(Board initial)
   {
      this.initialPosition = initial;
   }

   // is the initial board solvable?
   public boolean isSolvable()
   {
      if (solvable != null) return solvable;

      Board twinPosition = initialPosition.twin();

      MinPQ<SearchNode> initialQueue = new MinPQ<SearchNode>();
      MinPQ<SearchNode> twinQueue = new MinPQ<SearchNode>();

      initialQueue.insert(new SearchNode(initialPosition, 0, null));
      twinQueue.insert(new SearchNode(twinPosition, 0, null));

      SearchNode stepInitBoard;
      SearchNode stepTwinBoard;
      boolean initBoardSolved;
      do
      {
         stepInitBoard = nextStep(initialQueue);
         stepTwinBoard = nextStep(twinQueue);
         initBoardSolved = stepInitBoard.position.isGoal();
      } while (!initBoardSolved && !stepTwinBoard.position.isGoal());

      if (initBoardSolved)
      {
         solution = reconstructSolution(stepInitBoard);
         solvable = true;
      }

      return initBoardSolved;
   }

   // min number of moves to solve initial board; -1 if no solution
   public int moves()
   {
      return initialPosition.hamming();
   }

   // sequence of boards in a shortest solution; null if no solution
   public Iterable<Board> solution()
   {
      return isSolvable() ? solution : null;

//      if (solution == null)
//      {
//         MinPQ<SearchNode> pq = new MinPQ<SearchNode>();
//         SearchNode lastStep = solve(pq, initialPosition);
//         solution = reconstructSolution(lastStep);
//      }
//
//      return solution;
   }

//   private SearchNode solve(MinPQ<SearchNode> pq, Board initialPosition1)
//   {
//      pq.insert(new SearchNode(initialPosition1, 0, null));
//
//      SearchNode step;
//      do
//      {
//         step = nextStep(pq);
//      } while (!step.position.isGoal());
//
//      return step;
//   }

   private SearchNode nextStep(MinPQ<SearchNode> pq)
   {
      SearchNode next = pq.delMin();

      for (Board neighbour : next.position.neighbors())
      {
         pq.insert(new SearchNode(neighbour, next.stepsNumber + 1, next));
      }

      return next;
   }

   private Stack<Board> reconstructSolution(SearchNode step)
   {
      Stack<Board> s = new Stack<Board>();
      do
      {
         s.push(step.position);
         step = step.prevStep;
      } while (step.prevStep != null);

      return s;
   }

   // solve a slider puzzle (given below)
   public static void main(String[] args)
   {
      // create initial board from file
      In in = new In(args[0]);
      int N = in.readInt();
      char[][] blocks = new char[N][N];
      for (int i = 0; i < N; i++)
         for (int j = 0; j < N; j++)
            blocks[i][j] = in.readInt();
      Board initial = new Board(blocks);

      // solve the puzzle
      Solver solver = new Solver(initial);

      // print solution to standard output
      if (!solver.isSolvable())
         StdOut.println("No solution possible");
      else {
         StdOut.println("Minimum number of moves = " + solver.moves());
         for (Board board : solver.solution())
            StdOut.println(board);
      }
   }

   private static class SearchNode
   {
      Board position;
      int stepsNumber;
      SearchNode prevStep;

      private SearchNode(Board position, int stepsNumber, SearchNode prevStep)
      {
         this.position = position;
         this.stepsNumber = stepsNumber;
         this.prevStep = prevStep;
      }
   }
}