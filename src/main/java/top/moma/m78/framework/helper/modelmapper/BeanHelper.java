package top.moma.m78.framework.helper.modelmapper;

import com.baomidou.mybatisplus.core.toolkit.ClassUtils;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.cglib.beans.BeanMap;
import top.moma.m78.framework.helper.TypeHelper;

/**
 * BeanHelper
 *
 * <p>TODO
 *
 * @author ivan
 * @version 1.0 Created by ivan on 1/7/19 - 4:02 PM.
 */
public class BeanHelper {
  private static final ModelMapper MODEL_MAPPER;

  static {
    MODEL_MAPPER = new ModelMapper();
    MODEL_MAPPER.getConfiguration().setFullTypeMatchingRequired(true);
    MODEL_MAPPER.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
  }

  public static ModelMapper getModelMapper() {
    return MODEL_MAPPER;
  }

  public static <T> Map<String, Object> beanToMap(T bean) {
    Map<String, Object> map = Collections.emptyMap();
    if (null != bean) {
      BeanMap beanMap = BeanMap.create(bean);
      map = new HashMap<>(beanMap.keySet().size());
      for (Object key : beanMap.keySet()) {
        map.put(String.valueOf(key), beanMap.get(key));
      }
    }
    return map;
  }

  public static <T> Map<String, String> beanToStringMap(T bean) {
    Map<String, String> map = Collections.emptyMap();
    if (null != bean) {
      BeanMap beanMap = BeanMap.create(bean);
      map = new HashMap<>(beanMap.keySet().size());
      for (Object key : beanMap.keySet()) {
        if (Objects.nonNull(beanMap.get(key))) {
          map.put(String.valueOf(key), TypeHelper.castToString(beanMap.get(key)));
        }
      }
    }
    return map;
  }

  public static <T> List<Map<String, Object>> beansToMaps(List<T> beanList) {
    List<Map<String, Object>> mapList = Collections.emptyList();
    if (CollectionUtils.isNotEmpty(beanList)) {
      mapList = new ArrayList<>(beanList.size());
      Map<String, Object> map;
      T bean;
      for (T anObjList : beanList) {
        bean = anObjList;
        map = beanToMap(bean);
        mapList.add(map);
      }
    }
    return mapList;
  }

  public static <T> List<T> mapsToBeans(List<Map<String, Object>> mapList, Class<T> beanClass) {
    List<T> beanList = Collections.emptyList();
    if (CollectionUtils.isNotEmpty(mapList)) {
      beanList = new ArrayList<>(mapList.size());
      Map<String, Object> map;
      T bean;
      for (Map<String, Object> map1 : mapList) {
        map = map1;
        bean = mapToBean(map, beanClass);
        beanList.add(bean);
      }
    }
    return beanList;
  }

  public static <T> T mapToBean(Map<String, Object> map, Class<T> beanClass) {
    T bean = ClassUtils.newInstance(beanClass);
    BeanMap beanMap = BeanMap.create(bean);
    beanMap.putAll(map);
    return bean;
  }

  public static <T> List<T> listToList(List<?> sourceList, Class<T> targetClass) {
    return CollectionUtils.isEmpty(sourceList)
        ? Collections.emptyList()
        : sourceList
            .stream()
            .map(source -> beanToBean(source, targetClass))
            .collect(Collectors.toList());
  }

  public static <T> T beanToBean(Object source, Class<T> targetClass) {
    return getModelMapper().map(source, targetClass);
  }
}
