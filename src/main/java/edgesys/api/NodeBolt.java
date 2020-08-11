package edgesys.api;

import com.twitter.heron.api.bolt.*;
import com.twitter.heron.api.topology.*;
import com.twitter.heron.api.tuple.*;

import java.util.*;

public abstract class NodeBolt extends BaseBasicBolt {

    private String fieldName;
    public abstract void initialize();
    public abstract String getFieldName();
    public abstract boolean computation(Object object);

    @Override
    public void prepare(Map stormConf, TopologyContext context) {
        initialize();
        this.fieldName = getFieldName();
    }

    @Override
    public void execute(Tuple input, BasicOutputCollector collector) {
        double newLoc = Double.parseDouble(input.getString(0));
        System.out.println(newLoc);
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields(fieldName));
    }
}
