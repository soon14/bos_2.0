package io.maang.bos.service.base.impl;

import io.maang.bos.dao.base.StandardRepository;
import io.maang.bos.domain.base.Standard;
import io.maang.bos.service.base.StandardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 收派标准的管理
 */

@Service
@Transactional
public class StandardServiceImpl implements StandardService {

	@Autowired
	public StandardRepository standardRepository;

	@Override
	public void save(Standard standard) {

		standardRepository.save(standard);

	}


	@Override
	public Page<Standard> findPageData(Pageable pageable) {
		return (Page<Standard>) standardRepository.findAll(pageable);
	}

    @Override
    public Standard getOne(Integer id) {
		Standard one = standardRepository.getOne(id);
		return one;
	}

    @Override
    public List<Standard> findAll() {
        return standardRepository.findAll();
    }
}
