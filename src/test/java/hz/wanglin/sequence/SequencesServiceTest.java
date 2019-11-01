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
                Source.Config.builder().cycle(Source.Cycle.RING).max(10L).build()
        );
        sequencesService.register(ring1);
    }

    @Test
    public void test() {
        for (int i = 0; i < 12; i++) {
            System.out.println(sequencesService.nextVal("ring"));
        }
    }

}