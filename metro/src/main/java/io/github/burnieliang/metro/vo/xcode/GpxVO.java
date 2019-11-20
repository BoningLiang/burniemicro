package io.github.burnieliang.metro.vo.xcode;


import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "gpx")
public class GpxVO {

    @XmlAttribute
    public String version;

    @XmlAttribute
    public String creator;

    public List<WptVO> wpt;

}
