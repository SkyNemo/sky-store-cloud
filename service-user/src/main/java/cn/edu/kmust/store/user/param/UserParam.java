package cn.edu.kmust.store.user.param;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

public class UserParam {

    private Integer id;

    @NotEmpty(message = "用户名不能为空")
    private String name;

    @NotEmpty(message = "密码不能为空")
    @Length(min = 6, max = 20, message = "密码长度应大于6位小于20位")
    private String password;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
