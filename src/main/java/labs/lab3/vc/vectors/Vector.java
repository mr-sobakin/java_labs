package labs.lab3.vc.vectors;

public class Vector {
    protected final double[] elements;

    public Vector(int size) {
        if (size <= 0) throw new IllegalArgumentException("Size must be positive");
        this.elements = new double[size];
    }

    public Vector(double... elements) {
        if (elements == null || elements.length == 0) throw new IllegalArgumentException("Elements cannot be null or empty");
        this.elements = elements.clone();
    }

    public static Vector zero(int size) {
        return new Vector(size);
    }

    public static Vector random(int size) {
        Vector vector = new Vector(size);
        for (int i = 0; i < size; i++) {
            vector.set(i, Math.random());
        }
        return vector;
    }

    public static Vector random(int size, double min, double max) {
        Vector vector = new Vector(size);
        for (int i = 0; i < size; i++) {
            vector.set(i, Math.random() * (max - min) + min);
        }
        return vector;
    }

    public int size() {
        return elements.length;
    }

    public double get(int index) {
        this.checkIndex(index);
        return elements[index];
    }

    public void set(int index, double value) {
        this.checkIndex(index);
        elements[index] = value;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= elements.length) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + elements.length);
        }
    }

    public Vector add(Vector vector) {
        ensureSameSize(vector);
        double[] result = new double[this.size()];
        for (int i = 0; i < this.size(); i++) {
            result[i] = this.get(i) + vector.get(i);
        }
        return new Vector(result);
    }

    public Vector sub(Vector vector) {
        ensureSameSize(vector);
        double[] result = new double[this.size()];
        for (int i = 0; i < this.size(); i++) {
            result[i] = this.get(i) - vector.get(i);
        }
        return new Vector(result);
    }

    public Vector mul(double scalar) {
        double[] result = new double[this.size()];
        for (int i = 0; i < this.size(); i++) {
            result[i] = this.get(i) * scalar;
        }
        return new Vector(result);
    }

    public Vector div(double scalar) {
        if (scalar == 0) throw new ArithmeticException("Division by zero");
        double[] result = new double[this.size()];
        for (int i = 0; i < this.size(); i++) {
            result[i] = this.get(i) / scalar;
        }
        return new Vector(result);
    }

    public void increment() {
        for (int i = 0; i < this.size(); i++) {
            this.set(i, this.get(i) + 1);
        }
    }

    public void decrement() {
        for (int i = 0; i < this.size(); i++) {
            this.set(i, this.get(i) - 1);
        }
    }

    public Vector copy() {
        return new Vector(this.elements);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("Vector(");
        for (int i = 0; i < elements.length; i++) {
            sb.append(elements[i]);
            if (i < elements.length - 1) sb.append(", ");
        }
        sb.append(")");
        return sb.toString();
    }

    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Vector)) return false;
        Vector other = (Vector) obj;
        if (this.size() != other.size()) return false;
        for (int i = 0; i < this.size(); i++) {
            if (this.get(i) != other.get(i)) return false;
        }
        return true;
    }

    public double dot(Vector vector) {
        ensureSameSize(vector);
        double result = 0;
        for (int i = 0; i < this.size(); i++) {
            result += this.get(i) * vector.get(i);
        }
        return result;
    }

    public double magnitude() {
        double sum = 0;
        for (int i = 0; i < this.size(); i++) {
            sum += this.get(i) * this.get(i);
        }
        return Math.sqrt(sum);
    }

    public double angleBetween(Vector vector) {
        ensureSameSize(vector);
        double dotProduct = this.dot(vector);
        double magnitudes = this.magnitude() * vector.magnitude();
        if (magnitudes == 0) throw new ArithmeticException("Cannot calculate angle with zero magnitude vector");
        return Math.acos(dotProduct / magnitudes);
    }

    private void ensureSameSize(Vector vector) {
        if (this.size() != vector.size()) {
            throw new IllegalArgumentException("Vectors must be of the same size");
        }
    }
}
