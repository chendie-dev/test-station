package com.chendie.teststation.entity;

import java.io.Serializable;
import lombok.Data;
/**
 * <p>
 *
 * </p>
 *
 * @author chendie
 * @since 2023-08-26
 */
@Data
public class Questions implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long qid;

    private String content;

    private String typeA;

    private String typeB;

    private String typeC;

    private String typeD;

    private String rightType;

    private Integer value;

    private Long uid;


}
