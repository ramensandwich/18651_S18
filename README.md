# 18651_S18

## Simulator

Contains the Java programs that simulate 
To run simulator, open Simulator directory in IDE of choice and run Main.java

## data

Contains all of the JSON files that were output by the simulator and are needed by the jobs. Information includes user locations at various meal times, their purchasing history, and the points of interest used by the simulator.

## jobs

Contains the python scripts for visualizing our data and finding optimal vending machine placement. These programs all require python 2.7, matplotlib, numpy, and pandas.

### movement_visualizer.py
This job visualizes our movement dataset to allow us to verify that customer movement is realistic in our simulation. It renders the simulation's points of interest and animates user motion at a timestep of 5 seconds. To change the dataset, change the input to the breakfastMovementFile variable.

### kmeans.py
Given a number of desired machines and a list of [x,y,value] tuples, determines the optimal location for a vending machine using Lloyd's algorithm. The [x,y,value] tuple is a customer's location and how much money they spend on average per day. This program performs its own visualization using a voronoi diagram overlaid on the CMU campus. Note: k must be greater than 0.

### test.py
Our test program for piping simulation output into our kmeans tool. By default it's using the lunch dataset. To use another time of day, replace instances of "lunchMovementFile" with your desired time of day, ex "breakfastMovementFile". To change the number of clusters to be used in kmeans, adjust the first parameter in the call to kmeans().

## media

Contains images used by the visualizers and those that were used in our reports.

## nCPS_Server

Contains the ASP.NET Core server that receives and serves all of our simulation data. To open, use Visual Studio (VS17 is preferred, community edition is acceptable) to open the .sln file. Should be able to run immediately.
