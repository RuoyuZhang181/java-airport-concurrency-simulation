//Ruoyu Zhang

public class Operator implements Runnable {

    private final Airport airport;

    public Operator(Airport airport) {
        this.airport = airport;
    }

    @Override
    public void run() {
        while (true) {
            Params.log("Airport is open. Normal operation.");
            Params.sleep(Params.OPEN_DURATION);

            Params.log("Operator begins closing airport.");
            airport.beginClosing();

            Params.log("Waiting for all aircraft to depart...");
            airport.waitUntilEmptyAndClose();

            Params.log("Airport is now fully closed.");
            Params.sleep(Params.CLOSED_DURATION);

            // Start the next operating cycle.
            airport.reopen();
        }
    }
}
