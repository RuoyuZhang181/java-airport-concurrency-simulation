//Ruoyu Zhang

public class Aircraft implements Runnable {

    private final Airport airport;

    public Aircraft(Airport airport) {
        this.airport = airport;
    }

    @Override
    public void run() {
        boolean landed = false;

        // Retry landing until the airport is open and resources are available.
        while (!landed) {
            Params.log("Requesting landing");
            landed = airport.requestLanding();

            if (!landed) {
                Params.log("Cannot land, airport closed");
                Params.sleep(Params.WAIT_ON_CLOSURE);
            }
        }

        Params.log("Landing");
        Params.sleepRandom(Params.LANDING_MIN, Params.LANDING_MAX);
        airport.finishLanding();

        Params.log("At gate");
        Params.sleepRandom(Params.TURNAROUND_MIN, Params.TURNAROUND_MAX);

        Params.log("Requesting takeoff");
        airport.requestTakeoff();

        Params.log("Taking off");
        Params.sleepRandom(Params.TAKEOFF_MIN, Params.TAKEOFF_MAX);
        airport.finishTakeoff();

        Params.log("Departed");
    }
}
