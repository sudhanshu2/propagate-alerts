# Propagate Alerts

This app uses EdgeSys to develop a decentralized alert system that reduces latency close to the cloudlet where the alert originates, thereby helping increase response time for time-critical emergencies like earthquake response or accidents. This idea can further be expanded to include applications that require limited or close proximity propagation, such as tracking of person or object of interest using a network of cameras and nodes.

--- 

This application has two parts: code that runs on EdgeSys and a visual simulation of the algorithm. EdgeSys is a decentralized edge computing framework being developed at USC Information Sciences Institute and currently supports Apache/Twitter Heron. The visual simulation is built using Java Swing, and the nodes represent earthquake nodes receiving data from sensors. The nodes then run ElarmS algorithm to compute the predominant period for vertical component of ground movement, where the stream of input is random numbers. If the predominany period crosses a certain thershold, an alert is triggered that propagates through the network of nodes.

---

## Motivation

A centralized system where data from sensors or nodes gets transferred to a central server is high inefficient especially when it comes to situations which require immediate alerts close to the origin of the alert. Also, a centralized system is more prone to failure of connection to a specific node, especially if nodes are just connected to the server using only one medium. The `simulation.ipynb` Jupyter Notebook contains simulations that show that a decentralized system is more fault tolerant and also faster closer to the origin of the alert. Further, a decentralized system is easier to develop as there is no expensive infrastructure costs and if developed correctly then setting up a decetralized network is just as easy as spreading nodes in a large area with no physical interconnection between them.

---

The Java application attached has two elements: the API and an example of the Earthquake Early Warning application that uses the API.

The API exapects two CSVs: one with the location of the compute nodes (vertices) and other with list of connections (edges).

The location of compute nodes is relative to the **maximum** x and y coordinates for the system of nodes (the maximum could be from two different nodes). We recommend using a grid of 10x10 while setting the location of nodes.


---

Developed as a part of the FogSys project at the Information Sciences Institute (USC/ISI) led by Dr. JP Walters 