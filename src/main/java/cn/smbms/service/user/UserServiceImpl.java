package cn.smbms.service.user;

import cn.smbms.dao.UserMapper;
import cn.smbms.pojo.User;
import cn.smbms.pojo.UserExample;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;


    @Override
    public PageInfo<User> getAll(String userName,Long userRole,Integer currentPageNo, Integer pageSize) {
        UserExample example=new UserExample();
        UserExample.Criteria criteria = example.createCriteria();

        if (null!=userName&&""!=userName){
            criteria.andUserNameLike("%"+userName+"%");
        }
        if (null!=userRole&&userRole!=0){
            criteria.andUserRoleEqualTo(userRole);
        }
        PageHelper.startPage(currentPageNo, pageSize);
        List<User> userList = userMapper.selectByExample(example);
        PageInfo pageInfo=new PageInfo(userList);

        return pageInfo;
    }

    @Override
    public User login(String userCode, String userPassword) {
        UserExample example=new UserExample();
        example.createCriteria().andUserCodeEqualTo(userCode);

        List<User> list = userMapper.selectByExample(example);
        if (null!=list&&list.size()>0){
            User user=list.get(0);
           if (user.getUserPassword().equals(userPassword)){
               return user;
           }

        }

        return null;
    }

    @Override
    public User getUserById(Long uid){
        UserExample example=new UserExample();
        example.createCriteria().andIdEqualTo(uid);

        User user = userMapper.selectByPrimaryKey(uid);
        return user;
    }

    @Override
    public int modifySave(User user) {
        int i = userMapper.updateByPrimaryKeySelective(user);
        return i;
    }

    @Override
    public int delUserById(Long uid) {
        return  userMapper.deleteByPrimaryKey(uid);
    }

    @Override
    public User getUserByUserCode(String userCode) {
        User user=null;
        if (null==userCode&&""==userCode) {
            return user;
        }

        UserExample example = new UserExample();
        example.createCriteria().andUserCodeEqualTo(userCode);
        List<User> users = userMapper.selectByExample(example);
        if (null != users && users.size() > 0) {
            user = users.get(0);
        }
        return user;
    }

    @Override
    public int add(User user) {

        return  userMapper.insert(user);
    }
}
