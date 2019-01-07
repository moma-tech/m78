package top.moma.m78.framework.common.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;
import java.util.function.Function;
import top.moma.m78.framework.common.dao.SuperDao;

/**
 * SuperService
 *
 * <p>Super Service
 *
 * <p>E - Entity
 *
 * @author ivan
 * @version 1.0 Created by ivan on 1/7/19 - 3:46 PM.
 */
public interface SuperService<D extends SuperDao<E>, E> extends IService<E> {
  <T> List<T> entityList(Wrapper<E> wrapper, Function<? super E, T> mapper);
}
