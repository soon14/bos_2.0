package io.maang.bos.service.take_delivery.impl;


import io.maang.bos.dao.take_delivery.PromotionRepository;
import io.maang.bos.domain.page.PageBean;
import io.maang.bos.domain.take_delivery.Promotion;
import io.maang.bos.service.take_delivery.PromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;


/**
 * 描述:
 *
 * @outhor ming
 * @create 2018-04-11 13:34
 */
@Service
@Transactional
public class PromotionServiceImpl implements PromotionService {

    @Autowired
    private PromotionRepository promotionRepository;

    @Override
    public void save(Promotion promotion) {
        promotionRepository.save(promotion);
    }

    @Override
    public Page<Promotion> findPageData(Pageable pageable) {
        return promotionRepository.findAll(pageable);
    }

    @Override
    public PageBean<Promotion> pageQuery(Integer page, Integer rows) {
        Pageable pageable = new PageRequest(page - 1, rows);
        Page<Promotion> pageData = promotionRepository.findAll(pageable);

        //封装到page对象
        PageBean<Promotion> pageBean = new PageBean<>();
        pageBean.setTotalCount(pageData.getTotalElements());
        pageBean.setPageData(pageData.getContent());
        return pageBean;
    }

    @Override
    public Promotion findById(Integer id) {
        return promotionRepository.findOne(id);
    }

    @Override
    public void updeStatus(Date date) {
        promotionRepository.updeStatus(date);
    }
}
