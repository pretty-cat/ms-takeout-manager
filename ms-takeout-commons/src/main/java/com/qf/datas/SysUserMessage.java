package com.qf.datas;

import com.qf.sys.pojo.SysUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysUserMessage {
    private StatusCode code;
    private String msg;
    private SysUser data;
}
