package com.qf.datas;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message {
    private StatusCode code;
    private String msg;
    private Object data;
}
