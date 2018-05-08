package io.maang.bos.service.base.impl;

import io.maang.bos.dao.base.CourierRepository;
import io.maang.bos.domain.base.Courier;
import io.maang.bos.service.base.CourierService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Set;

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
    @RequiresPermissions("courier_add")
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

    @Override
    public List<Courier> findNoAssociation() {
        //封装查询条件
        Specification<Courier> specification = new Specification<Courier>() {
            @Override
            public Predicate toPredicate(Root<Courier> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                //查询条件判断列表size为空
                Predicate predicate = cb.isEmpty(root.get("fixedAreas").as(Set.class));
                return predicate;
            }
        };
        return courierRepository.findAll(specification);
    }
}
