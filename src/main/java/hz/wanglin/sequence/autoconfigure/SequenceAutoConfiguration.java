package hz.wanglin.sequence.autoconfigure;

import hz.wanglin.sequence.SequencesService;
import hz.wanglin.sequence.Source;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

/**
 * SEQUENCE 自动组装类
 * <p>
 * 作者：王林
 * <p>
 * 1，当用户配置sequence.enable = true时，生效
 */
@Configuration
@EnableScheduling
public class SequenceAutoConfiguration { 

    @Bean
    public SequencesService sequencesService() {
        return new SequencesService();
    }

    @Bean
    public SequenceSchedule sequenceSchedule() {
        return new SequenceSchedule();
    }

    @Bean
    public TaskScheduler scheduledExecutorService() {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(2);
        scheduler.setThreadNamePrefix("task-thread-");
        return scheduler;
    }

    static class SequenceSchedule {
        @Autowired
        SequencesService sequencesService;

//        每天重置
        @Scheduled(cron = "0 0 1 * * ?")
        public void day() {
            sequencesService.reset(Source.Cycle.DAY);
        }

//        每月重置
        @Scheduled(cron = "0 0 1 1 * ?")
        public void month() {
            sequencesService.reset(Source.Cycle.MONTH);
        }

//        每年重置
        @Scheduled(cron = "0 0 1 1 1 ?")
        public void year() {
            sequencesService.reset(Source.Cycle.YEAR);
        }
    }
}
