import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FunctionSystem {

    private MachineClass machine = new MachineClass();

    public String inputX(){
        Scanner s = new Scanner(System.in);
        System.out.println("Input an x(in binary)->");
        String x = s.nextLine();

        return x;
    }

    public String inputY(){
        Scanner s = new Scanner(System.in);
        System.out.println("Input an y(in binary)->");
        String y = s.nextLine();

        return y;
    }

    public String compareXandY(){

        Character x0 = null;
        Character y0 = null;

        for(int i=0; i < machine.getTape().length(); i++){
            if (machine.getTape().length()==1) {break;}
            if (i==0){
                x0 = machine.read(0);
                y0=null;
            }
            if (machine.getTape().charAt(i)==' '){
                y0 = machine.read(i+1);
            }
            if (y0!=null) {
                i=-1;
                if (x0.compareTo(y0) != 0) {
                    if (x0.compareTo(y0) > 0) {
                        return "reject";
                    } else {
                        return "accept";
                    }

                }

            }

        }
        return "accept";
    }

    public String multiply(){
        Character x0 = null;
        Character y0 = null;

        List<StringBuilder> multiplies = new ArrayList<>();
        StringBuilder tmp = new StringBuilder();

        for (int i=machine.getTape().length()-1; i>machine.getTape().indexOf(" ") ;i--){
            y0 = machine.getIndex(i);
            for (int j=machine.getTape().indexOf(" ")-1; j>=0 ;j--){
                x0 = machine.getIndex(j);
                if (x0 == '1' && y0=='1') {tmp.append("1");}
                else {tmp.append("0");}
            }
            for(int k=1; k < machine.getTape().length()-i; k++)
            {
                tmp.append('0');
            }
            for (int k=1; k < i- machine.getTape().indexOf(" "); k++){
                tmp.insert(0,'0');
            }
            multiplies.add(tmp);
            tmp = new StringBuilder();
        }
        System.out.println("multiplies:");
        for (StringBuilder s:multiplies){
            System.out.println(s);
        }

        StringBuilder result = multiplies.get(0);
        for (int j=1; j < multiplies.size(); j++){
            Character inMind = null;
            for (int i=multiplies.get(j).length()-1; i >= 0; i--){
                if (result.charAt(i)=='1' && multiplies.get(j).charAt(i)=='1'){
                    if (inMind!=null){
                        result.setCharAt(i, '1');
                    } else {
                        result.setCharAt(i, '0');
                        inMind = '1';
                    }
                }
                else if(result.charAt(i)=='0' && multiplies.get(j).charAt(i)=='0'){
                    if (inMind!=null){
                        result.setCharAt(i, '1');
                        inMind = null;
                    } else { result.setCharAt(i, '0'); }
                } else {
                    if (inMind!=null){
                        result.setCharAt(i, '0');
                    } else {
                        result.setCharAt(i, '1');
                    }
                }
            }
            if (inMind!=null) {result.insert(0, inMind); }
        }
        return result.toString();
    }

    public String plusXY(){

        Character x0 = null;
        Character y0 = null;

        StringBuilder result = new StringBuilder();
        Character inMind = null;
        for (int i=0; i < machine.getTape().length(); i++){
            if (machine.getTape().length()==1) {break;}
            if (machine.getIndex(i)==' '){
                i--;
                x0 = machine.read(i);
            }
            if (machine.getTape().length()-1==i){
                y0 = machine.read(i);
                i=-1;

                Character plus = null;
                if (x0 == '1' && y0=='1'){
                    if (inMind!=null) {
                        plus = '1';
                    } else {
                        plus = '0';
                        inMind = '1';
                    }
                    inMind = '1';
                }
                else if (x0 == '0' && y0=='0'){
                    if (inMind!=null){
                        plus = '1';
                        inMind = null;
                    }else {
                        plus = '0';
                    }
                }
                else {
                    if (inMind!=null){
                        plus='0';
                    } else {
                        plus = '1';
                    }
                }
                result.insert(0, plus);
            }

        }
        if (inMind!=null) {result.insert(0, inMind); }
        return result.toString();
    }

    public void main(){
        StringBuilder x = new StringBuilder(inputX());
        StringBuilder y = new StringBuilder(inputY());
        if (x.length()<y.length()){
            int difference = y.length()-x.length();
            for (int i=0; i < difference; i++){
                x.setCharAt(0, '0');
            }
        }
        else if (x.length()>y.length()){
            int difference = x.length()-y.length();
            for (int i=0; i < difference; i++){
                y.insert(0, '0');
            }
        }
        machine.addTape(x.toString());
        machine.addTape(" ");
        machine.addTape(y.toString());
        if(compareXandY().equals("accept")){
            System.out.println("x <= y. Multiplying has begun...");
            machine.setTape(new StringBuilder());
            machine.addTape(x.toString());
            machine.addTape(" ");
            machine.addTape(y.toString());
            machine.addTape(" "+multiply());
            machine.printTape();
        } else {
            System.out.println("x > y. Adding has begun...");
            machine.setTape(new StringBuilder());
            machine.addTape(x.toString());
            machine.addTape(" ");
            machine.addTape(y.toString());
            String addition = plusXY();
            machine.setTape(new StringBuilder());
            machine.addTape(x.toString());
            machine.addTape(" ");
            machine.addTape(y.toString());
            machine.addTape(" " + addition);
            machine.printTape();
        }
    }


}
