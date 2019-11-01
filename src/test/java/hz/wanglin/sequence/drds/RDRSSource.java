package hz.wanglin.sequence.drds;

import hz.wanglin.sequence.Source;
import org.springframework.stereotype.Service;

@Service("rdrsSource")
public class RDRSSource implements Source<Long> {
    @Override
    public Long increBy(Config sc) {
        //...
        return null;
    }

    @Override
    public void reset(Config sc) {
        //....
    }

    @Override
    public Type type() {
        return Type.DRDS;
    }
}
