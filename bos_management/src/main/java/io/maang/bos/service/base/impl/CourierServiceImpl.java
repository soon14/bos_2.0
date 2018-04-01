package io.maang.bos.service.base.impl;

import io.maang.bos.dao.base.CourierRepository;
import io.maang.bos.domain.base.Courier;
import io.maang.bos.service.base.CourierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 描述:
 *
 * @outhor ming
 * @create 2018-03-29 23:41
 */
@Service
@Transactional
public class CourierServiceImpl implements CourierService {

    @Autowired
    private CourierRepository courierRepository;
    @Override
    public void save(Courier courier) {
        courierRepository.save(courier);
    }

    @Override
    public Page<Courier> findPageDate(Specification<Courier> specification, Pageable pageable) {
        return  courierRepository.findAll(specification,pageable);
    }

    @Override
    public void delBatch(String[] idArray) {

        for (String s : idArray) {
            Integer id = Integer.parseInt(s);
            courierRepository.updateDelTag(id);
        }
    }

    @Override
    public void restoreBatch(String[] idArray) {

        for (String s : idArray) {
            Integer id = Integer.parseInt(s);
            Courier one = courierRepository.findOne(id);
            one.setDeltag(null);
        }
    }
}
