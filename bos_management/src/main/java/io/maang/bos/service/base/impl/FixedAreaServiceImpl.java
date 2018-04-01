package io.maang.bos.service.base.impl;

import io.maang.bos.dao.base.FixedAreaRepository;
import io.maang.bos.domain.base.FixedArea;
import io.maang.bos.service.base.FixedAreaService;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Override
    public void save(FixedArea fixedArea) {
        fixedAreaRepository.save(fixedArea);
    }
}
