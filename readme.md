  #  通用sequence生成器

  ##  样例
        
      
         
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
         
        


 
    
    
  ##  用户手册
<table align="left" >
    <tr>
        <th>配置项目</th>
        <th >语法</th>
        <th>说明</th>
    </tr>
    <tr align="left">
        <th>表达式</th>
        <th>  s:字符串<br/>nf：数字格式<br/>df：日期</th>
        <th>sequence表达式</th>
    </tr>
    <tr align="left">
        <th>生成器数据源</th>
        <th>LONG：虚拟机生成器<br/>REDIS：REDIS<br/>DB:数据库生成器<br/>自定义：自定义生成器，参见customSource<br/></th>
        <th></th>
    </tr>
    <tr  align="left">
        <th>重置模式</th>
        <th>DAY:按日重置<br/>WEEK：按周重置<br/>MONTH：......<br/>YEAR：......<br/>RING:环形重置，参见MAX<br/>NONE:不重置</th>
        <th></th>
    </tr >
    <tr  align="left">
        <th>STEP</th>
        <th>数字：默认1</th>
        <th></th>
    </tr>
    <tr  align="left">
        <th>最小值</th>
        <th>数字：默认0</th>
        <th></th>
    </tr>
    <tr  align="left">
        <th>最大值</th>
        <th>数字：默认Long.MAX</th>
        <th></th>
    </tr>
    <tr  align="left">
        <th>customSource</th>
        <th>自定义生成器</th>
        <th></th>
    </tr>
</table>


  ##  例子

    //配置sequence规则，这是一个最大10的环形重置sequencce
         SequenceRule ring1 = new SequenceRule(sequencesService,"ring");
         ring1.s("PAY-ORDER-").df("yyyyMMdd-").nf("00000000",
                 Source.Config.builder().cycle(Source.Cycle.RING).max(10L).build()
         );
         sequencesService.register(ring1);
         
    //生成sequence    
          for (int i = 0; i < 12; i++) {
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
        PAY-ORDER-20191101-00000001
        PAY-ORDER-20191101-00000002
        
        //      按日重置的
        SequenceRule day = new SequenceRule(sequencesService,"day");
        day.s("DAY-").df("yyyyMMdd-").nf("######",
                Source.Config.builder().cycle(Source.Cycle.DAY).build()
        );
        sequencesService.register(day);
        
        DAY-20191101-1
        DAY-20191101-2
        DAY-20191101-3
        DAY-20191101-4
        DAY-20191101-5
        DAY-20191101-6
        DAY-20191101-7
        DAY-20191101-8
        DAY-20191101-9
        DAY-20191101-10
        DAY-20191101-11
        DAY-20191101-12
        DAY-20191101-13
        DAY-20191101-14
        DAY-20191101-15
        DAY-20191101-16
        DAY-20191101-17
        DAY-20191101-18
        DAY-20191101-19
        DAY-20191101-20
        DAY-20191101-21
        DAY-20191101-22
        DAY-20191101-23
        DAY-20191101-24
        DAY-20191101-25
        DAY-20191101-26
        DAY-20191101-27
        DAY-20191101-28
        DAY-20191101-29
        DAY-20191101-30
        DAY-20191101-31
        DAY-20191101-32
        DAY-20191101-33
        DAY-20191101-34
        DAY-20191101-35
        DAY-20191101-36
        DAY-20191101-37
        DAY-20191101-38
        DAY-20191101-39
        DAY-20191101-40
        DAY-20191101-41
        DAY-20191101-42
        DAY-20191101-43
        DAY-20191101-44
        DAY-20191101-45
        DAY-20191101-46
        DAY-20191101-47
        DAY-20191101-48
        DAY-20191101-49
        DAY-20191101-50
        DAY-20191101-51
        DAY-20191101-52
        DAY-20191101-53
        DAY-20191101-54
        DAY-20191101-55
        DAY-20191101-56
        DAY-20191101-57
        DAY-20191101-58
        DAY-20191101-59
        DAY-20191101-60
        DAY-20191101-61
        DAY-20191101-62
        DAY-20191101-63
        DAY-20191101-64
        DAY-20191101-65
        DAY-20191101-66
        DAY-20191101-67
        DAY-20191101-68
        DAY-20191101-69
        DAY-20191101-70
        DAY-20191101-71
        DAY-20191101-72
        DAY-20191101-73
        DAY-20191101-74
        DAY-20191101-75
        DAY-20191101-76
        DAY-20191101-77
        DAY-20191101-78
        DAY-20191101-79.。。。。。。




  ##  扩展
  ####  sequence  源扩展
 - 继承Source接口
 - 在spring中定义bean
 - 使用

```java

@Service("rdrsSource")
public class RDRSSource implements Source<Long> {
	JdbcTemplate jdbcTemplate;

	public DrdsSequenceSource(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public Long increBy(Config sc) {
		String sql = "SELECT " + sc.sequenceName + ".nextval FROM dual WHERE count = ? ";
		return Long.parseLong(Collections.max(jdbcTemplate.queryForList(sql, new Object[] { sc.step }, String.class)));
	}

	@Override
	public void reset(Config sc) {

	}

	@Override
	public Type type() {
		return Type.DRDS;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
} 

```
使用

```java

        //        00000000-00000004 循环
        SequenceRule ring1 = new SequenceRule(sequencesService,"ring");
        ring1.s("PAY-ORDER-").df("yyyyMMdd-").nf("00000000",
                Source.Config.builder().customSource("rdrsSource").max(10L).build()
        );
        sequencesService.register(ring1);

```
 