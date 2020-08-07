package edgesys.examples.earthquake;

import com.twitter.heron.api.spout.SpoutOutputCollector;
import com.twitter.heron.api.topology.OutputFieldsDeclarer;
import com.twitter.heron.api.topology.TopologyContext;
import com.twitter.heron.api.tuple.Fields;
import com.twitter.heron.api.tuple.Values;
import com.twitter.heron.api.utils.Utils;
import edgesys.api.Streamer;

import java.util.*;

public class GroundData extends Streamer {
    private Random random;
    private SpoutOutputCollector collector;
    private double multiplier;

    @Override
    public void open(Map<String, Object> conf, TopologyContext context, SpoutOutputCollector collector) {
        this.random = new Random();
        this.collector = collector;
        this.multiplier = 20.0;
    }

    @Override
    public void nextTuple() {
        Utils.sleep(10);
        collector.emit(new Values(multiplier * random.nextDouble()));
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("sensor"));
    }
}
