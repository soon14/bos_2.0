package io.maang.bos.service.base.impl;

import io.maang.bos.dao.base.AreaRepository;
import io.maang.bos.domain.base.Area;
import io.maang.bos.service.base.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 描述:
 *
 * @outhor ming
 * @create 2018-03-31 21:18
 */
@Service
@Transactional
public class AreaServiceImpl implements AreaService {

    @Autowired
    private AreaRepository areaRepository;

    @Override
    public void saveBatch(List<Area> areas) {
        areaRepository.save(areas);
    }

    @Override
    public Page<Area> findPageData(Specification<Area> specification, Pageable pageable) {
        return areaRepository.findAll(specification,pageable);
    }
}
