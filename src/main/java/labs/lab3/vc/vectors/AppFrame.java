package labs.lab3.vc.vectors;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Random;

public class AppFrame extends JFrame {
    private final JTextArea out;
    private Vector[] vectors;
    private final Random rnd = new Random();

    public AppFrame() {
        super("Vectors demo");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 500);
        setLocationRelativeTo(null);

        out = new JTextArea();
        out.setEditable(false);
        out.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        JScrollPane scroll = new JScrollPane(out);

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton gen = new JButton("Generate vectors");
        JButton pairs = new JButton("Show pairs & angles");
        JButton demoArith = new JButton("Arithmetic demo");
        JButton incDec = new JButton("Increment/Decrement/Indexing demo");

        gen.addActionListener(this::onGenerate);
        pairs.addActionListener(this::onPairs);
        demoArith.addActionListener(this::onArithmetic);
        incDec.addActionListener(this::onIncDec);

        buttons.add(gen);
        buttons.add(pairs);
        buttons.add(demoArith);
        buttons.add(incDec);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(buttons, BorderLayout.NORTH);
        getContentPane().add(scroll, BorderLayout.CENTER);
    }

    private void onGenerate(ActionEvent e) {
        int m = 5;
        int dim = 3;
        int lb=-5, ub=5;
        vectors = new Vector[m];
        for (int i = 0; i < m; i++) {
            vectors[i] = Vector.random(3, lb, ub);
        }

        out.append("Generated " + m + " vectors (dim " + dim + "):\n");
        for (int i = 0; i < vectors.length; i++) {
            out.append("v" + i + " = " + vectors[i] + "\n");
        }
        out.append("\n");
    }

    private void onPairs(ActionEvent e) {
        if (vectors == null) {
            out.append("Generate vectors first.\n\n");
            return;
        }

        out.append("Pairwise dot products, magnitudes and angles (degrees):\n");
        for (int i = 0; i < vectors.length; i++) {
            for (int j = i + 1; j < vectors.length; j++) {
                Vector a = vectors[i];
                Vector b = vectors[j];
                double dot = a.dot(b);
                double magA = a.magnitude();
                double magB = b.magnitude();
                String angleStr;
                try {
                    double angleDeg = Math.toDegrees(a.angleBetween(b));
                    angleStr = String.format("%.2f", angleDeg);
                } catch (ArithmeticException ex) {
                    angleStr = "undefined";
                }
                out.append(String.format("v%d · v%d = %.3f, |v%d|=%.3f, |v%d|=%.3f, angle=%s\n",
                        i, j, dot, i, magA, j, magB, angleStr));
            }
        }
        out.append("\n");
    }

    private void onArithmetic(ActionEvent e) {
        if (vectors == null || vectors.length < 2) {
            out.append("Need at least 2 vectors (generate first).\n\n");
            return;
        }
        Vector a = vectors[0];
        Vector b = vectors[1];

        out.append("Arithmetic demo with v0 and v1:\n");
        out.append("v0 = " + a + "\n");
        out.append("v1 = " + b + "\n");
        out.append("v0 + v1 = " + a.add(b) + "\n");
        out.append("v0 - v1 = " + a.sub(b) + "\n");
        out.append("v0 * 2.5 = " + a.mul(2.5) + "\n");
        try {
            out.append("v1 / 2 = " + b.div(2.0) + "\n");
        } catch (ArithmeticException ex) {
            out.append("Division error: " + ex.getMessage() + "\n");
        }
        out.append("\n");
    }

    private void onIncDec(ActionEvent e) {
        if (vectors == null || vectors.length == 0) {
            out.append("Generate vectors first.\n\n");
            return;
        }

        Vector v = vectors[0].copy();

        out.append("Indexing and inc/dec demo on copy of v0:\n");
        out.append("orig = " + v + "\n");

        double original0 = v.get(0);
        out.append("v[0] = " + original0 + "\n");
        v.set(0, original0 + 9.9);
        out.append("after v.set(0, v[0] + 9.9): " + v + "\n");

        v.increment();
        out.append("after increment(): " + v + "\n");

        v.decrement();
        v.decrement();
        out.append("after decrement() twice: " + v + "\n\n");
    }
}
