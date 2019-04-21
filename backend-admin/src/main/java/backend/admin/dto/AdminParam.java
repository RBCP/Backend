package backend.admin.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;


@Getter
@Setter
public class AdminParam {
    @ApiModelProperty(value="用户名",required = true)
    @NotEmpty(message="用户名不能为空")
    private String username;
    @ApiModelProperty(value="密码",required = true)
    @NotEmpty(message="密码不能为空")
    private String password;
    @Email(message="邮箱不合法")
    private String email;
    private String nickName;
    private String note;




}
