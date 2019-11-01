package hz.wanglin.sequence.source;


import hz.wanglin.sequence.Source;

import java.util.Date;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DateSource implements Source<Date> {
    Date date;
    ReentrantLock lock = new ReentrantLock();
    public DateSource(Date date){
        this.date = date;
    }
    @Override
    public Date increBy(Config sc) {
        return date;
    }



    @Override
    public void reset(Config sc) {

    }

    @Override
    public Type type() {
        return Type.INNER;
    }
}
