package com.lazzy.mongodb.dao;

import com.lazzy.mongodb.entity.PageVO;
import com.lazzy.mongodb.entity.Comment;
import com.lazzy.mongodb.entity.UserQueryForm;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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
     * @param comment
     * @return
     */
    @Transactional
    public int insertComment(Comment comment) {
        comment.setTime(LocalDateTime.now());
        mongoTemplate.insert(comment);
        return 1;
    }

    /**
     * 更新数据
     * @param comment
     * @return
     */
    public int updateComment(Comment comment) {
        //通过query根据id查询出对应对象，通过update对象进行修改
        Query query = new Query(Criteria.where("_id").is(comment.getId()));
        Update update = new Update().set("username", comment.getUsername()).set("time", comment.getTime());
        mongoTemplate.updateFirst(query, update, Comment.class);
        return 1;
    }

    /**
     * 删除数据
     * @param id
     * @return
     */
    public int removeComment(Long id) {
        Query query = new Query(Criteria.where("_id").is(id));
        mongoTemplate.remove(query, Comment.class);
        return 1;
    }

    /**
     * 根据id查询
     * @param comment
     * @return
     */
    public Comment findOne(Comment comment) {
        Query query = new Query(Criteria.where("_id").is(comment.getId()));
        return mongoTemplate.findOne(query, Comment.class);
    }

    /**
     * 模糊查询
     * @param comment
     * @return
     */
    public List<Comment> findLike(Comment comment) {
        Pattern pattern = Pattern.compile("^.*" + comment.getUsername().trim() + ".*$", Pattern.CASE_INSENSITIVE);
        Query query = new Query(Criteria.where("username").regex(pattern));
        return mongoTemplate.find(query, Comment.class);
    }

    /**
     * 匹配多条数据
     * @param comment
     * @return
     */
    public List<Comment> findMore(Comment comment) {
        Query query = new Query(Criteria.where("username").is(comment.getUsername()));
        return mongoTemplate.find(query, Comment.class);
    }

    /**
     * 查询全部数据
     * @return
     */
    public List<Comment> findAll() {
        return mongoTemplate.findAll(Comment.class);
    }

    /**
     * 分页查询
     * @param queryForm
     * @return
     */
    public PageVO<Comment> findByPage(UserQueryForm queryForm) {

        Query query = new Query();
        if (queryForm.getUsername() != null) {
            query.addCriteria(Criteria.where("username").is(queryForm.getUsername()));
        }
        // 1.根据条件查询总数 总数无数据则反回空数据分页对象
        int count = (int) mongoTemplate.count(query, Comment.class);
        if (count < 1) {
            return PageVO.emptyResult();
        }
        Sort orders = Sort.by(Sort.Direction.DESC, "time");
        query.with(orders);
        // 2.有数据,则查询指定页数数据 (skip =(当前页-1)*指定页长度)
        int skip = (queryForm.getPageIndex() - 1) * queryForm.getPageSize();
        query.skip(skip).limit(queryForm.getPageSize());
        List<Comment> comments = mongoTemplate.find(query, Comment.class);
        // 3.获取总页数 总数除每页长度
        // 4.拼接返回分页响应
        return  PageVO.getPageResult(comments, queryForm.getPageIndex(), queryForm.getPageSize(), count);
    }





}
