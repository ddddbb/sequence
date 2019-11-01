package hz.wanglin.sequence.decorator;

import hz.wanglin.sequence.Source;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RingSequenceSource implements Source<Long> {
    Source<Long> sequenceSource;

    public RingSequenceSource(Source<Long> source) {
        this.sequenceSource = source;
    }

    @Override
    public Long increBy(Source.Config sc) {
        Long curVal = sequenceSource.increBy(sc);
        if (sc.cycle.equals(Source.Cycle.RING) && (curVal.equals(sc.max) || curVal > (sc.max))) {
            sequenceSource.reset(sc);
            log.info("重置sequence:{},-->{}", sc.sequenceName, sc.initVal);
        }
        return curVal;
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

