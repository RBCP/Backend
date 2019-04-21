package backend.admin.services.impl;

import backend.admin.dto.AdminParam;
import backend.admin.services.AdminService;
import backend.admin.utils.JwtTokenUtil;
import backend.mbg.mapper.AdminMapper;
import backend.mbg.model.Admin;
import backend.mbg.model.AdminExample;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {
    private static final Logger LOGGER= LoggerFactory.getLogger(AdminServiceImpl.class);
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Value("${jwt.tokenHead}")
    private String tokenHead;
    @Autowired
    private AdminMapper adminMapper;

    @Override
    public Admin getAdminByUsername(String username ){
        AdminExample example =new AdminExample();
        example.createCriteria().andUsernameEqualTo(username);
        List<Admin> adminList=adminMapper.selectByExample(example);
        if(adminList!=null&&adminList.size()>0){
            return adminList.get(0);
        }

        return null;
    }
    @Override
    public Admin register(AdminParam adminParam){
        Admin admin=new Admin();
        BeanUtils.copyProperties(adminParam,admin);
        admin.setCreateTime(new Date());
        AdminExample example =new AdminExample();
        example.createCriteria().andUsernameEqualTo(admin.getUsername());
        List<Admin> AdminList=adminMapper.selectByExample(example);
        if(AdminList.size()>0){
            return null;
        }
        String md5Password=passwordEncoder.encode(admin.getPassword());
        admin.setPassword(md5Password);
        return admin;
    }
    @Override
    public String login(String username,String password){
        String token=null;
        try{
            UserDetails userDetails=userDetailsService.loadUserByUsername(username);
            UsernamePasswordAuthenticationToken authentication=new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            token=jwtTokenUtil.generateToken(userDetails);
            updateLoginTimeByusername(username);
        }catch(AuthenticationException e){
            LOGGER.warn("登录异常:{}",e.getMessage());
        }
        return token;
    }
    private void updateLoginTimeByusername(String username){
        Admin record=new Admin();
        record.setLoginTime(new Date());
        AdminExample example=new AdminExample();
        example.createCriteria().andUsernameEqualTo(username);
        adminMapper.updateByExampleSelective(record,example);
    }
    @Override
    public String refreshToken(String oldToken){
        String token=oldToken.substring(tokenHead.length());
        if(jwtTokenUtil.canRefresh(token)){
            return jwtTokenUtil.refreshToken(token);
        }
        return null;
    }
    @Override
    public Admin getItem(Long id){
        return adminMapper.selectByPrimaryKey(id);

    }
    public int update(Long id,Admin admin){
        admin.setId(id);
        return adminMapper.updateByPrimaryKey(admin);
    }
    public int delete(Long id){
        return adminMapper.deleteByPrimaryKey(id);
    }

}
