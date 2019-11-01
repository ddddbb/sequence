package hz.wanglin.sequence.source;


import hz.wanglin.sequence.Source;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class AtomicLongSequenceSource implements Source<Long> {
    ConcurrentMap<String, AtomicLong> sequences = new ConcurrentHashMap<String, AtomicLong>();

    @Override
    public Long increBy(Source.Config sc) {
        if (!sequences.containsKey(sc.sequenceName)) {
            sequences.put(sc.sequenceName, new AtomicLong(sc.initVal));
        }
        return sequences.get(sc.sequenceName).addAndGet(sc.step);

    }

    @Override
    public void reset(Source.Config sc) {
        if (!sequences.containsKey(sc.sequenceName)) {
            sequences.put(sc.sequenceName, new AtomicLong(sc.initVal));
            return;
        }
        sequences.get(sc.sequenceName).set(sc.initVal);
    }

    @Override
    public Type type() {
        return Type.LONG;
    }
}
