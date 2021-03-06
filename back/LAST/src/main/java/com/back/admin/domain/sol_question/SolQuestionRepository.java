package com.back.admin.domain.sol_question;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SolQuestionRepository extends JpaRepository<SolQuestion,Long> {

    // 모든 질문 다 보여주기
    @Query("select q from SolQuestion q")
    List<SolQuestion> findAll();

    // 특정 학생의 질문 보여주기 findBoardByStu_id
    @Query("select q from SolQuestion q where q.studentsolq.user_no=:user_no")
    SolQuestion findByUser_no(@Param("user_no") Long user_no);

    // 특정 질문 찾기 findBySolQ_no
    @Query("select q from SolQuestion q where q.sol_q_no=:sol_q_no")
    SolQuestion findBySol_q_no(@Param("sol_q_no") Long sol_q_no);

    // 특정 회사의 질문 보여주기 findBoardByStu_id
    @Query("select q from SolQuestion q where q.sol_q_company=:sol_q_company")
    SolQuestion findByCompany(@Param("sol_q_company") String sol_q_company);

    // 특정 sol_q_want_job(직무)의 질문 보여주기 findBoardByStu_id
    @Query("select q from SolQuestion q where q.sol_q_want_job=:sol_q_want_job")
    SolQuestion findByWant_job(@Param("sol_q_want_job") String sol_q_want_job);
}
