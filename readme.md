通用sequence生成器

用法
        
        定义规则
        SequenceRule ring1 = new SequenceRule(sequenceService,"ring");
        ring1.s("PAY-ORDER-").df("yyyyMMdd-").nf("00000000",
                Source.Config.builder().build()
        );
        sequencesService.register(ring1);
        
        成成sequence
         for (int i = 0; i < 10; i++) {
                    System.out.println(sequencesService.nextVal("ring"));
         }
         
         PAY-ORDER-20191101-00000001
         PAY-ORDER-20191101-00000002
         PAY-ORDER-20191101-00000003
         PAY-ORDER-20191101-00000004
         PAY-ORDER-20191101-00000005
         PAY-ORDER-20191101-00000006
         PAY-ORDER-20191101-00000007
         PAY-ORDER-20191101-00000008
         PAY-ORDER-20191101-00000009
         PAY-ORDER-20191101-00000010
         
        


 
    
    
配置词典
<table>
    <tr>
        <th>配置项目</th>
        <th>语法</th>
        <th>说明</th>
    </tr>
    <tr>
        <th>表达式</th>
        <th>s,nf,df</th>
        <th>sequence表达式</th>
    </tr>
    <tr>
        <th>生成器数据源</th>
        <th>LONG,REDIS,DB,自定义源</th>
        <th>默认LONG，单虚拟机不重复</th>
    </tr>
    <tr>
        <th>重置模式</th>
        <th>DAY,WEEK,MONTH,YEAR,RING,NONE</th>
        <th>DAY：日重置。。。。RING：环装重置，参见最大值定义，NONE：不重置</th>
    </tr>
    <tr>
        <th>STEP</th>
        <th>数字：默认1</th>
        <th></th>
    </tr>
    <tr>
        <th>最小值</th>
        <th>数字：默认0</th>
        <th></th>
    </tr>
    <tr>
        <th>最大值</th>
        <th>数字：默认Long.MAX</th>
        <th></th>
    </tr>
</table>
例

         SequenceRule ring1 = new SequenceRule(sequencesService,"ring");
         ring1.s("PAY-ORDER-").df("yyyyMMdd-").nf("00000000",
                 Source.Config.builder().cycle(Source.Cycle.RING).max(10L).build()
         );
         sequencesService.register(ring1);
         
         
        
        PAY-ORDER-20191101-00000001
        PAY-ORDER-20191101-00000002
        PAY-ORDER-20191101-00000003
        PAY-ORDER-20191101-00000004
        PAY-ORDER-20191101-00000005
        PAY-ORDER-20191101-00000006
        PAY-ORDER-20191101-00000007
        PAY-ORDER-20191101-00000008
        PAY-ORDER-20191101-00000009
        PAY-ORDER-20191101-00000010
        PAY-ORDER-20191101-00000001
        PAY-ORDER-20191101-00000002




     