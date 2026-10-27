import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * The Battleship window: title, 10 by 10 grid, status displays,
 * and the Play Again and Quit buttons.
 *
 * @author Kirby Fortney
 */
public class BattleshipFrame extends JFrame
{
    private Board board;
    private BattleshipGame game;

    private JTextField missField;
    private JTextField strikeField;
    private JTextField totalMissField;
    private JTextField totalHitField;

    public BattleshipFrame()
    {
        board = new Board();
        game = new BattleshipGame(board);

        setTitle("Battleship");
        setLayout(new BorderLayout(5, 5));

        // title
        JLabel titleLabel = new JLabel("BATTLESHIP", SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 32));
        add(titleLabel, BorderLayout.NORTH);

        // game board: one listener for all 100 cells
        JPanel gridPanel = new JPanel(new GridLayout(Board.SIZE, Board.SIZE));
        ActionListener cellListener = ae -> handleShot((Cell) ae.getSource());
        for (int row = 0; row < Board.SIZE; row++)
        {
            for (int col = 0; col < Board.SIZE; col++)
            {
                Cell cell = board.getCell(row, col);
                cell.addActionListener(cellListener);
                gridPanel.add(cell);
            }
        }
        add(gridPanel, BorderLayout.CENTER);

        add(createBottomPanel(), BorderLayout.SOUTH);

        updateStatus();

        setSize(650, 750);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private JPanel createBottomPanel()
    {
        // status displays
        JPanel statusPanel = new JPanel(new GridLayout(2, 4, 10, 5));
        statusPanel.setBorder(BorderFactory.createTitledBorder("Status"));

        missField = createStatusField();
        strikeField = createStatusField();
        totalMissField = createStatusField();
        totalHitField = createStatusField();

        statusPanel.add(new JLabel("Miss (0 to 5)", SwingConstants.CENTER));
        statusPanel.add(new JLabel("Strike (0 to 3)", SwingConstants.CENTER));
        statusPanel.add(new JLabel("Total Miss (0 to 83)", SwingConstants.CENTER));
        statusPanel.add(new JLabel("Total Hit (0 to 17)", SwingConstants.CENTER));
        statusPanel.add(missField);
        statusPanel.add(strikeField);
        statusPanel.add(totalMissField);
        statusPanel.add(totalHitField);

        // buttons
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        JButton playAgainButton = new JButton("Play Again");
        JButton quitButton = new JButton("Quit");
        playAgainButton.addActionListener(ae -> playAgain());
        quitButton.addActionListener(ae -> quit());
        buttonPanel.add(playAgainButton);
        buttonPanel.add(quitButton);

        JPanel bottomPanel = new JPanel(new BorderLayout(5, 5));
        bottomPanel.add(statusPanel, BorderLayout.CENTER);
        bottomPanel.add(buttonPanel, BorderLayout.SOUTH);
        return bottomPanel;
    }

    private JTextField createStatusField()
    {
        JTextField field = new JTextField("0");
        field.setEditable(false);
        field.setHorizontalAlignment(JTextField.CENTER);
        field.setFont(new Font("SansSerif", Font.BOLD, 18));
        return field;
    }

    /**
     * Fires at the clicked cell, shows the result and checks for a win or loss.
     *
     * @param cell the cell that was clicked
     */
    private void handleShot(Cell cell)
    {
        String result = game.fire(cell);

        if (cell.hasShip())
        {
            cell.showHit();
        }
        else
        {
            cell.showMiss();
        }
        updateStatus();

        switch (result)
        {
            case "SUNK":
                JOptionPane.showMessageDialog(this,
                        "You sunk a ship of size " + cell.getShip().getSize() + "!");
                break;
            case "WIN":
                JOptionPane.showMessageDialog(this, "You sunk the last ship. You win!");
                askToReplay();
                break;
            case "LOSS":
                JOptionPane.showMessageDialog(this, "That is 3 strikes. You lose!");
                askToReplay();
                break;
            default:
                break; // HIT, MISS and STRIKE only update the counters
        }
    }

    private void updateStatus()
    {
        missField.setText("" + game.getMissCount());
        strikeField.setText("" + game.getStrikeCount());
        totalMissField.setText("" + game.getTotalMiss());
        totalHitField.setText("" + game.getTotalHit());
    }

    private void askToReplay()
    {
        int answer = JOptionPane.showConfirmDialog(this, "Do you want to play again?",
                "Play Again?", JOptionPane.YES_NO_OPTION);

        if (answer == JOptionPane.YES_OPTION)
        {
            game.newGame();
            updateStatus();
        }
        else
        {
            System.exit(0);
        }
    }

    private void playAgain()
    {
        int answer = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to start a new game?",
                "Play Again", JOptionPane.YES_NO_OPTION);

        if (answer == JOptionPane.YES_OPTION)
        {
            game.newGame();
            updateStatus();
        }
    }

    private void quit()
    {
        int answer = JOptionPane.showConfirmDialog(this, "Are you sure you want to quit?",
                "Quit", JOptionPane.YES_NO_OPTION);

        if (answer == JOptionPane.YES_OPTION)
        {
            System.exit(0);
        }
    }
}
