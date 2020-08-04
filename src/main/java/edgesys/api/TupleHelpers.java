package edgesys.api;

import com.twitter.heron.api.*;
import com.twitter.heron.api.tuple.*;

public final class TupleHelpers {

    private TupleHelpers() {}

    public static boolean isTickTuple(Tuple tuple) {
        return tuple.getSourceComponent().equals(Constants.SYSTEM_COMPONENT_ID)
                && tuple.getSourceStreamId().equals(Constants.SYSTEM_TICK_STREAM_ID);
    }

}