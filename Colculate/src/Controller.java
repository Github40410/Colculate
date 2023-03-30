import java.util.ArrayList;

public class Controller {

    private static String v;
    private static ArrayList<String> arrayList = new ArrayList<>();
    private static ArrayList<String> stringArrayList = new ArrayList<>();

    public Controller(String v){
        this.v=v;
        this.arrayList.add(v);
    }

    public static ArrayList<String> getArrayList(){
        return arrayList;
    }

    public static void setStringArrayList(ArrayList<String> arr){
        stringArrayList = arr;
    }

    public static ArrayList<String> getStringArrayList(){
        return stringArrayList;
    }

    public static boolean ctF(){
        if(v==null){
            return false;
        }
        return true;
    }

    public static String sc(){

        int colLeft = 0;
        int colRight = 0;
        int indLef = 0;
        int indRight = 0;

        v = v.replaceAll(" ", "");
        for(int i=0; i<v.length(); i++){
            if(v.charAt(i)=='('&&colRight==0){
                colLeft++;
                indLef = i;
            }
            else if(v.charAt(i)=='('){
                colLeft++;
            }
            if(v.charAt(i)==')'&&colRight==0){
                indRight = i;
                colRight++;
            }
        }

        if(colLeft==0){
            return vrS(v);
        }

        if(indRight==v.length()-1){
            v = v.substring(0, indLef) + vrS(v.substring(indLef+1, indRight));
        }
        else {
            String substringRight = v.substring(indRight + 1);
            String substringLef = v.substring(0, indLef);
            v = substringLef + vrS(v.substring(indLef+1, indRight)) + substringRight;
        }
        if(colLeft>1){
            v = sc();
        }
        else return vrS(v);
        return v;
    }

    private static int[] find(String cl){
        int idFerst = 0;
        int idLeter = 0;
        for(int i = 0; i<cl.length(); i++){
            if((cl.charAt(i)=='*'||cl.charAt(i)=='/')&&idFerst==0){
                idFerst = i;
            }
            if((cl.charAt(i)=='+'||cl.charAt(i)=='-')&&idLeter==0){
                idLeter=i;
            }
        }
        int[] m = new int[2];
        m[0] = idFerst;
        m[1] = idLeter;
        return m;
    }

    private static String vrS(String cl){
        int idFerst = 0;
        int idLeter = 0;
        int idLef = 0;
        int idRight = 0;
        int idOneMod = 0;
        int idTwoMod = 0;
        int colMod = 0;

        cl = cl.replace("++", "+");
        cl = cl.replace("+-", "-");
        cl = cl.replace("-+", "-");
        cl = cl.replace("--", "+");

        for(int i =0; i<cl.length(); i++){
            if(cl.charAt(i)=='|'&&colMod==0){
                idOneMod = i;
                colMod++;
            }
            else if(cl.charAt(i)=='|'&&colMod!=0){
                idTwoMod = i;
                break;
            }
        }

        if(idTwoMod!=0){
            String sub = cl.substring(idOneMod+1, idTwoMod);
            sub = vrS(sub);
            sub = Model.mod(Float.parseFloat(sub)) + "";
            if(idOneMod==0&&idTwoMod==cl.length()-1){
                cl = vrS(sub);
            }
            else if(idOneMod==0&&idTwoMod!=cl.length()-1){
                sub = sub + cl.substring(idTwoMod+1);
                cl = vrS(sub);
            }
            else if(idOneMod!=0&&idTwoMod==cl.length()-1){
                sub = cl.substring(0, idOneMod) + sub;
                cl = vrS(sub);
            }
            else if(idOneMod!=0&&idTwoMod!=cl.length()-1){
                sub = cl.substring(0, idOneMod) + sub + cl.substring(idTwoMod+1);
                cl = vrS(sub);
            }
        }

        for(int i = 0; i<cl.length(); i++){
            if((cl.charAt(i)=='*'||cl.charAt(i)=='/'||cl.charAt(i)=='%'||cl.charAt(i)=='^')&&idFerst==0){
                idFerst = i;
            }
            if((cl.charAt(i)=='+'||cl.charAt(i)=='-')&&idLeter==0){
                idLeter = i;
            }
        }

        if(idFerst==0&&idLeter==0){
            return cl;
        }

        if(idFerst!=0){

            for(int i=idFerst; i>0; i--){
                if((cl.charAt(i)=='+'||cl.charAt(i)=='-')&&idLef==0){
                    idLef=i;
                    break;
                }
                if((cl.charAt(i)=='+'||cl.charAt(i)=='-')&&idLef==0){
                    idLef=i;
                    break;
                }
            }

            for(int i=idFerst+1; i<cl.length(); i++){
                if((cl.charAt(i)=='+'||cl.charAt(i)=='-'||cl.charAt(i)=='*'||cl.charAt(i)=='/')&&i==idFerst+1){
                    continue;
                }
                if(cl.charAt(i)=='+'||cl.charAt(i)=='-'||cl.charAt(i)=='*'||cl.charAt(i)=='/'){
                    idRight = i;
                    break;
                }
                idRight = i;
            }

            if(idLef==0&&idRight!=cl.length()-1){
                String substringLeft = cl.substring(0, idFerst);
                String substringRight = "";
                if(cl.charAt(idFerst+1)=='*'||cl.charAt(idFerst+1)=='/'){
                    substringRight = cl.substring(idFerst+2, idRight);
                }
                else {
                    substringRight = cl.substring(idFerst+1, idRight);
                }
                float a = Float.parseFloat(substringLeft);
                float b = Float.parseFloat(substringRight);
                double it;
                if (cl.charAt(idFerst)=='*'&&cl.charAt(idFerst+1)!='*'){
                    it = Model.multiple(a, b);
                    cl = it + cl.substring(idRight);
                    cl = vrS(cl);
                }
                else if (cl.charAt(idFerst)=='*'&&cl.charAt(idFerst+1)=='*'){
                    it = Model.pv(a, b);
                    cl = it + cl.substring(idRight);
                    cl = vrS(cl);
                }
                else if(cl.charAt(idFerst)=='^'){
                    it = Model.pv(a, b);
                    cl = it + cl.substring(idRight);
                    cl = vrS(cl);
                }
                else if(cl.charAt(idFerst)=='%'){
                    it = Model.divOst(a, b);
                    cl = it + cl.substring(idRight);
                    cl = vrS(cl);
                }
                else if(cl.charAt(idFerst)=='/'&&cl.charAt(idFerst+1)=='/'){
                    it = Model.divOne(a, b);
                    cl = it + cl.substring(idRight);
                    cl = vrS(cl);
                }
                else if(cl.charAt(idFerst)=='/'&&cl.charAt(idFerst+1)!='/'){
                    it = Model.div(a, b);
                    cl = it + cl.substring(idRight);
                    cl = vrS(cl);
                }
            }

            if(idLef==0&&idRight==cl.length()-1){
                String substringLeft = cl.substring(0, idFerst);
                String substringRight = "";
                if (cl.charAt(idFerst+1)=='/'||cl.charAt(idFerst+1)=='*'){
                    substringRight = cl.substring(idFerst+2);
                }
                else {
                    substringRight = cl.substring(idFerst + 1);
                }
                float a = Float.parseFloat(substringLeft);
                float b = Float.parseFloat(substringRight);
                double it;
                if (cl.charAt(idFerst)=='*'&&cl.charAt(idFerst+1)!='*'){
                    it = Model.multiple(a, b);
                    cl = it + "";
                    cl = vrS(cl);
                }
                else if (cl.charAt(idFerst)=='*'&&cl.charAt(idFerst+1)=='*'){
                    it = Model.pv(a, b);
                    cl = it + "";
                    cl = vrS(cl);
                }
                else if(cl.charAt(idFerst)=='^') {
                    it = Model.pv(a, b);
                    cl = it + "";
                    cl = vrS(cl);
                }
                else if(cl.charAt(idFerst)=='%') {
                    it = Model.divOst(a, b);
                    cl = it + "";
                    cl = vrS(cl);
                }
                else if(cl.charAt(idFerst)=='/'&&cl.charAt(idFerst+1)=='/'){
                    it = Model.divOne(a, b);
                    cl = it + "";
                    cl = vrS(cl);
                }
                else if(cl.charAt(idFerst)=='/'&&cl.charAt(idFerst+1)!='/'){
                    it = Model.div(a, b);
                    cl = it + "";
                    cl = vrS(cl);
                }
            }

            if(idLef!=0&&idRight==cl.length()-1){
                String substringLeft = cl.substring(idLef+1, idFerst);
                String substringRight = "";
                if (cl.charAt(idFerst+1)=='/'||cl.charAt(idFerst+1)=='*'){
                    substringRight = cl.substring(idFerst+2);
                }
                else {
                    substringRight = cl.substring(idFerst + 1);
                }
                float a = Float.parseFloat(substringLeft);
                float b = Float.parseFloat(substringRight);
                double it;
                if (cl.charAt(idFerst)=='*'&&cl.charAt(idFerst+1)!='*'){
                    it = Model.multiple(a, b);
                    cl = cl.substring(0, idLef+1) + it;
                    cl = vrS(cl);
                }
                else if (cl.charAt(idFerst)=='*'&&cl.charAt(idFerst+1)=='*'){
                    it = Model.pv(a, b);
                    cl = cl.substring(0, idLef+1) + it;
                    cl = vrS(cl);
                }
                else if(cl.charAt(idFerst)=='^') {
                    it = Model.pv(a, b);
                    cl = cl.substring(0, idLef+1) + it;
                    cl = vrS(cl);
                }
                else if(cl.charAt(idFerst)=='%') {
                    it = Model.divOst(a, b);
                    cl = cl.substring(0, idLef+1) + it;
                    cl = vrS(cl);
                }
                else if(cl.charAt(idFerst)=='/'&&cl.charAt(idFerst+1)=='/'){
                    it = Model.divOne(a, b);
                    cl = cl.substring(0, idLef+1) + it;
                    cl = vrS(cl);
                }
                else if(cl.charAt(idFerst)=='/'&&cl.charAt(idFerst+1)!='/'){
                    it = Model.div(a, b);
                    cl = cl.substring(0, idLef+1) + it;
                    cl = vrS(cl);
                }
            }

            if(idLef!=0&&idRight!=cl.length()-1){
                int[] mas = find(cl);
                idFerst = mas[0];
                idLeter = mas[1];
                if (idFerst==0&&idLeter==0){
                    return cl;
                }
                String substringLeft = cl.substring(idLef+1, idFerst);
                String substringRight = "";
                if (cl.charAt(idFerst+1)=='*'||cl.charAt(idFerst+1)=='/'){
                    substringRight = cl.substring(idFerst+2, idRight);
                }
                else{
                    substringRight = cl.substring(idFerst+1, idRight);
                }
                float a = Float.parseFloat(substringLeft);
                float b = Float.parseFloat(substringRight);
                double it;
                if (cl.charAt(idFerst)=='*'&&cl.charAt(idFerst+1)!='*'){
                    it = Model.multiple(a, b);
                    cl = cl.substring(0, idLef+1) + it + cl.substring(idRight);
                    cl = vrS(cl);
                }
                else if (cl.charAt(idFerst)=='*'&&cl.charAt(idFerst+1)=='*'){
                    it = Model.pv(a, b);
                    cl = cl.substring(0, idLef+1) + it + cl.substring(idRight);
                    cl = vrS(cl);
                }
                else if(cl.charAt(idFerst)=='^'){
                    it = Model.pv(a, b);
                    cl = cl.substring(0, idLef+1) + it + cl.substring(idRight);
                    cl = vrS(cl);
                }
                else if(cl.charAt(idFerst)=='%'){
                    it = Model.divOst(a, b);
                    cl = cl.substring(0, idLef+1) + it + cl.substring(idRight);
                    cl = vrS(cl);
                }
                else if(cl.charAt(idFerst)=='/'&&cl.charAt(idFerst+1)=='/'){
                    it = Model.divOne(a, b);
                    cl = cl.substring(0, idLef+1) + it + cl.substring(idRight);
                    cl = vrS(cl);
                }
                else if(cl.charAt(idFerst)=='/'&&cl.charAt(idFerst+1)!='/'){
                    it = Model.div(a, b);
                    cl = cl.substring(0, idLef+1) + it + cl.substring(idRight);
                    cl = vrS(cl);
                }
            }
        }

        if(idFerst==0){
            for(int i=idLeter-1; i>0; i--){
                if((cl.charAt(i)=='+'||cl.charAt(i)=='-')&&i==idLeter+1){
                    continue;
                }
                if((cl.charAt(i)=='+'||cl.charAt(i)=='-')&&idLef==0){
                    if(i-1>0){
                        if(cl.charAt(i-1)=='-'||cl.charAt(i-1)=='+'){
                            idLef=i-1;
                            break;
                        }
                    }
                    else if(i-1<0){
                        idLef=0;
                        break;
                    }
                    else {
                        idLef = i;
                        break;
                    }
                }
            }

            for(int i=idLeter+1; i<cl.length(); i++) {
                if (cl.charAt(i) == '+' || cl.charAt(i) == '-') {
                    if(i<cl.length()-1){
                        if(cl.charAt(i+1)=='-'&&cl.charAt(i+1)=='+'){
                            continue;
                        }
                        else {
                            idRight = i;
                            break;
                        }
                    }
                }
                idRight = i;
            }

            if(idLef==0&&idRight!=cl.length()-1){
                String substringLeft = cl.substring(0, idLeter);
                String substringRight = cl.substring(idLeter+1, idRight);
                float a = Float.parseFloat(substringLeft);
                System.out.println(substringRight);
                float b = Float.parseFloat(substringRight);
                float it;
                if (cl.charAt(idLeter)=='+'){
                    it = Model.sum(a, b);
                    cl = it + cl.substring(idRight);
                    cl = vrS(cl);
                }
                else {
                    it = Model.con(a, b);
                    cl = it + cl.substring(idRight);
                    cl = vrS(cl);
                }
            }

            if(idLef==0&&idRight==cl.length()-1){
                String substringLeft = cl.substring(0, idLeter);
                String substringRight = cl.substring(idLeter+1);
                float a = Float.parseFloat(substringLeft);
                float b = Float.parseFloat(substringRight);
                float it;
                if (cl.charAt(idLeter)=='+'){
                    it = Model.sum(a, b);
                    cl = it + "";
                    cl = vrS(cl);
                }
                else {
                    it = Model.con(a, b);
                    cl = it + "";
                    cl = vrS(cl);
                }
            }
        }
        return cl;
    }
}