package io.maang.bos.service.base;

import io.maang.bos.domain.base.TakeTime;

import java.util.List;

public interface TakeTimeService {
    List<TakeTime> findAll();
}
