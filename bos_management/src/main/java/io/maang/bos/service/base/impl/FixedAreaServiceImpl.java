package io.maang.bos.service.base.impl;

import io.maang.bos.dao.base.CourierRepository;
import io.maang.bos.dao.base.FixedAreaRepository;
import io.maang.bos.dao.base.TakeTimeRepository;
import io.maang.bos.domain.base.Courier;
import io.maang.bos.domain.base.FixedArea;
import io.maang.bos.domain.base.TakeTime;
import io.maang.bos.service.base.FixedAreaService;
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
 * @create 2018-04-01 20:40
 */
@Service
@Transactional
public class FixedAreaServiceImpl implements FixedAreaService {

    @Autowired
    private FixedAreaRepository fixedAreaRepository;
    @Autowired
    private CourierRepository courierRepository;
    @Autowired
    private TakeTimeRepository takeTimeRepository;

    @Override
    public void save(FixedArea fixedArea) {
        fixedAreaRepository.save(fixedArea);
    }

    @Override
    public Page<FixedArea> findPageData(Specification<FixedArea> specification, Pageable pageable) {
        return fixedAreaRepository.findAll(specification,pageable);
    }

    @Override
    public void associationCourierToFixedArea(FixedArea model, Integer courierId, Integer takeTimeId) {
        FixedArea fixArea = fixedAreaRepository.findOne(model.getId());
        Courier courier = courierRepository.findOne(courierId);
        TakeTime takeTime = takeTimeRepository.findOne(takeTimeId);
        //快递员关联到定区上
        fixArea.getCouriers().add(courier);
        //收派标准关联到快递员上
        courier.setTakeTime(takeTime);
    }
}
