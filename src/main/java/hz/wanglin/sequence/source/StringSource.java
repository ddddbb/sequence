package hz.wanglin.sequence.source;


import hz.wanglin.sequence.Source;

import java.util.concurrent.locks.Lock;

public class StringSource implements Source<String> {
    public  String str;
    public StringSource(String str){
        this.str = str;
    }
    @Override
    public String increBy(Config sc) {
        return str;
    }



    @Override
    public void reset(Config sc) {

    }

    @Override
    public Type type() {
        return Type.INNER;
    }
}
