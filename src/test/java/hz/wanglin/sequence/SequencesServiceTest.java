package hz.wanglin.sequence;

import hz.wanglin.sequence.autoconfigure.SequenceAutoConfiguration;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class SequencesServiceTest {
    SequencesService sequencesService;

    @Before
    public void setup() {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.register(SequenceAutoConfiguration.class);
        ctx.refresh();
        sequencesService = ctx.getBean(SequencesService.class);
        initSequenceRule();
    }

    private void initSequenceRule() {
        //        00000000-00000004 循环
        SequenceRule ring1 = new SequenceRule(sequencesService,"ring");
        ring1.s("PAY-ORDER-").df("yyyyMMdd-").nf("00000000",
                Source.Config.builder().customSource("rdrsSource").max(10L).build()
        );
        sequencesService.register(ring1);
        //      按日重置的
        SequenceRule day = new SequenceRule(sequencesService,"day");
        day.s("DAY-").df("yyyyMMdd-").nf("######",
                Source.Config.builder().cycle(Source.Cycle.DAY).build()
        );
        sequencesService.register(day);
    }

    @Test
    public void test() {
        for (int i = 0; i < 1200; i++) {
            System.out.println(sequencesService.nextVal("day"));
        }
    }

}