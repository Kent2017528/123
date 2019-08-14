package cn.smbms.service.bill;

import cn.smbms.pojo.Bill;
import cn.smbms.pojo.User;
import com.github.pagehelper.PageInfo;

public interface BillService {

    public PageInfo<Bill> getAll(String productName,Long providerId,Integer isPayment,
    Integer currentPage,Integer pageSize);
    public Bill getBillByBillid(Long billid);

    public Bill getBillById(Long billid);

    public int modifySave(Bill bill);

    public int delBillById(Long billId);

}
