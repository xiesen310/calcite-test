package org.example.calcite.test.tutorial;

import lombok.Data;

import java.io.Serializable;

@Data
public class TutorialColumn implements Serializable {
    /**
     * 字段名称
     */
    private String name;

    /**
     * 字段类型名称
     */
    private String type;
}