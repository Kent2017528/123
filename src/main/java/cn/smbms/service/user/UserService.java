package cn.smbms.service.user;

import cn.smbms.pojo.User;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface UserService {

    public User login(String userCode,String userPassword);

    public PageInfo<User> getAll(String userName,Long userRole,Integer currentPageNo,Integer pageSize);

    public User getUserById(Long uid);

    public int modifySave(User user);

    public int delUserById(Long uid);

    public User getUserByUserCode(String userCode);

    public int add(User user);


}
