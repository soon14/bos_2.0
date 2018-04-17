package io.maang.bos.service.base.impl;

import io.maang.bos.dao.base.TakeTimeRepository;
import io.maang.bos.domain.base.TakeTime;
import io.maang.bos.service.base.TakeTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 描述:
 *
 * @outhor ming
 * @create 2018-04-05 16:25
 */
@Service
@Transactional
public class TakeTimeServiceImpl implements TakeTimeService {
    @Autowired
    private TakeTimeRepository takeTimeRepository;
    @Override
    public List<TakeTime> findAll() {

        return takeTimeRepository.findAll();
    }
}
