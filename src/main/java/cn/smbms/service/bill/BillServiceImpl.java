package cn.smbms.service.bill;

import cn.smbms.dao.BillMapper;
import cn.smbms.pojo.*;
import cn.smbms.service.provider.ProviderService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BillServiceImpl implements BillService {
    @Autowired
    private BillMapper billMapper;
    /*@Autowired
    private ProviderService providerService;*/

    @Override
    public Bill getBillByBillid(Long billid) {
        return billMapper.selectByPrimaryKey(billid);
    }

    @Override
    public PageInfo<Bill> getAll(String productName, Long providerId, Integer isPayment,
                                 Integer currentPage, Integer pageSize) {
        PageInfo<Bill> pageInfo=null;
        PageHelper.startPage(currentPage,pageSize);
        BillExample example=new BillExample();
        BillExample.Criteria criteria = example.createCriteria();
        if(null!=productName&&""!=productName){
           /* List<Long> providerIds=new ArrayList<Long>();
            List<Provider> providerList = providerService.getProviderByName(productName);
            for (Provider providers:providerList){
                Long id = providers.getId();
                providerIds.add(id);
            }
            criteria.andProviderIdIn(providerIds);*/
            criteria.andProductNameLike("%"+productName+"%");
        }
        if(null!=providerId&&providerId!=0){
            criteria.andProviderIdEqualTo(providerId);
        }
        if(null!=isPayment&&isPayment!=0){
            criteria.andIsPaymentEqualTo(isPayment);
        }
        List<Bill> list = billMapper.selectByExample(example);
        pageInfo=new PageInfo<Bill>(list);


        return pageInfo;
    }

    @Override
    public Bill getBillById(Long billid) {
        BillExample example=new BillExample();
        example.createCriteria().andIdEqualTo(billid);

        Bill bill = billMapper.selectByPrimaryKey(billid);
        return bill;
    }

    @Override
    public int modifySave(Bill bill) {
        int i = billMapper.updateByPrimaryKeySelective(bill);
        return i;

    }

    @Override
    public int delBillById(Long billId) {

        return  billMapper.deleteByPrimaryKey(billId);
    }
}
