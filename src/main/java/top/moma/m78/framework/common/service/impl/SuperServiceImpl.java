package top.moma.m78.framework.common.service.impl;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import top.moma.m78.framework.common.dao.SuperDao;
import top.moma.m78.framework.common.service.SuperService;

/**
 * SuperServiceImpl
 *
 * <p>TODO
 *
 * @author ivan
 * @version 1.0 Created by ivan on 1/7/19 - 3:48 PM.
 */
public class SuperServiceImpl<D extends SuperDao<E>, E> extends ServiceImpl<D, E>
    implements SuperService<E> {
  public <T> List<T> entityList(Wrapper<E> wrapper, Function<? super E, T> mapper) {
    return list(wrapper).stream().map(mapper).collect(Collectors.toList());
  }
}
