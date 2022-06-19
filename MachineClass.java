public class MachineClass {

    //States


    private StringBuilder tape = new StringBuilder();


    public Character getIndex(int index){
        return tape.charAt(index);
    }


    public StringBuilder getTape() {
        return tape;
    }

    public void setTape(StringBuilder tape) {
        this.tape = tape;
    }

    public Character read(int index){

        Character needed = tape.charAt(index);
        tape.deleteCharAt(index);
        return needed;
    }

    public void addTape(String elem){
        tape.append(elem);
    }

    public void printTape(){
        System.out.println("Tape:");
        System.out.println(tape);
    }
}
