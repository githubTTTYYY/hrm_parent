package cn.itsource.hrm.service.impl;

import cn.itsource.hrm.domain.CourseType;
import cn.itsource.hrm.mapper.CourseTypeMapper;
import cn.itsource.hrm.service.ICourseTypeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

    @Override
    public List<CourseType> mnuetree() {
        return mnue();
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
}
