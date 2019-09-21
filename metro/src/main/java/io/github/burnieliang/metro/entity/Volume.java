package io.github.burnieliang.metro.entity;

import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

/**
 * Created by liangboning on 2019/7/9.
 */
@Data
public class Volume {

    public Volume() {
        this.id = UUID.randomUUID().toString().replaceAll("-", "");
    }

    private String id;

    private LocalDate date;

    private Long volume;

    private Long meters;

    private Long cityid;

}
