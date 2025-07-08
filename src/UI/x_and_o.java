package UI;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class x_and_o {

    ArrayList<Integer> playerOne = new ArrayList<>();
    ArrayList<Integer> playerTwo = new ArrayList<>();

    JFrame window = new JFrame("X And O Game");
    JPanel welcomePanel = new JPanel();
    JPanel inputPanel = new JPanel(new GridBagLayout());
    JPanel boardPanel = new JPanel(new GridLayout(3, 3));
    JPanel resultPanel = new JPanel(new FlowLayout());

    JTextField player1Field = new JTextField(15);
    JTextField player2Field = new JTextField(15);
    JButton continueButton = new JButton("Continue");
    JButton startButton = new JButton("Start Game");
    JButton backToMenuButton = new JButton("Back to Menu");

    String player1Name = "Player 1";
    String player2Name = "Player 2";

    JButton[] buttons = new JButton[9];
    int flag = 0;

    void showIntroScreen() {
        welcomePanel.setBackground(Color.BLACK);
        welcomePanel.setLayout(new BorderLayout());

        JLabel title = new JLabel("Welcome to Tic Tac Toe!", SwingConstants.CENTER);
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Serif", Font.BOLD, 32));
        welcomePanel.add(title, BorderLayout.CENTER);

        startButton.setFont(new Font("Serif", Font.BOLD, 20));
        startButton.setBackground(Color.BLUE);
        startButton.setForeground(Color.WHITE);
        welcomePanel.add(startButton, BorderLayout.SOUTH);

        window.add(welcomePanel);
        window.setSize(500, 400);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLocationRelativeTo(null);
        window.setVisible(true);

        animateText(title, "Welcome to Tic Tac Toe!");

        startButton.addActionListener(e -> {
            window.remove(welcomePanel);
            showWelcomePage();
        });
    }

    void animateText(JLabel label, String text) {
        final StringBuilder builder = new StringBuilder();
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            int index = 0;

            @Override
            public void run() {
                if (index < text.length()) {
                    builder.append(text.charAt(index));
                    label.setText(builder.toString());
                    index++;
                } else {
                    timer.cancel();
                }
            }
        }, 0, 100);
    }

    void showWelcomePage() {
        inputPanel.setBackground(Color.DARK_GRAY);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel nameLabel1 = new JLabel("Enter Name:");
        nameLabel1.setForeground(Color.WHITE);
        JLabel nameLabel2 = new JLabel("Enter Name:");
        nameLabel2.setForeground(Color.WHITE);

        player1Field.setFont(new Font("Arial", Font.PLAIN, 18));
        player2Field.setFont(new Font("Arial", Font.PLAIN, 18));
        continueButton.setFont(new Font("Arial", Font.BOLD, 18));
        continueButton.setBackground(Color.BLUE);
        continueButton.setForeground(Color.BLACK);

        gbc.gridx = 0;
        gbc.gridy = 0;
        inputPanel.add(nameLabel1, gbc);

        gbc.gridx = 1;
        inputPanel.add(player1Field, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        inputPanel.add(nameLabel2, gbc);

        gbc.gridx = 1;
        inputPanel.add(player2Field, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        inputPanel.add(continueButton, gbc);

        window.add(inputPanel);
        window.revalidate();
        window.repaint();

        continueButton.addActionListener(e -> {
            player1Name = player1Field.getText();
            player2Name = player2Field.getText();
            window.remove(inputPanel);
            drawGame();
        });
    }

    void drawGame() {
        boardPanel.removeAll();
        boardPanel.setBackground(Color.BLACK);

        for (int i = 0; i < 9; i++) {
            buttons[i] = new JButton("");
            buttons[i].setFont(new Font("Arial", Font.BOLD, 60));
            buttons[i].setBackground(Color.WHITE);
            final int index = i;
            buttons[i].addActionListener(e -> handleMove(index));
            boardPanel.add(buttons[i]);
        }

        backToMenuButton.setFont(new Font("Arial", Font.BOLD, 16));
        backToMenuButton.setBackground(Color.BLUE);
        backToMenuButton.addActionListener(e -> {
            window.remove(boardPanel);
            window.remove(resultPanel);
            showWelcomePage();
        });

        resultPanel.removeAll();
        resultPanel.setBackground(Color.BLACK);
        resultPanel.add(backToMenuButton);

        window.add(boardPanel, BorderLayout.CENTER);
        window.add(resultPanel, BorderLayout.SOUTH);
        window.revalidate();
        window.repaint();
    }

    void handleMove(int index) {
        if (!buttons[index].getText().equals("")) return;

        if (flag == 0) {
            buttons[index].setText("X");
            buttons[index].setForeground(Color.BLUE);
            playerOne.add(index);
            flag = 1;
        } else {
            buttons[index].setText("O");
            buttons[index].setForeground(Color.RED);
            playerTwo.add(index);
            flag = 0;
        }

        checkWinner();
    }

    void checkWinner() {
        int[][] winningCombos = {
                {0, 1, 2}, {3, 4, 5}, {6, 7, 8},
                {0, 3, 6}, {1, 4, 7}, {2, 5, 8},
                {0, 4, 8}, {2, 4, 6}
        };

        for (int[] combo : winningCombos) {
            if (playerOne.contains(combo[0]) && playerOne.contains(combo[1]) && playerOne.contains(combo[2])) {
                animateWinner(combo);
                JOptionPane.showMessageDialog(window, player1Name + " Wins!");
                resetGame();
                return;
            } else if (playerTwo.contains(combo[0]) && playerTwo.contains(combo[1]) && playerTwo.contains(combo[2])) {
                animateWinner(combo);
                JOptionPane.showMessageDialog(window, player2Name + " Wins!");
                resetGame();
                return;
            }
        }

        if (playerOne.size() + playerTwo.size() == 9) {
            JOptionPane.showMessageDialog(window, "It's a Draw!");
            resetGame();
        }
    }

    void animateWinner(int[] combo) {
        for (int index : combo) {
            buttons[index].setBackground(Color.YELLOW);
        }
    }

    void resetGame() {
        for (JButton button : buttons) {
            button.setText("");
            button.setEnabled(true);
            button.setBackground(Color.WHITE);
        }
        playerOne.clear();
        playerTwo.clear();
        flag = 0;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new x_and_o().showIntroScreen());
    }
}
