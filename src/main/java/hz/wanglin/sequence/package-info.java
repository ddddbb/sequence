package hz.wanglin.sequence;


/**
 * 任意一个sequence 例如  order20190821b000001 可以如下划分
 * {order}{yyyyMMdd}{b}{sequence}
 * 既
 * {section}{section}{section}.... = sequenceRule
 *
 * 其中section定义了section的生成格式（format），生成数据来源（source），以及相关的配置Source.Config
 *
 *
 *
 *
 *
 * 用法：先定义规则，再利用规则生成sequence
 *
 * 参见SequenceServiceTest
 *
 *
 *
 * sequenceRule.s //字符串 section定义
 * sequenceRule.df //日期 section定义
 * sequenceRule.nf //数字 section定义
 *      在Source.Config 定义了nf的各种定义
 *
 * Type：lONG ,Redis,Db,Rdrss，各种sequence生成源的定义。
 * Cycle:sequence重置周期，策略
 * sequenceName：sequence名
 * step，大于1，则使用本地缓存优化
 * initVal：sequence初始值
 * Max：最大值
 *
 *
 *
 */