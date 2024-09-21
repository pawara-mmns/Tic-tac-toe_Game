import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Example extends JFrame implements ActionListener {
    private JButton[][] buttons = new JButton[3][3];
    private JButton resetButton;
    private JLabel statusLabel;
    private JLabel scoreLabel;
    private boolean xTurn = true;
    private int movesCount = 0;
    private int xScore = 0;
    private int oScore = 0;
    private Color xColor = new Color(41, 128, 185); 
    private Color oColor = new Color(231, 76, 60);   
    private Color bgColor = new Color(236, 240, 241); 
    private Color buttonColor = new Color(189, 195, 199); 

    public Example() {
        setTitle("Tic-Tac-Toe");
        setSize(400, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(bgColor);

        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(bgColor);
        JLabel titleLabel = new JLabel("Tic-Tac-Toe");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 32));
        titleLabel.setForeground(new Color(52, 73, 94));
        titlePanel.add(titleLabel);
        add(titlePanel, BorderLayout.NORTH);

        JPanel gamePanel = new JPanel(new GridLayout(3, 3, 10, 10));
        gamePanel.setBackground(bgColor);
        gamePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j] = new JButton("");
                buttons[i][j].setFont(new Font("Arial", Font.BOLD, 60));
                buttons[i][j].setFocusPainted(false);
                buttons[i][j].setBackground(buttonColor);
                buttons[i][j].setBorder(BorderFactory.createLineBorder(bgColor, 2));
                buttons[i][j].addActionListener(this);
                gamePanel.add(buttons[i][j]);
            }
        }
        add(gamePanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new GridLayout(3, 1, 5, 5));
        bottomPanel.setBackground(bgColor);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));

        statusLabel = new JLabel("X's turn", SwingConstants.CENTER);
        statusLabel.setFont(new Font("Arial", Font.BOLD, 24));
        statusLabel.setForeground(xColor);
        bottomPanel.add(statusLabel);

        scoreLabel = new JLabel("X: 0 | O: 0", SwingConstants.CENTER);
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 20));
        scoreLabel.setForeground(new Color(52, 73, 94)); 
        bottomPanel.add(scoreLabel);

        resetButton = new JButton("Reset Game");
        resetButton.setFont(new Font("Arial", Font.BOLD, 18));
        resetButton.setFocusPainted(false);
        resetButton.setBackground(new Color(46, 204, 113)); 
        resetButton.setForeground(Color.WHITE);
        resetButton.addActionListener(this);
        bottomPanel.add(resetButton);

        add(bottomPanel, BorderLayout.SOUTH);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == resetButton) {
            resetGame();
            return;
        }

        JButton clickedButton = (JButton) e.getSource();
        if (clickedButton.getText().equals("")) {
            if (xTurn) {
                clickedButton.setText("X");
                clickedButton.setForeground(xColor);
                statusLabel.setText("O's turn");
                statusLabel.setForeground(oColor);
            } else {
                clickedButton.setText("O");
                clickedButton.setForeground(oColor);
                statusLabel.setText("X's turn");
                statusLabel.setForeground(xColor);
            }
            movesCount++;
            
            if (checkWin()) {
                String winner = xTurn ? "X" : "O";
                statusLabel.setText(winner + " wins!");
                statusLabel.setForeground(xTurn ? xColor : oColor);
                updateScore(xTurn);
                disableButtons();
            } else if (movesCount == 9) {
                statusLabel.setText("It's a draw!");
                statusLabel.setForeground(Color.BLACK);
            } else {
                xTurn = !xTurn;
            }
        }
    }

    private boolean checkWin() {
        String[][] board = new String[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = buttons[i][j].getText();
            }
        }

       
        for (int i = 0; i < 3; i++) {
            if (board[i][0].equals(board[i][1]) && board[i][1].equals(board[i][2]) && !board[i][0].equals("")) {
                highlightWinningLine(i, 0, i, 1, i, 2);
                return true;
            }
            if (board[0][i].equals(board[1][i]) && board[1][i].equals(board[2][i]) && !board[0][i].equals("")) {
                highlightWinningLine(0, i, 1, i, 2, i);
                return true;
            }
        }
        if (board[0][0].equals(board[1][1]) && board[1][1].equals(board[2][2]) && !board[0][0].equals("")) {
            highlightWinningLine(0, 0, 1, 1, 2, 2);
            return true;
        }
        if (board[0][2].equals(board[1][1]) && board[1][1].equals(board[2][0]) && !board[0][2].equals("")) {
            highlightWinningLine(0, 2, 1, 1, 2, 0);
            return true;
        }
        return false;
    }

    private void highlightWinningLine(int r1, int c1, int r2, int c2, int r3, int c3) {
        buttons[r1][c1].setBackground(new Color(46, 204, 113)); 
        buttons[r2][c2].setBackground(new Color(46, 204, 113));
        buttons[r3][c3].setBackground(new Color(46, 204, 113));
    }

    private void updateScore(boolean xWin) {
        if (xWin) {
            xScore++;
        } else {
            oScore++;
        }
        scoreLabel.setText("X: " + xScore + " | O: " + oScore);
    }

    private void resetGame() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText("");
                buttons[i][j].setEnabled(true);
                buttons[i][j].setBackground(buttonColor);
            }
        }
        xTurn = true;
        movesCount = 0;
        statusLabel.setText("X's turn");
        statusLabel.setForeground(xColor);
    }

    private void disableButtons() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (buttons[i][j].getText().equals("")) {
                    buttons[i][j].setEnabled(false);
                }
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Example game = new Example();
            game.setVisible(true);
        });
    }
}
