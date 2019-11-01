package hz.wanglin.sequence;

import java.text.Format;

/**
 * 序列化生成器的格式段{Section1}{Section2}{Section3}......
 */
public class Section {
    /**
     * 格式化表达式
     */
    Format format;
    /**
     * 序列号生成器
     */
    public Source source;
    /**
     *
     */
    public Source.Config sc;


    public Section(Source source, Format format) {
        assert source != null : "SequenceSource is null";
        this.source = source;
        this.format = format;
    }

    public Section(Source source, Format format, Source.Config sc) {
        assert source != null : "SequenceSource is null";
        this.source = source;
        this.format = format;
        this.sc = sc;
    }

    public String value() {
        return format.format(source.increBy(sc));
    }

}