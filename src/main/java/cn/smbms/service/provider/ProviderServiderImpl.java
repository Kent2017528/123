package cn.smbms.service.provider;

import cn.smbms.dao.ProviderMapper;
import cn.smbms.pojo.Provider;
import cn.smbms.pojo.ProviderExample;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ProviderServiderImpl implements ProviderService {

    @Autowired
    private ProviderMapper providerMapper;


    @Override
    public Provider getProviderByProid(Long proid) {
        return providerMapper.selectByPrimaryKey(proid);
    }

    @Override
    public PageInfo<Provider> getProiderList(String queryProCode, String queryProName,
                                             Integer currentPageNo, Integer pageSize) {
        PageInfo pageInfo=null;
        PageHelper.startPage(currentPageNo,pageSize);
        ProviderExample example=new ProviderExample();
        ProviderExample.Criteria criteria = example.createCriteria();

        if (null!=queryProCode&&""!=queryProCode){
            criteria.andProCodeLike("%"+queryProCode+"%");
        }
        if (null!=queryProName&&""!=queryProName){
            criteria.andProNameLike("%"+queryProName+"%");
        }
        PageHelper.startPage(currentPageNo, pageSize);
        List<Provider> userList = providerMapper.selectByExample(example);
        pageInfo=new PageInfo(userList);

        return pageInfo;
    }

    @Override
    public List<Provider> getAll() {
        ProviderExample example=new ProviderExample();
        List<Provider> list = providerMapper.selectByExample(example);

        return list;
    }
    @Override
    public List<Provider> getProviderByName(String productName) {
        ProviderExample example=new ProviderExample();
        example.createCriteria().andProNameLike("%"+productName+"%");

        List<Provider> list = providerMapper.selectByExample(example);

        return list;
    }
}
