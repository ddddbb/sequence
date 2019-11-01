package hz.wanglin.sequence.decorator;

import hz.wanglin.sequence.Source;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicLong;

@Slf4j
public class CachedNumSequenceSource implements Source<Long> {
    Source<Long> sequenceSource;
    AtomicLong cursor = new AtomicLong(0);
    Long left;
    Long right;

    public CachedNumSequenceSource(Source<Long> sequenceSource, Source.Config sc) {
        this.sequenceSource = sequenceSource;
        synchronized (CachedNumSequenceSource.class) {
            this.right = sequenceSource.increBy(sc);
            this.left = right - sc.step;
        }
    }

    @Override
    public synchronized Long increBy(Source.Config sc) {
        Long nextVal = cursor.getAndIncrement();
        if (nextVal.equals(sc.step)) {
            right = sequenceSource.increBy(sc);
            left = right - sc.step;
            cursor = new AtomicLong(0);
            log.info("缓存sequence:{},{}-->{}", sc.sequenceName, left, right);
            return left + cursor.getAndIncrement();
        }
        return left + nextVal;
    }

    @Override
    public void reset(Source.Config sc) {
        sequenceSource.reset(sc);
    }

    @Override
    public Type type() {
        return Type.INNER;
    }
}
