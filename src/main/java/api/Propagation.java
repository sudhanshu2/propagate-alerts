package api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.*;

public class Propagation {
    private static final boolean USE_EDGESYS;
    private static final int DISTANCE_THRESHOLD;
    private static HashMap<Integer, List<Integer>> adjacencyList;
    private static ArrayList<Node> nodeArray;
    static {
        adjacencyList = new HashMap<>();
        nodeArray = new ArrayList<>();
        USE_EDGESYS = false;
        DISTANCE_THRESHOLD = 10;
    }

    private int nodeID;

    public Propagation(int nodeID) {
        this.nodeID = nodeID;
    }

    public static void propagateAlert(int nodeID, boolean isStart) {
        if (USE_EDGESYS) {
            /* Add code to interface with RabbitMQ */
            System.out.println("Use EdgeSys\n");
        } else {
            if (adjacencyList == null) {
                System.out.println("empty adjacency list\n");
                System.exit(-1);
            }

            if (isStart) {
                nodeArray.get(nodeID).startAlert();
            }

            CyclicBarrier barrier = new CyclicBarrier(adjacencyList.get(nodeID).size() + 1);
            boolean createdThreads = false;
            for (int currentID : adjacencyList.get(nodeID)) {
                System.out.println(Thread.activeCount());
                if (Thread.activeCount() > 8) {
                    nodeArray.get(currentID).forwardAlert();
                } else {
                    Propagation propagate = new Propagation(currentID);
                    Thread thread = new Thread(propagate.createThread(currentID, barrier));
                    thread.start();
                    createdThreads = true;
                }
            }

            if (createdThreads) {
                try {
                    barrier.await();
                } catch (Exception e) {
                    System.out.println("concurrent threads failed\n");
                    System.exit(-1);
                }
            }

            for (int currentID :adjacencyList.get(nodeID)) {
                Propagation.propagateAlert(currentID, false);
            }
        }
    }

    private Runnable createThread(int node, CyclicBarrier barrier) {
        return new PropagateThread(nodeArray.get(node), barrier, 1000);
    }

    protected static void populateAdjacencyList(ArrayList<Node> nodeArray) {
        Propagation.nodeArray = nodeArray;
        for (Node node : nodeArray) {
            ArrayList<Integer> adjacency = new ArrayList<>();
            for (Node otherNode : nodeArray) {
                if ((node.getDistance(otherNode.getLongitude(),
                        otherNode.getLatitude()) < DISTANCE_THRESHOLD) &&
                        !otherNode.equals(node)) {
                    adjacency.add(otherNode.getNodeID() - 1);
                }
            }
            adjacencyList.put(node.getNodeID(), adjacency);
        }
    }

    public class PropagateThread implements Runnable {
        private Node node;
        private CyclicBarrier barrier;
        private long waitTime;

        public PropagateThread(Node node, CyclicBarrier barrier, long waitTime) {
            this.node = node;
            this.barrier = barrier;
            this.waitTime = waitTime;
        }

        @Override
        public void run() {
            try {
                barrier.await();
                Thread.sleep(waitTime);
            } catch (InterruptedException i) {
                System.out.println("thread interrupted " + nodeID + "\n");
            } catch (Exception e) {
                System.out.println(e.toString());
                System.out.println("concurrent threads failed\n");
                System.exit(-1);
            }

            node.forwardAlert();
        }
    }
}
