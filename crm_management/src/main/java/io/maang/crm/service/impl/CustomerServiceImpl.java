package io.maang.crm.service.impl;

import io.maang.crm.dao.CustomerRepository;
import io.maang.crm.domain.Customer;
import io.maang.crm.service.CustomerService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 描述:
 *
 * @outhor ming
 * @create 2018-04-02 19:51
 */
@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public List<Customer> findNoAssociationCustomers() {
        List<Customer> byFixedAreaIdIsNull = customerRepository.findByFixedAreaIdIsNull();
        System.out.println(byFixedAreaIdIsNull);
        return byFixedAreaIdIsNull;
    }

    @Override
    public List<Customer> findHasAssociationFixedAreaCustomers(String fixedAreaId) {
        return customerRepository.findByFixedAreaId(fixedAreaId);
    }

    @Override
    public void associationCustomerToFixedAreaId(String customerIdStr, String fixedAreaId) {
        //解除关联的动作
        customerRepository.clearFixedAreaId(fixedAreaId);
        //切割字符串1,2,3
        if (StringUtils.isBlank(customerIdStr)||"null".equals(customerIdStr)) {
            return;
        }
        String[] customerArray = customerIdStr.split(",");
        for (String idStr : customerArray) {
            Integer id = Integer.parseInt(idStr);
            customerRepository.updateFixedId(fixedAreaId, id);
        }
    }

    @Override
    public void regist(Customer customer) {
        customerRepository.save(customer);
    }

    @Override
    public Customer findByTelephone(String telephone) {
        return customerRepository.findByTelephone(telephone);
    }

    @Override
    public void updateType(String telephone) {
        customerRepository.updateType(telephone);
    }
}
