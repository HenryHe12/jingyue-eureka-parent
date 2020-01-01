package com.jingyue.api.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User  implements Serializable {
    private Long id;
    private String userName;
    private String password;
    private String address;
    private String email;
    private int age;
    private String mobile;
    private int gender;
    private String cardId;
    private String position;//职位名称
    private Long deptId;
}
