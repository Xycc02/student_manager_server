package cn.shu.student_server.service;

import cn.shu.student_server.entity.Course;
import cn.shu.student_server.mapper.CourseMapper;
import cn.shu.student_server.mapper.CourseTeacherMapper;
import cn.shu.student_server.mapper.StudentCourseTeacherMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Auther: flopsyyan
 * @Date: 2022/2/9 13:46
 * @Description: CourseService
 * @Version 1.0.0
 */

@Service
public class CourseService {
    @Autowired
    private CourseMapper courseMapper;
    @Autowired
    private CourseTeacherMapper courseTeacherMapper;
    @Autowired
    private StudentCourseTeacherMapper studentCourseTeacherMapper;

    public List<Course> findBySearch(Map<String, String> map) {
        Integer cid = null;
        Integer lowBound = null;
        Integer highBound = null;
        Integer fuzzy = 0;
        String cname = null;

        if (map.containsKey("cid")) {
            try {
                cid = Integer.parseInt(map.get("cid"));
            }
            catch (Exception e) {
            }
        }
        if (map.containsKey("lowBound")) {
            try {
                lowBound = Integer.parseInt(map.get("lowBound"));
            }
            catch (Exception e) {
            }
        }
        if (map.containsKey("highBound")) {
            try {
                highBound = Integer.valueOf(map.get("highBound"));
            }
            catch (Exception e) {
            }
        }
        if (map.containsKey("cname")) {
            cname = map.get("cname");
        }
        if (map.containsKey("fuzzy")) {
            fuzzy = (map.get("fuzzy").equals("true")) ? 1 : 0;
        }
        System.out.println("查询课程 map：" + map);
        System.out.println(cid + " " + cname + " " + fuzzy + " " + lowBound + " " + highBound);
        return courseMapper.findBySearch(cid, cname, fuzzy, lowBound, highBound);
    }

    public List<Course> findBySearch(Integer cid) {
        return courseMapper.findBySearch(cid, null, 0, null, null);
    }

    public List<Course> findById(Integer cid) {
        HashMap<String, String> map = new HashMap<>();
        if (cid != null)
            map.put("cid", String.valueOf(cid));
        return findBySearch(map);
    }

    public boolean updateById(Course course) {
        return courseMapper.updateById(course);
    }

    public boolean insertCourse(Course course) {
        return courseMapper.insertCourse(course);
    }

    public boolean deleteById(Integer cid) {
        courseTeacherMapper.deleteByCId(cid);
        studentCourseTeacherMapper.deleteByCId(cid);
        return courseMapper.deleteById(cid);
    }


}
