package hz.wanglin.sequence;


import hz.wanglin.sequence.decorator.CachedNumSequenceSource;
import hz.wanglin.sequence.decorator.RingSequenceSource;
import hz.wanglin.sequence.source.AtomicLongSequenceSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class SequencesService implements ApplicationContextAware {
    ConcurrentHashMap<String, SequenceRule> rules       = new ConcurrentHashMap<String, SequenceRule>();
    List<Section>                           numSections = new ArrayList<Section>();
    private ApplicationContext ctx;
    AtomicLongSequenceSource LONG_SOUCE = new AtomicLongSequenceSource();

    /**
     * 注册sequence rule
     *
     * @param rule
     */
    public void register(SequenceRule rule) {
        rules.put(rule.getName(), rule);
        initNumSections();
    }

    public SequenceRule getRule(String rule) {
        return rules.get(rule);
    }

    /**
     * 生成下一个sequence
     *
     * @param ruleName
     * @return
     */
    public String nextVal(String ruleName) {
        SequenceRule rule = rules.get(ruleName);
        assert null != rule : "no such rule :" + ruleName;
        return rule.generate();
    }


    /**
     * 重置这个section（的source对应的sequenceName）
     *
     * @param cycle
     */
    public void reset(Source.Cycle cycle) {
        for (Section section : numSections) {
            if (null != section.sc && cycle.equals(section.sc.cycle)) {
//                if(section.source.getLock(section.sc)) {
                section.source.reset(section.sc);
                log.info("重置sequence:{} -->{}", section.sc.sequenceName, section.sc.initVal);
//                }
            }
        }
    }


    private void initNumSections() {
        numSections.clear();
        for (SequenceRule rule : rules.values()) {
            numSections.addAll(rule.getNumSections());
        }
    }

    /**
     * 构建source，
     * <p>
     * 逻辑；
     * 1，获取原生sequencce source
     * 2， 分别对ring和cache模式进行装饰
     *
     * @param sc
     * @return
     */
    public Source buildSource(Source.Config sc) {
        /**
         * 获取原生source
         */
        Source<Long> source = getSource(sc);
        assert null != source : sc.type + " Source is null";
//        装饰ring
        if (sc.cycle.equals(Source.Cycle.RING)) {
            source = new RingSequenceSource(source);
        }
//      year，month等   time的装饰使用autoconfiguration中的scheduled机制
//        ……不表

//        缓存sequence
        if (sc.step > 1) {
            source = new CachedNumSequenceSource(source, sc);
        }
        return source;
    }

    public Source<Long> getSource(Source.Config sc) {
        if (!StringUtils.isEmpty(sc.customSource)) {
            return ctx.getBean(sc.customSource, Source.class);
        }
        if (sc.type.equals(Source.Type.LONG)) {
            return LONG_SOUCE;
        }
        Source source = null;
        for (Source s : ctx.getBeansOfType(Source.class).values()) {
            if (s.type().equals(sc.type)) {
                source = s;
                break;
            }
        }
        ;
        assert null != source : "Source.Type:{" + sc.type + "} is null";
        return source;
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.ctx = applicationContext;
    }
}
