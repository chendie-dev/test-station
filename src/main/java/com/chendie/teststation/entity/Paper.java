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
public class Paper implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long pid;

    private String pname;

    private String desc;

    private String totalValue;

    private Long uid;


}
