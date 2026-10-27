/**
 * A ship on the Battleship board. It knows its size and how many times it has been hit.
 *
 * @author Kirby Fortney
 */
public class Ship
{
    private int size;
    private int hits;

    /**
     * Creates a ship.
     *
     * @param size how many cells the ship takes up
     */
    public Ship(int size)
    {
        this.size = size;
        this.hits = 0;
    }

    /**
     * Gets the size of the ship.
     *
     * @return the number of cells the ship takes up
     */
    public int getSize()
    {
        return size;
    }

    /**
     * Records one hit on this ship.
     */
    public void addHit()
    {
        hits++;
    }

    /**
     * Tells whether every cell of the ship has been hit.
     *
     * @return true if the ship is sunk
     */
    public boolean isSunk()
    {
        return hits >= size;
    }

    /**
     * Clears the hits for a new game.
     */
    public void reset()
    {
        hits = 0;
    }
}
