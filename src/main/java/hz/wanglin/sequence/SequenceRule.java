package hz.wanglin.sequence;


import hz.wanglin.sequence.source.DateSource;
import hz.wanglin.sequence.source.StringSource;

import java.text.*;
import java.util.*;

public class SequenceRule {
    private final String name;
    private SequencesService sequencesService;
    LinkedList<Section> sections = new LinkedList<Section>();

    public SequenceRule(SequencesService sequencesService,String name) {
        this.sequencesService = sequencesService;
        this.name = name;
    }


    /**
     * 常量字符串
     */
    public SequenceRule s(final String str) {
        sections.add(new Section(new StringSource(str), new SimpleStringFormat(str)));
        return this;
    }

    /**
     * 日期格式化生成
     */
    public SequenceRule df(String format) {
        sections.add(new Section(new DateSource(new Date()), new SimpleDateFormat(format)));
        return this;
    }

    /**
     * 日期格式化生成
     */
    public SequenceRule df(String format,Date date) {
        sections.add(new Section(new DateSource(date), new SimpleDateFormat(format)));
        return this;
    }

    /**
     * 数字(num)格式化生成
     */

    public SequenceRule nf(String format, Source.Config sc) {
        try {
            sections.add(new Section(sequencesService.buildSource(sc), new DecimalFormat(format), sc));
        }catch (Exception e){
            e.printStackTrace();
        }
        return this;
    }

    public String generate() {
        StringBuilder sb = new StringBuilder();
        for (Section section : sections) {
            sb.append(section.value());
        }
        return sb.toString();
    }

    public List<Section> getNumSections() {
        List<Section> list = new ArrayList<Section>();
        for (Section sec : sections) {
            if (sec.format instanceof DecimalFormat) {
                list.add(sec);
            }
        }
        return list;
    }


    public String getName() {
        return name;
    }

    public static class SimpleStringFormat extends Format {
        private final String str;

        public SimpleStringFormat(String str) {
            this.str = str;
        }



        @Override
        public StringBuffer format(Object obj, StringBuffer toAppendTo, FieldPosition pos) {
            return new StringBuffer(str);
        }

        @Override
        public Object parseObject(String source, ParsePosition pos) {
            return str;
        }
    }


}
