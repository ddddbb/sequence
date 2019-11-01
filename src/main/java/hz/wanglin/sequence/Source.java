package hz.wanglin.sequence;

import lombok.Builder;

import java.util.concurrent.locks.Lock;

public interface Source<T> {
    T increBy(Config sc);


    void reset(Config sc);


    Type type();


    @Builder
    class Config {
        /**
         * 兼容原有idcard，不推荐使用
         */
        @Deprecated
        public String customSource;
        /**
         * Sequencce souce 的类型
         */
        @Builder.Default
        public Type type = Type.LONG;
        /**
         * sequence 重置模式
         */
        @Builder.Default
        public Cycle cycle = Cycle.NONE;
        /**
         *
         */
        @Builder.Default
        public String sequenceName = Type.LONG.name();

        /**
         * 本地缓存模式下，本地的缓存size,既为remote的step。1为不缓存。
         */
        @Builder.Default
        public Long step = 1L;
        /**
         * 重置的初始化值
         */
        @Builder.Default
        public Long initVal = 0L;
        /**
         * 缺省最大值
         */
        @Builder.Default
        public Long max = Long.MAX_VALUE;
    }

     enum Type {
        INNER,REDIS, DB, LONG,DRDS
    }

     enum Cycle {
        NONE,
        YEAR,
        MONTH,
        DAY,
        SECOND,//开发测试用，生产谁用这个。
        RING; //到头就再循环使用

    }

}
