public class Model {

    public static float sum(float a, float b){
        return a+b;
    }

    public static float multiple(float a, float b){
        return a*b;
    }

    public static float div(float a, float b) {
        return a/b;
    }

    public static float divOst(float a, float b){
        return a%b;
    }

    public static double divOne(float a, float b){
        return 1.0*(a/b);
    }

    public static float con(float a, float b){
        return a-b;
    }

    public static double pv(float a, float b){return Math.pow(a, b);}

    public static double mod(float a){return Math.abs(a);}
}
