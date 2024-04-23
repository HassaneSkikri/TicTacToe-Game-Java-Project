import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TicTacToe extends JFrame implements ActionListener {
    private final JButton[] buttons = new JButton[9];
    private JButton btnDemarre;
    private JLabel scoreXLabel;
    private JLabel scoreOLabel;
    private int scoreX = 0;
    private int scoreO = 0;
    private int compteur = 0;
    private boolean start = false;

    private final Color colorX = new Color(65, 105, 225);
    private final Color colorO = new Color(220, 20, 60);
    private final Color couleurVide = new Color(255, 250, 250);
    private final Font policeJ = new Font("Arial", Font.BOLD, 60);
    private final Font policeScore = new Font("Arial", Font.PLAIN, 20);
    private final LineBorder gridBorder = new LineBorder(Color.GRAY, 2);

    public TicTacToe() {
        setTitle("Tic Tac Toe");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        JPanel topPanel = new JPanel(new FlowLayout());
        scoreXLabel = new JLabel("Score X: 0");
        scoreOLabel = new JLabel("Score O: 0");
        scoreXLabel.setFont(policeScore);
        scoreOLabel.setFont(policeScore);
        btnDemarre = new JButton("Start Game");
        btnDemarre.addActionListener(e -> startGame());

        topPanel.add(scoreXLabel);
        topPanel.add(btnDemarre);
        topPanel.add(scoreOLabel);
        add(topPanel, BorderLayout.NORTH);

        JPanel gridPanel = new JPanel(new GridLayout(3, 3, 2, 2));
        gridPanel.setBackground(Color.BLACK);
        gridPanel.setPreferredSize(new Dimension(400, 400));

        for (int i = 0; i < 9; i++) {
            buttons[i] = new JButton();
            buttons[i].setFont(policeJ);
            buttons[i].setBackground(couleurVide);
            buttons[i].setForeground(Color.BLACK);
            buttons[i].setBorder(gridBorder);
            buttons[i].setFocusPainted(false);
            buttons[i].addActionListener(this);
            buttons[i].setEnabled(false);
            gridPanel.add(buttons[i]);
        }

        add(gridPanel, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void startGame() {
        for (JButton button : buttons) {
            button.setEnabled(true);
            button.setText("");
        }
        compteur = 0;
        start = true;
        btnDemarre.setEnabled(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!start) {
            return;
        }
        JButton clickedButton = (JButton) e.getSource();
        if (clickedButton.getText().isEmpty()) {
            clickedButton.setText(compteur % 2 == 0 ? "X" : "O");
            clickedButton.setForeground(compteur % 2 == 0 ? colorX : colorO);
            clickedButton.setBackground(Color.WHITE);
            compteur++;
            checkForWin();
        }
    }

    private void checkForWin() {
        int[][] winConditions = new int[][] {
                {0, 1, 2}, {3, 4, 5}, {6, 7, 8},
                {0, 3, 6}, {1, 4, 7}, {2, 5, 8},
                {0, 4, 8}, {2, 4, 6}
        };

        boolean winner = false;
        String winningPlayer = "";

        for (int[] condition : winConditions) {
            if (!buttons[condition[0]].getText().equals("") &&
                    buttons[condition[0]].getText().equals(buttons[condition[1]].getText()) &&
                    buttons[condition[1]].getText().equals(buttons[condition[2]].getText())) {

                winningPlayer = buttons[condition[0]].getText();
                JOptionPane.showMessageDialog(null, winningPlayer + " winner!", "Game End", JOptionPane.INFORMATION_MESSAGE);
                winner = true;
                resetButtons();
                break;
            }
        }

        boolean draw = !winner && compteur == 9;
        if (draw) {
            JOptionPane.showMessageDialog(null, "Hmmmm... !", "Game End", JOptionPane.INFORMATION_MESSAGE);
            resetButtons();
        }

        if (winner) {
            if (winningPlayer.equals("X")) {
                scoreX++;
                scoreXLabel.setText("Score X: " + scoreX);
            } else {
                scoreO++;
                scoreOLabel.setText("Score O: " + scoreO);
            }
        }
    }


    private void resetButtons() {
        for (JButton button : buttons) {
            button.setText("");
            button.setBackground(couleurVide);
            button.setEnabled(false);
        }
        start = false;
        btnDemarre.setEnabled(true);
    }

    public static void main(String[] args) {
        new TicTacToe();
    }
}

