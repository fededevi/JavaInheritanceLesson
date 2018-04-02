import java.util.ArrayList;

public class Expression {

    public static void main(String[] args) {
        Expression e = new Expression();

    }

    private ArrayList < Token > tokens = new ArrayList < > ();

    public Expression() {
        insert(new Operand("2"));
        insert(new Operand("3"));
        insert(new Add());
        insert(new Operand("2"));
        insert(new Operand("1"));
        insert(new Add());
        insert(new Operand("12"));
        insert(new Mul());
        insert(new Operand("2"));
        insert(new Sub());
        insert(new Operand("4"));
        insert(new Div());
        insert(new Operand("8"));
        insert(new Add());
        insert(new Operand("22"));
        System.out.println(toText());
        execute();
        System.out.println("Result: " + toText());

    }
    public void execute() {
        execute(true);
        execute(false);
    }

    private void execute(boolean withPriority) {
        ArrayList < Token > s1 = new ArrayList < > ();
        for (Token t: tokens) {
            int last = s1.size() - 1;
            s1.add(t);

            if (last < 1)
                continue;

            Token lT = s1.get(last);

            if (lT instanceof Operator && (!withPriority || ((Operator) lT).hasPriority())) {
                Operand right = (Operand) s1.remove(s1.size() - 1);
                Operator op = (Operator) s1.remove(s1.size() - 1);
                Operand left = (Operand) s1.remove(s1.size() - 1);
                Operand res = op.execute(left, right);
                System.out.println(left.toText() + op.toText() + right.toText() + "=" + res.toText());
                s1.add(res);
            }
        }
        tokens = s1;
    }

    public String toText() {
        String testoBellllo = "";

        for (Token t: tokens) {
            testoBellllo += t.toText() + " ";

        }

        return testoBellllo;
    }

    public void insert(Token t) {
        int last = tokens.size() - 1;
        if (last < 0) {
            tokens.add(t);
            return;
        }
        if (tokens.get(last) instanceof Operator &&
            t instanceof Operator) {
            tokens.remove(last);
            tokens.add(t);
            return;
        }

        if (tokens.get(last) instanceof Operand && t instanceof Operand) {
            ((Operand) tokens.get(last)).merge((Operand) t);
            return;
        }

        tokens.add(t);

    }

    public class Token {
        protected String tokenString;

        String toText() {
            return tokenString;
        }
    }

    public abstract class Operator extends Token {
        protected boolean hasPriority = false;
        public boolean hasPriority() {
            return hasPriority;
        }

        public abstract Operand execute(Operand left, Operand right);
    }

    public class Add extends Operator {
        public Operand execute(Operand left, Operand right) {
            double l = Double.parseDouble(left.toText());
            double r = Double.parseDouble(right.toText());
            String result = String.valueOf(l + r);
            return new Operand(result);
        }
        public Add() {
            this.tokenString = "+";
        }
    }

    public class Sub extends Operator {
        public Operand execute(Operand left, Operand right) {
            double l = Double.parseDouble(left.toText());
            double r = Double.parseDouble(right.toText());
            String result = String.valueOf(l - r);
            return new Operand(result);
        }
        public Sub() {
            this.tokenString = "-";
        }
    }

    public class Mul extends Operator {
        public Operand execute(Operand left, Operand right) {
            double l = Double.parseDouble(left.toText());
            double r = Double.parseDouble(right.toText());
            String result = String.valueOf(l * r);
            return new Operand(result);
        }
        public Mul() {
            hasPriority = true;
            this.tokenString = "*";
        }
    }

    public class Div extends Operator {
        public Operand execute(Operand left, Operand right) {
            double l = Double.parseDouble(left.toText());
            double r = Double.parseDouble(right.toText());
            String result = String.valueOf(l / r);
            return new Operand(result);
        }
        public Div() {
            hasPriority = true;
            this.tokenString = "/";
        }
    }

    public class Operand extends Token {

        public Operand(String s) {
            tokenString = s;
        }

        public void merge(Operand o) {
            tokenString = tokenString + o.toText();
        }
    }

}
