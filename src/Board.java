import java.util.ArrayList;
import java.util.Random;

/**
 * The 10 by 10 Battleship board. It holds the cells and the ships,
 * and places the ships randomly.
 *
 * @author Kirby Fortney
 */
public class Board
{
    public static final int SIZE = 10;
    private static final int[] SHIP_SIZES = {5, 4, 3, 3, 2}; // largest first

    private Cell[][] cells = new Cell[SIZE][SIZE];
    private ArrayList<Ship> ships = new ArrayList<>();
    private Random rnd = new Random();

    /**
     * Creates the board, its cells and its ships, and places the ships.
     */
    public Board()
    {
        for (int row = 0; row < SIZE; row++)
        {
            for (int col = 0; col < SIZE; col++)
            {
                cells[row][col] = new Cell(row, col);
            }
        }
        for (int shipSize : SHIP_SIZES)
        {
            ships.add(new Ship(shipSize));
        }
        placeShips();
    }

    /**
     * Gets the cell at a row and column.
     *
     * @param row the row (0 to 9)
     * @param col the column (0 to 9)
     * @return the cell
     */
    public Cell getCell(int row, int col)
    {
        return cells[row][col];
    }

    /**
     * Places every ship on the board.
     */
    public void placeShips()
    {
        for (Ship ship : ships)
        {
            placeShip(ship);
        }
    }

    /**
     * Tells whether every ship has been sunk.
     *
     * @return true if all ships are sunk
     */
    public boolean allShipsSunk()
    {
        for (Ship ship : ships)
        {
            if (!ship.isSunk())
            {
                return false;
            }
        }
        return true;
    }

    /**
     * Clears the board and places the ships again for a new game.
     */
    public void reset()
    {
        for (int row = 0; row < SIZE; row++)
        {
            for (int col = 0; col < SIZE; col++)
            {
                cells[row][col].reset();
            }
        }
        for (Ship ship : ships)
        {
            ship.reset();
        }
        placeShips();
    }

    /**
     * Places one ship. Picks a random direction and a random row or column,
     * finds every spot in it with enough empty cells in a row, and picks one
     * of those spots at random. If there is no room, it tries another line.
     *
     * @param ship the ship to place
     */
    private void placeShip(Ship ship)
    {
        int size = ship.getSize();

        while (true)
        {
            boolean horizontal = rnd.nextBoolean();
            int line = rnd.nextInt(SIZE); // the row (horizontal) or column (vertical)

            // find every start position with enough empty cells
            ArrayList<Integer> starts = new ArrayList<>();
            for (int start = 0; start <= SIZE - size; start++)
            {
                boolean empty = true;
                for (int i = start; i < start + size; i++)
                {
                    Cell cell = horizontal ? cells[line][i] : cells[i][line];
                    if (cell.hasShip())
                    {
                        empty = false;
                        break;
                    }
                }
                if (empty)
                {
                    starts.add(start);
                }
            }

            if (!starts.isEmpty())
            {
                int start = starts.get(rnd.nextInt(starts.size()));
                for (int i = start; i < start + size; i++)
                {
                    Cell cell = horizontal ? cells[line][i] : cells[i][line];
                    cell.setShip(ship);
                }
                return;
            }
        }
    }
}
