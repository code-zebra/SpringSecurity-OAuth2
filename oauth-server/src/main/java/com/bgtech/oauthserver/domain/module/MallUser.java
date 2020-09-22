package com.bgtech.oauthserver.domain.module;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author HuangJiefeng
 * @date 2020/9/18 0018 9:27
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MallUser implements Serializable {

    private Integer uid;

    private String username;

    private String password;
}
