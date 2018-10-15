package com.xu.springbootmybatis.service;

import com.xu.springbootmybatis.bean.Department;
import com.xu.springbootmybatis.mapper.DepartmentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.*;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @CacheConfig：配置全局的缓存配置(抽取缓存的公共配置)
 *      cacheNames：缓存名称
 *      keyGenerator：key值生成策略
 *      cacheManager：缓存管理
 *      cacheResolver：缓存解析器
 */
@CacheConfig(cacheNames = "emp")
@Service
public class DeptService {

    @Autowired
    DepartmentMapper departmentMapper;

    /**
     * 添加数据
     * @param department
     */
    public void addDepart(Department department){
        departmentMapper.insertDept(department);
    }

    /**
     * @Cacheable: 使用详细讲解如下：
     *      作用：将方法的运行结果进行缓存，以后再要相同的数据，直接从缓存中获取，不用调用该方法
     *      CacheManager管理多个Cache组件的，对缓存的真正CRUD操作在Cache组件中，每一个缓存组件有自己
     *      唯一一个名字与之对应；
     *      几个属性作用：
     *              cacheNames/value：指定缓存组件的名字 cacheNames = {"emp"}，将方法的返回结果放入到指定的缓存中，为数组方式，可以指定多个缓存
     *              key：缓存数据使用的key：可以用它来指定/默认是使用方法参数的值id   key = "#root.args[0]"
     *                      编写SqEL：#id:参数id的值   #a0  #p0  #root.agrs[0]
     *
     *              keyGenerator：key的生成器，也可以自己指定key的生成器的组件id
     *                       key/keyGenerator：二选一使用
     *              cacheManager：缓存管理器或者cacheResolver缓存解析器
     *              condition：判断条件，指定符合条件的情况才使用缓存 condition = "#id>0" (第一个参数的值>1 则进行缓存)
     *              unless：判断条件，指定符合条件的情况不使用缓存 unless = "#result == null " （返回的结果为null时不进行缓存）
     *              sync：异步缓存
     * 缓存原理：
     *      1.自动配置类 CacheAutoConfiguration ---> 默认加载 SimpleCacheConfiguration
     *      2.缓存配置类 ConcurrentMapCacheManager 缓存管理类
     *      3. 给容器中注册一个CacheManager组件
     * 运行流程：
     *      1.方法运行之前，先去查询Cache缓存组件，按照cacheNames指定的名字获取
     *          第一次获取缓存如果没有则创建
     *      2.去Cache中查找缓存的内容，使用一个key，默认就是方法的参数
     *          key是按照某种策略生成的，默认使用keyGenerator生成的，默认为SimpleKeyGenerator生成key
     *          默认为SimpleKeyGenerator生成key的默认策略：
     *              (1) 如果没有参数：key = new SimpleKey();
     *              (2) 如果有一个参数：key = 该参数值
     *              (3) 如果有多个参数：key = new SimpleKey(params);
     *      3.如果没有查询到缓存就调用目标方法
     *      4.将目标方法返回的结果，放入缓存中
     * 总结：@Cacheable
     *       @Cacheable标注的方法执行之前先来检查缓存有没有需要查询的数据，默认按照参数的值作为key去查询缓存
     *          如果没有就运行方法并将结果放入缓存中，之后再调用该方法就可以直接使用缓存中的数据了。
     * 根据id查询数据
     * @param id
     * @return
     */
//    @Cacheable(cacheNames = {"emp"}, key = "#root.args[0]",condition = "#id>0",unless = "#result == null ")
//    @Cacheable(cacheNames = {"emp"}, keyGenerator = "myKeyGenerator")
    @Cacheable(cacheNames = {"emp"}, key = "#result.id")
    public Department getDepartById(Integer id){
        System.out.println("查询数据库编号："+id);
        return departmentMapper.getDeptById(id);
    }

    /**
     * 查询信息列表
     * @return
     */
    public List<Department> listDepart(){
        System.out.println("查询信息列表");
        return departmentMapper.listDepartment();
    }

    /**
     * 更新数据
     * @param department
     * @CachePut：使用详细讲解如下：
     * 作用: 即调用方法，又更新缓存数据，修改了数据库的某个数据，同时更新缓存
     * 运行过程：
     *      1.先调用目标方法
     *      2.将目标方法的结果放入缓存中
     * 注意：
     *      实现同步更新缓存的目的，需要注意，取缓存的方法（getDepartById()）和更新缓存的方法（updateDepart()）的key值相同
     */
    @CachePut(/*cacheNames = {"emp"},*/ key = "#department.id")
    public void updateDepart(Department department){
        System.out.println("更新操作");
        departmentMapper.updateDept(department);
    }

    /**
     * 删除信息
     * @param id
     * @CacheEvict:作用：缓存清除
     *      key：指定要清除的数据
     *      allEntries：allEntries=ture 指定清除这个缓存中的所有缓存数据
     *      beforeInvocation：缓存的清除是否在方法之前执行，默认为false在方法之后执行
     */
    @CacheEvict(/*value = "emp",*/ key = "#id")
    public void deleteDepart(Integer id){
        System.out.println("删除操作");
        departmentMapper.deleteDeptById(id);
    }

    /**
     * @Caching:作用：复合使用缓存配置
     * @param departmentName
     * @return
     */
    @Caching(
            cacheable = {
                    @Cacheable(/*value = "emp",*/ key = "#departmentName")
            }
//            put = {
//                    @CachePut(value = "emp",key = "#result.id"),
//                    @CachePut(value = "emp", key = "#result.departmentName")  /*CachePut是必须先执行目标方法然后才缓存*/
//            }
    )
    public Department getDepartByLastName(String  departmentName){
        System.out.println("查询数据库名称："+departmentName);
        return departmentMapper.getDepartByDepartmentName(departmentName);
    }
}
