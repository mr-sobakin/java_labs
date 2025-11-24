package labs.lab2.vc.matrixcompressor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class MainFrame extends JFrame {
    private final JTextField nField = new JTextField("4", 4);
    private final JTextArea original = new JTextArea(12, 30);
    private final JTextArea compressed = new JTextArea(12, 30);

    public MainFrame() {
        setTitle("Matrix Compressor");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        init();
        pack();
        setLocationRelativeTo(null);
    }

    private void init() {
        JPanel top = new JPanel();
        top.add(new JLabel("n:"));
        top.add(nField);
        JButton gen = new JButton("Generate");
        gen.addActionListener(this::onGenerate);
        top.add(gen);

        original.setEditable(false);
        compressed.setEditable(false);
        original.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        compressed.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));

        JPanel center = new JPanel(new GridLayout(1,2,8,8));
        center.add(new JScrollPane(original));
        center.add(new JScrollPane(compressed));

        getContentPane().setLayout(new BorderLayout(8,8));
        getContentPane().add(top, BorderLayout.NORTH);
        getContentPane().add(center, BorderLayout.CENTER);
    }

    private void onGenerate(ActionEvent e) {
        int n;
        try {
            n = Integer.parseInt(nField.getText().trim());
            if (n <= 0) throw new NumberFormatException();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Enter positive integer n.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int[][] a = MatrixModel.generateMatrix(n);
        int[][] b = MatrixModel.compressMatrix(a);
        original.setText(MatrixModel.matrixToString(a));
        compressed.setText(MatrixModel.matrixToString(b));
    }
}
