# Java Airport Concurrency Simulation

## Project Overview

This project is a Java concurrency simulation of an airport landing and takeoff system.

The simulation models an airport with a fixed number of runways and gates. Aircraft arrive at the airport, request landing, use a runway, move to a gate, complete turnaround, request takeoff, use a runway again, and then depart from the airport.

The project focuses on concurrency control in Java. It uses low-level Java concurrency mechanisms such as threads, synchronized methods, wait, and notifyAll to manage shared airport resources safely.

## System Description

The airport contains two main limited resources:

Runways

Gates

Each runway can only be used by one aircraft at a time.

Each gate can only be occupied by one aircraft at a time.

An arriving aircraft must obtain both a runway and an available gate before landing. A departing aircraft must obtain a runway before taking off.

The system also includes an airport operator. The operator controls the opening and closing of the airport. During normal operation, both landing and takeoff are allowed. When the airport begins closing, no new aircraft are allowed to land, but aircraft already at the airport may continue to take off. The airport becomes fully closed after all aircraft have departed.

## Main Features

Simulates multiple aircraft using Java threads

Controls access to shared airport resources

Models limited runways and limited gates

Implements arrival priority during normal operation

Supports airport opening and closing behaviour

Generates trace messages showing aircraft and operator actions

Demonstrates Java monitor-based concurrency control

## Project Files

### Aircraft.java

Defines the aircraft behaviour. Each aircraft runs as a separate thread and performs actions such as requesting landing, landing, waiting at a gate, requesting takeoff, taking off, and departing.

### Airport.java

Defines the shared airport monitor. It manages runway availability, gate availability, aircraft landing, aircraft takeoff, arrival priority, and airport closing conditions.

### Operator.java

Defines the airport operator behaviour. The operator opens and closes the airport and waits until all aircraft have departed before the airport becomes fully closed.

### Params.java

Stores system parameters such as the number of runways, number of gates, timing values, and logging functions.

### Main.java

Runs the simulation by creating the airport, aircraft, and operator objects.

## Technologies Used

Java

Object-oriented programming

Multithreading

Low-level concurrency control

Synchronized methods

wait and notifyAll

Monitor-based design

## How to Compile

Open a terminal or command prompt in the project folder.

Compile the Java files with:

```bash
javac *.java
```

If there is no error message, the project has compiled successfully.

## How to Run

After compiling the files, run:

```bash
java Main
```

The program will print a trace of airport events to the command line.

## Example Output

The output may vary between runs because the simulation is concurrent and non-deterministic.

Example trace messages may include:

```text
[Aircraft_10] Requesting takeoff
[Aircraft_10] Taking off
[Aircraft_10] Departed
[Aircraft_12] Requesting landing
[Aircraft_12] Landing
[Aircraft_12] At gate
[Operator] Operator begins closing airport.
[Operator] Waiting for all aircraft to depart...
[Aircraft_12] Requesting takeoff
[Aircraft_12] Taking off
[Aircraft_12] Departed
[Operator] Airport is now fully closed.
```

## Concurrency Design

The main concurrency challenge is coordinating multiple aircraft that compete for a limited number of runways and gates.

The Airport class is responsible for protecting shared state. It uses synchronized methods to ensure that only one thread can update critical airport state at a time.

Aircraft that cannot proceed immediately wait until airport conditions change. When a runway, gate, or airport state changes, waiting aircraft are notified and allowed to check whether they can continue.

This design prevents two aircraft from using the same runway or gate at the same time.

## Safety Properties

One important safety property is that each runway and each gate can only be occupied by one aircraft at a time.

The implementation enforces this by storing the number of available runways and gates inside the shared Airport monitor. Aircraft must successfully acquire the required resources before landing or taking off. These resource counters are updated inside synchronized methods, which prevents race conditions.

## Skills Demonstrated

Java programming

Object-oriented design

Thread-based simulation

Concurrency control

Monitor design

Resource allocation

Safety and liveness reasoning

Command-line Java execution

## Author

Ruoyu Zhang
