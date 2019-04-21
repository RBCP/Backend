package backend.admin.services;

import backend.admin.dto.AdminParam;
import backend.mbg.model.Admin;


public interface AdminService {
    Admin getAdminByUsername(String username);

    Admin register(AdminParam adminParam);

    String login(String username,String password);

    String refreshToken(String oldToken);

    Admin getItem(Long id);
    int update(Long id,Admin admin);
    int delete(Long id);



}

