//Ruoyu Zhang

public class Airport {

    private enum State {
        OPEN,
        CLOSING,
        CLOSED
    }

    private int availableRunways;
    private int availableGates;
    private int aircraftAtAirport;
    private int waitingArrivals;
    private State state;

    public Airport() {
        this.availableRunways = Params.RUNWAYS;
        this.availableGates = Params.GATES;
        this.aircraftAtAirport = 0;
        this.waitingArrivals = 0;
        this.state = State.OPEN;
    }

    public synchronized boolean requestLanding() {
        // New arrivals are rejected once the airport is no longer open.
        if (state != State.OPEN) {
            return false;
        }

        waitingArrivals++;

        try {
            // Landing requires both a runway and a gate.
            while (state == State.OPEN && (availableRunways == 0 || availableGates == 0)) {
                wait();
            }

            if (state != State.OPEN) {
                return false;
            }

            availableRunways--;
            availableGates--;
            aircraftAtAirport++;
            return true;

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return false;

        } finally {
            waitingArrivals--;
        }
    }

    public synchronized void finishLanding() {
        // After landing, the runway becomes free but the gate remains occupied.
        availableRunways++;
        notifyAll();
    }

    public synchronized void requestTakeoff() {
        try {
            // During normal operation, arrivals have priority over departures.
            while (state == State.CLOSED
                    || availableRunways == 0
                    || (state == State.OPEN && waitingArrivals > 0 && availableGates > 0)) {
                wait();
            }

            availableRunways--;

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public synchronized void finishTakeoff() {
        // Takeoff releases both the runway and the gate.
        availableRunways++;
        availableGates++;
        aircraftAtAirport--;
        notifyAll();
    }

    public synchronized void beginClosing() {
        if (state == State.OPEN) {
            state = State.CLOSING;
            notifyAll();
        }
    }

    public synchronized void waitUntilEmptyAndClose() {
        try {
            // The airport fully closes only after all aircraft have departed.
            while (aircraftAtAirport > 0) {
                wait();
            }

            state = State.CLOSED;
            notifyAll();

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public synchronized void reopen() {
        state = State.OPEN;
        notifyAll();
    }
}
