package com.back.admin.service;

import com.back.admin.domain.experience.Experience;
import com.back.admin.domain.experience.ExperienceRepository;
import com.back.admin.domain.user.User;
import com.back.admin.domain.user.UserRepository;
import com.back.admin.web.dto.experience.ExperienceResponseDto;
import com.back.admin.web.dto.experience.ExperienceSaveRequestDto;
import com.back.admin.web.dto.experience.ExperienceUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExperienceService {
    private final ExperienceRepository experienceRepository;
    private final UserRepository userRepository;

    // 관리자가 모든 학생의 경험을 확인하기
    public List<Experience> selectAll() {
        return experienceRepository.findAll();
    }


    // 특정 경험 보여주기
    public Experience findByExperience(Long experience_no) {
        return experienceRepository.findByExperience_no(experience_no);
    }


    // 학생 개인의 경험 확인하기
    @Transactional
    public List<ExperienceResponseDto> findExperienceByStu_no(Long stu_no) {
        return experienceRepository.findByStu_no(stu_no);
    }

    // 경험 저장
    @Transactional
    public void save(ExperienceSaveRequestDto experienceSaveRequestDto, Long user_no) {
        User student = userRepository.findByUser_no(user_no);
        experienceRepository.save(experienceSaveRequestDto.toEntity(student));
    }

    // 경험 수정
    @Transactional
    public boolean update(Long experience_no, String user_id_email, ExperienceUpdateRequestDto experienceUpdateRequestDto) {
        Experience experience = experienceRepository.findByExperience_no(experience_no);
        String exp_stu_id = experience.getStudentexperience().getUser_id_email();
        if (exp_stu_id.equals(user_id_email)) { //수정 권한이 있어
            experience.update(experienceUpdateRequestDto.getExperience_start(), experienceUpdateRequestDto.getExperience_end(),
                    experienceUpdateRequestDto.getExperience_title(), experienceUpdateRequestDto.getExperience_content(),
                    experienceUpdateRequestDto.getExperience_tag());
            return true;
        } else { //수정 권한이 없어
            return false;
        }
    }


    // 경험 삭제
    @Transactional
    public boolean delete(Long experience_no, String user_id_email){
        Experience experience = experienceRepository.findByExperience_no(experience_no); //엔티티 하나의 레코드를 가져옴
        String exp_stu_id = experience.getStudentexperience().getUser_id_email();//현재 리뷰작성자 아이디.
        if (exp_stu_id.equals(user_id_email)) { //삭제 권한이 있어
            experienceRepository.delete(experience);
            return true;
        } else { //삭제 권한이 없어
            return false;
        }
    }

}
