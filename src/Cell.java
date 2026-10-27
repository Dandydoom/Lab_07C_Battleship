import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * One cell on the Battleship board. It is a JButton that knows its row,
 * its column, and the ship on it (if any).
 *
 * The symbols are drawn as icons so they keep their colors after the
 * button is disabled.
 *
 * @author Kirby Fortney
 */
public class Cell extends JButton
{
    private static final Color WATER = new Color(20, 40, 80);
    private static final Icon WAVE_ICON = makeIcon("~", new Color(135, 206, 250));
    private static final Icon MISS_ICON = makeIcon("M", Color.YELLOW);
    private static final Icon HIT_ICON = makeIcon("X", Color.RED);

    private int row;
    private int col;
    private Ship ship;

    /**
     * Creates a blank cell.
     *
     * @param row the row of the cell (0 to 9)
     * @param col the column of the cell (0 to 9)
     */
    public Cell(int row, int col)
    {
        super();
        this.row = row;
        this.col = col;
        setBackground(WATER);
        setOpaque(true);
        setFocusPainted(false);
        reset();
    }

    public int getRow()
    {
        return row;
    }

    public int getCol()
    {
        return col;
    }

    public Ship getShip()
    {
        return ship;
    }

    public void setShip(Ship ship)
    {
        this.ship = ship;
    }

    /**
     * Tells whether part of a ship is on this cell.
     *
     * @return true if a ship is here
     */
    public boolean hasShip()
    {
        return ship != null;
    }

    /**
     * Shows a red X and disables the cell.
     */
    public void showHit()
    {
        setSymbol(HIT_ICON);
        setEnabled(false);
    }

    /**
     * Shows a yellow M and disables the cell.
     */
    public void showMiss()
    {
        setSymbol(MISS_ICON);
        setEnabled(false);
    }

    /**
     * Makes the cell blank again for a new game.
     */
    public void reset()
    {
        ship = null;
        setSymbol(WAVE_ICON);
        setEnabled(true);
    }

    private void setSymbol(Icon icon)
    {
        setIcon(icon);
        setDisabledIcon(icon);
    }

    /**
     * Draws a symbol in a color as a small icon.
     */
    private static Icon makeIcon(String symbol, Color color)
    {
        int size = 32;
        BufferedImage image = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g.setFont(new Font("SansSerif", Font.BOLD, 24));
        g.setColor(color);
        FontMetrics metrics = g.getFontMetrics();
        int x = (size - metrics.stringWidth(symbol)) / 2;
        int y = (size - metrics.getHeight()) / 2 + metrics.getAscent();
        g.drawString(symbol, x, y);
        g.dispose();
        return new ImageIcon(image);
    }
}
