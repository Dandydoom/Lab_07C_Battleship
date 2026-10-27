/**
 * The rules of the game: keeps the counters and decides the result of each shot.
 *
 * @author Kirby Fortney
 */
public class BattleshipGame
{
    private static final int MISSES_PER_STRIKE = 5;
    private static final int STRIKES_TO_LOSE = 3;

    private Board board;
    private int missCount;
    private int strikeCount;
    private int totalMiss;
    private int totalHit;

    /**
     * Creates a game that is played on a board.
     *
     * @param board the board to play on
     */
    public BattleshipGame(Board board)
    {
        this.board = board;
        newGame();
    }

    /**
     * Fires a missile at a cell and updates the counters.
     *
     * @param cell the cell that was clicked
     * @return "HIT", "SUNK", "WIN", "MISS", "STRIKE" or "LOSS"
     */
    public String fire(Cell cell)
    {
        if (cell.hasShip())
        {
            totalHit++;
            missCount = 0;
            Ship ship = cell.getShip();
            ship.addHit();

            if (board.allShipsSunk())
            {
                return "WIN";
            }
            if (ship.isSunk())
            {
                return "SUNK";
            }
            return "HIT";
        }

        totalMiss++;
        missCount++;
        if (missCount == MISSES_PER_STRIKE)
        {
            strikeCount++;
            missCount = 0;
            if (strikeCount == STRIKES_TO_LOSE)
            {
                return "LOSS";
            }
            return "STRIKE";
        }
        return "MISS";
    }

    public int getMissCount()
    {
        return missCount;
    }

    public int getStrikeCount()
    {
        return strikeCount;
    }

    public int getTotalMiss()
    {
        return totalMiss;
    }

    public int getTotalHit()
    {
        return totalHit;
    }

    /**
     * Resets the counters and the board for a new game.
     */
    public void newGame()
    {
        missCount = 0;
        strikeCount = 0;
        totalMiss = 0;
        totalHit = 0;
        board.reset();
    }
}
