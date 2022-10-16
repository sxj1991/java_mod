package com.lazzy.mongodb.dao;

import com.lazzy.mongodb.entity.PageVO;
import com.lazzy.mongodb.entity.Student;
import com.lazzy.mongodb.entity.StudentQueryForm;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

/**
 * mongo dao层
 */
@Repository
public class UserDao {
    private final MongoTemplate mongoTemplate;

    public UserDao(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    /**
     * 插入数据
     * @param student
     * @return
     */
    public int insertStudent(Student student) {
        student.setTime(LocalDateTime.now());
        mongoTemplate.insert(student);
        return 1;
    }

    /**
     * 更新数据
     * @param student
     * @return
     */
    public int updateStudent(Student student) {
        //通过query根据id查询出对应对象，通过update对象进行修改
        Query query = new Query(Criteria.where("_id").is(student.getId()));
        Update update = new Update().set("username", student.getUsername()).set("time",student.getTime());
        mongoTemplate.updateFirst(query, update, Student.class);
        return 1;
    }

    /**
     * 删除数据
     * @param id
     * @return
     */
    public int removeStudent(Long id) {
        Query query = new Query(Criteria.where("_id").is(id));
        mongoTemplate.remove(query, Student.class);
        return 1;
    }

    /**
     * 根据id查询
     * @param student
     * @return
     */
    public Student findOne(Student student) {
        Query query = new Query(Criteria.where("_id").is(student.getId()));
        return mongoTemplate.findOne(query, Student.class);
    }

    /**
     * 模糊查询
     * @param student
     * @return
     */
    public List<Student> findLike(Student student) {
        Pattern pattern = Pattern.compile("^.*" + student.getUsername().trim() + ".*$", Pattern.CASE_INSENSITIVE);
        Query query = new Query(Criteria.where("username").regex(pattern));
        return mongoTemplate.find(query, Student.class);
    }

    /**
     * 匹配多条数据
     * @param student
     * @return
     */
    public List<Student> findMore(Student student) {
        Query query = new Query(Criteria.where("username").is(student.getUsername()));
        return mongoTemplate.find(query, Student.class);
    }

    /**
     * 查询全部数据
     * @return
     */
    public List<Student> findAll() {
        return mongoTemplate.findAll(Student.class);
    }

    /**
     * 分页查询
     * @param queryForm
     * @return
     */
    public PageVO<Student> findByPage(StudentQueryForm queryForm) {

        Query query = new Query();
        if (queryForm.getUsername() != null) {
            query.addCriteria(Criteria.where("username").is(queryForm.getUsername()));
        }
        // 1.根据条件查询总数 总数无数据则反回空数据分页对象
        int count = (int) mongoTemplate.count(query, Student.class);
        if (count < 1) {
            return PageVO.emptyResult();
        }
        Sort orders = Sort.by(Sort.Direction.DESC, "time");
        query.with(orders);
        // 2.有数据,则查询指定页数数据 (skip =(当前页-1)*指定页长度)
        int skip = (queryForm.getPageIndex() - 1) * queryForm.getPageSize();
        query.skip(skip).limit(queryForm.getPageSize());
        List<Student> students = mongoTemplate.find(query, Student.class);
        // 3.获取总页数 总数除每页长度
        // 4.拼接返回分页响应
        return  PageVO.getPageResult(students, queryForm.getPageIndex(), queryForm.getPageSize(), count);
    }





}
