package cn.smbms.service.provider;

import cn.smbms.pojo.Provider;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface ProviderService {

    public PageInfo<Provider> getProiderList(String queryProCode, String queryProName,
                                             Integer currentPageNo, Integer pageSize);

    public List<Provider> getAll();

    public List<Provider> getProviderByName(String productName);

    public Provider getProviderByProid(Long proid);


}
