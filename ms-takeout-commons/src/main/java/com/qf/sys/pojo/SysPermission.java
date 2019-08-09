package com.qf.sys.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysPermission  implements Serializable {
    private int id;
    private String name;
    private String permission;
    private String url;

}
