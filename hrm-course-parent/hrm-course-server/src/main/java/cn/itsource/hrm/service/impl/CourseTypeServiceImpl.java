package cn.itsource.hrm.service.impl;

import cn.itsource.hrm.client.RedisClient;
import cn.itsource.hrm.domain.CourseType;
import cn.itsource.hrm.mapper.CourseTypeMapper;
import cn.itsource.hrm.service.ICourseTypeService;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程目录 服务实现类
 * </p>
 *
 * @author ³õ´ú»ðÓ°
 * @since 2019-12-26
 */
@Service
public class CourseTypeServiceImpl extends ServiceImpl<CourseTypeMapper, CourseType> implements ICourseTypeService {

    @Autowired
    private RedisClient redisClient;

    @Override
    public List<CourseType> mnuetree() {
        //从redis中查询数据
        String courseTypestr = redisClient.get("hrm:course_type:treeData");
        List<CourseType> list=null;
        if (StringUtils.isNotEmpty(courseTypestr)){
            //如果存在
            //json字符串转java集合
            list= JSONObject.parseArray(courseTypestr, CourseType.class);
        }else {
            //如果不存在，则查询数据库
            list=mnue();
            //list集合转json字符串
            String j = JSONObject.toJSONString(list);
            //保存到redis中
            redisClient.set("hrm:course_type:treeData", j);
        }
        return list;
    }
    public List<CourseType> mnue(){
        //初始化一个集合存放一级类型
        List<CourseType> firstType=new ArrayList<>();
        //查询数据库中的所有类型
        List<CourseType> courseTypes = baseMapper.selectList(null);
        //创建一个Map，将courseTypes数据存到map中，key使用id，value就是CourseType
        Map<Long,CourseType> map=new HashMap<>();
        for (CourseType courseType:courseTypes){
            map.put(courseType.getId(), courseType);
        }

        //循环courseTypes，分配一级类型和非一级类型
        for (CourseType courseType:courseTypes){
            if (courseType.getPid().longValue()==0L){
                firstType.add(courseType);
            }else {
                CourseType parent = map.get(courseType.getPid());
                if (parent!=null){
                    parent.getChildren().add(courseType);
                }
            }
        }
        return firstType;
    }

    /**
     * 同步增删改
     */
    public void synOperate(){
        List<CourseType> courseTypes = mnuetree();
        String jsonString = JSONObject.toJSONString(courseTypes);
        redisClient.set("hrm:course_type:treeData", jsonString);
    }

    @Override
    public boolean save(CourseType entity) {
        super.save(entity);
        synOperate();
        return true;
    }

    @Override
    public boolean removeById(Serializable id) {
        super.removeById(id);
        synOperate();
        return true;
    }

    @Override
    public boolean updateById(CourseType entity) {
        super.updateById(entity);
        synOperate();
        return true;
    }
}
