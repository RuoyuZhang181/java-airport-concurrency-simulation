//Ruoyu Zhang

public class Main {

    public static void main(String[] args) {

        // Create the airport
        Airport airport = new Airport();
        
        // Create the operator who opens and closes the airport
        new Thread(new Operator(airport), "Operator").start();

        // Continuously create arriving aircraft
        new Thread(() -> {
            int id = 0;
            while (true) {
                new Thread(new Aircraft(airport), "Aircraft_" + id++).start();
                Params.sleepRandom(Params.ARRIVAL_MIN, Params.ARRIVAL_MAX);  // arrival pressure
            }
        }).start();

    }
}
