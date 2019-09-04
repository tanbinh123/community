package com.buguagaoshu.community.mapper;

import com.buguagaoshu.community.model.Question;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author Pu Zhiwei {@literal puzhiweipuzhiwei@foxmail.com}
 * create          2019-08-15 15:47
 */
@Mapper
public interface QuestionMapper {
    /**
     * 插入问题
     *
     * @param question 问题
     * @return 结果
     */
    @Insert("insert into Questions(userId, title, classification, description, fileUrl, tag, createTime, alterTime) " +
            "values (#{userId}, #{title}, #{classification}, #{description}, #{fileUrl}, #{tag}, #{createTime}, #{alterTime})")
    @Options(useGeneratedKeys = true, keyProperty = "questionId")
    int createQuestion(Question question);

    /**
     * 更新问题
     */
    @Update("update Questions set title=#{title}, classification=#{classification}, description=#{description}, fileUrl=#{fileUrl}, tag=#{tag}, " +
            "alterTime=#{alterTime} where questionId=#{questionId}")
    int updateQuestion(Question question);


    /**
     * 查找问题
     *
     * @param questionId 问题id
     * @return 问题
     */
    @Select("SELECT * FROM Questions where questionId=#{questionId}")
    Question selectQuestionById(@Param("questionId") long questionId);

    /**
     * TODO 优化分页
     * 获取问题列表 倒序
     *
     * @param page 页码
     * @param size 每页显示数量
     * @return 问题列表
     */
    @Select("select * from Questions ORDER BY questionId DESC limit #{page}, #{size}")
    List<Question> getSomeQuestion(@Param("page") long page, @Param("size") long size);


    /**
     * TODO 优化分页
     * 获取当前用户发布的问题列表 倒序
     *
     * @param page 页码
     * @param size 每页显示数量
     * @param id   用户id
     * @return 问题列表
     */
    @Select("select * from Questions where userId=#{userId} ORDER BY questionId DESC limit #{page}, #{size}")
    List<Question> getQuestionByUserId(@Param("page") long page, @Param("size") long size, @Param("userId") long id);

    /**
     * @return 返回问题总数
     */
    @Select("SELECT COUNT(1) FROM Questions")
    long getQuestionCount();

    /**
     * @param id 用户 id
     * @return 返回问题总数
     */
    @Select("SELECT COUNT(1) FROM Questions where userId=#{id}")
    long getUserQuestionCount(long id);


    /**
     * 阅读数加1
     *
     * @param questionId 问题 id
     * @return 阅读数加 1
     */
    @Update("update Questions set viewCount=viewCount+1 where questionId=#{questionId}")
    int updateQuestionViewCount(long questionId);

    /**
     * 评论数加 n
     *
     * @param question 问题
     * @return 评论数加 n
     */
    @Update("update Questions set commentCount=commentCount+#{commentCount} where questionId=#{questionId}")
    int updateQuestionCommentCount(Question question);

    /**
     * 根据正则匹配相关问题
     *
     * @param tag        标签
     * @param questionId 当前问题id
     * @param size       返回最大数量
     * @return 相关问题
     */
    @Select("select * from Questions where tag regexp #{tag} and questionId!=#{questionId} order by questionId desc limit #{size}")
    List<Question> getRelevantQuestion(@Param("tag") String tag, @Param("questionId") long questionId, @Param("size") int size);


    /**
     * 根据正则实现简单的搜索功能
     *
     * @param search 搜索参数
     * @param page 页码
     * @param size 数量
     * @return 搜索结果
     * */
    @Select("select * from Questions where title regexp #{search} or tag regexp #{search} order by questionId desc limit #{page}, #{size}")
    List<Question> searchQuestion(@Param("search") String search, @Param("page") long page, @Param("size") long size);


    @Select("select COUNT(*) from Questions where title regexp #{search} or tag regexp #{search}")
    long searchQuestionCount(@Param("search") String search);
}
