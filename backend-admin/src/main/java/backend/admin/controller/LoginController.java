package backend.admin.controller;

import backend.admin.dto.AdminLoginParam;
import backend.admin.dto.AdminParam;
import backend.admin.dto.CommonResult;
import backend.admin.services.AdminService;
import backend.mbg.model.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class LoginController {
    @Autowired
    private AdminService adminService;
    @Value("${jwt.tokenHeader}")
    private String tokenHeader;
    @Value("${jwt.tokenHead}")
    private String tokenHead;
    @RequestMapping(value="/register",method= RequestMethod.POST)
    public Object register(@RequestBody AdminParam adminParam, BindingResult result){
        Admin admin=adminService.register(adminParam);
        if(admin==null){
            return new CommonResult().failed();
        }
        return new CommonResult().success(admin);
    }
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public Object login(@RequestBody AdminLoginParam adminLoginParam, BindingResult result){
        String token=adminService.login(adminLoginParam.getUsername(),adminLoginParam.getPassword());
        if(token==null){
            return new CommonResult().validateFailed("用户名或者密码错误");
        }
        Map<String,String>tokenMap=new HashMap();
        tokenMap.put("token",token);
        tokenMap.put("tokenHead",tokenHead);
        return new CommonResult().success(tokenMap);

    }
    @RequestMapping(value="/hello",method = RequestMethod.GET)
    public Object Hello(){
        System.out.println("hello world");
        return new CommonResult().success("test");
    }
    @RequestMapping(value="/token/refresh",method=RequestMethod.GET)
    public Object refreshToken(HttpServletRequest request){
        String token = request.getHeader(tokenHeader);
        String refreshToken = adminService.refreshToken(token);
        if (refreshToken == null) {
            return new CommonResult().failed();
        }
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", refreshToken);
        tokenMap.put("tokenHead", tokenHead);
        return new CommonResult().success(tokenMap);
    }
}
