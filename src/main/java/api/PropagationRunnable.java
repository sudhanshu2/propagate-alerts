package api;

public interface PropagationRunnable extends Runnable {
    void setNode(Node node);

    void findAdjacent();
}
