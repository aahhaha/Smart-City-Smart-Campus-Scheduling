package graph.common;

public class BasicMetrics {
    private long dfsVisit, dfsEdge, qPush, qPop, relax;
    private long start, elapsed;

    public void incDfsVisit(){ dfsVisit++; }
    public void incDfsEdge(){ dfsEdge++; }
    public void incQueuePush(){ qPush++; }
    public void incQueuePop(){ qPop++; }
    public void incRelax(){ relax++; }

    public void startTimer(){ start = System.nanoTime(); }
    public void stopTimer(){ elapsed += System.nanoTime() - start; }

    public long getNanos() {
        return elapsed;
    }

    public long getDfsVisit() { return dfsVisit; }
    public long getDfsEdge() { return dfsEdge; }
    public long getQueuePush() { return qPush; }
    public long getQueuePop() { return qPop; }
    public long getRelax() { return relax; }

    @Override
    public String toString(){
        return String.format(
                "dfsVisit=%d, dfsEdge=%d, qPush=%d, qPop=%d, relax=%d, timeNanos=%d",
                dfsVisit, dfsEdge, qPush, qPop, relax, elapsed
        );
    }
}
