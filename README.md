# Propagate Alerts

This app uses EdgeSys to develop a decentralized alert system that reduces latency close to the cloudlet where the alert originates, thereby helping increase response time for time-critical emergencies like earthquake response or accidents. This idea can further be expanded to include applications that require limited or close proximity propagation, such as tracking of person or object of interest using a network of cameras and nodes.


This application has two parts: code that runs on EdgeSys and a visual simulation of the algorithm. EdgeSys is a decentralized edge computing framework being developed at USC Information Sciences Institute and currently supports Apache/Twitter Heron. The visual simulation is built using Java Swing, and the nodes represent earthquake nodes receiving data from sensors. The nodes then run ElarmS algorithm to compute the predominant period for vertical component of ground movement, where the stream of input is random numbers. If the predominany period crosses a certain thershold, an alert is triggered that propagates through the network of nodes.



## Motivation

A centralized system where data from sensors or nodes gets transferred to a central server is high inefficient especially when it comes to situations which require immediate alerts close to the origin of the alert. Also, a centralized system is more prone to failure of connection to a specific node, especially if nodes are just connected to the server using only one medium. The `simulation.ipynb` Jupyter Notebook contains simulations that show that a decentralized system is more fault tolerant and also faster closer to the origin of the alert. Further, a decentralized system is easier to develop as there is no expensive infrastructure costs and if developed correctly then setting up a decetralized network is just as easy as spreading nodes in a large area with no physical interconnection between them.

## Simulations

If we compare a decentralized system with a centralized one where all nodes are connected to a single server, there is a noticeable improvement in fault tolerance. There is loss in latency of the notifying the complete network but if we consider the response time for each *depth* of nodes in a decentralized system with a centralized, there is a significant reduction in propagation time for nodes closer to the alert origin. This type of system is really useful in situations like earthquake early warning.

The decentralized system is divided into a network with random number of neighbors (more realistic) and one with fixed number of neighbors per node. 

The orange line in the graphs represent the centralized network of nodes with a server, the blue line is a decentralized graph with fixed number of neighbors and the green one is with random number of neighbors with the maximum neighbors not exceeding the number of neighbors in the network with fixed number of neighbors. All of the graphs contain 1000 nodes and the decentralized ones have 20 neighbors per node (at max that number for graph with random number of neighbors).

**Latency and failure rate represent relative numbers and are not accurate numbers of real world performance of any of the networks**

This is a visualization of the graphs that were used for simulations

<img src="images/plots/centralized.png?raw=true" height="350" width="350" alt="centralized network" align="left">
<img src="images/plots/decentralized.png?raw=true" height="350" width="350" alt="decentralized network" align"right">

<br>

Fault tolerance of the decentralized networks is significantly better than the centralized one. In this simulation, for centralized graph the rate of failure is multiplied with the normalized distance between nodes whereas in the decentralized network the rate is multipled with the normalized depth of the node.

<img src="images/plots/fault-tolerance.png?raw=true" height="350" width="350" alt="fault tolerance" align="center">

<br>

The rate of propagation of the decentralized graphs is significantly better than the centralized one closer to the alert origin, and this improvement is present with fixed and random number of neighbors in the decentralized network.

<img src="images/plots/propagation-fixed.png?raw=true" height="350" width="350" alt="propagation with fixed no. neighbors" align="left">
<img src="images/plots/propagation-random.png?raw=true" height="350" width="350" alt="propagation with random no. neighbors">

## Visualization

The Java application attached has two elements: the API and an example of the Earthquake Early Warning application that uses the API.

The API exapects two CSVs: one with the location of the compute nodes (vertices) and other with list of connections (edges).

The location of compute nodes is relative to the **maximum** x and y coordinates for the system of nodes (the maximum could be from two different nodes). We recommend using a grid of 10x10 while setting the location of nodes.

The simulation uses a map of California fetched from Apple Maps to show the location of nodes accross the map, but the data used is random numbers and the location of the nodes is also random right now. But both of them can be modified to use actual data using the CSV file interface used in the code. 

When the simulation is run, it generates text files which contain data from the Elarms implementation along with a window that visualizes the nodes and their propagation. The image below is a sample run of the application where the blue nodes are those which have not been alerted or are origin of alerts. The red ones are the alert origins and the orange ones are the nodes which have been alerted and are forwarding alerts.

<img src="images/plots/visualization.png?raw=true" height="350" width="350" alt="visualization">

---

Developed as a part of the FogSys project at the Information Sciences Institute (USC/ISI) led by Dr. JP Walters 
