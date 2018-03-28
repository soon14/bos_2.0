package io.maang.bos.service.base;

import io.maang.bos.domain.base.Standard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * 收派标准的管理
 */

public interface StandardService  {


	public void save(Standard standard);



    Page<Standard> findPageData(Pageable pageable);

    Standard getOne(Integer id);
}
