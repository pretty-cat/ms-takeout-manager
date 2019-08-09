package com.qf.sys.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Set;

/**
 * @Data 在编译的时候，会自动生成setter 和getter
 * @NoArgsConstructor  无参的制造方法
 * @AllArgsConstructor  所有的参数的构造方法
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SysUser implements Serializable {
    private Integer id;
    private String name;
    private String password;
    private String realName;
    private String salt;
    private Set<SysPermission> permissionSet;
}
