// SWEN90004 2026 Assignment 1a
//
// The Main class controls the airport simulation. This file will not run until you have 
// implemented the Airport, Aircraft and Operator classes. As noted in the specification, 
// it may be simpler to start by just implementing the Airport and Aircraft classes first, 
// in which case you should temporarily comment out the line that starts the Operator thread.
//
// Nic Geard - ngeard@unimelb.edu.au
// 6 March 2026

//Ruoyu Zhang 1686756

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