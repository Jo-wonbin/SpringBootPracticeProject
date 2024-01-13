package com.wonbin.practice.service;

import com.wonbin.practice.dto.MemberDto;
import com.wonbin.practice.entity.MemberEntity;
import com.wonbin.practice.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public Long save(MemberDto memberDto) {
        MemberEntity memberEntity = MemberEntity.toMemberEntity(memberDto);
        MemberEntity save = memberRepository.save(memberEntity);
        if(save != null){
            return 1L;
        }else {
            return 0L;
        }
    }

    public MemberDto login(MemberDto memberDto) {
        /*
            1. 회원이 입력한 이메일로 DB에서 조회를 함.
            2. DB에서 조회한 비밀번호와 사용자가 입력한 비밀번호가 일치하는지 판단
         */
        Optional<MemberEntity> byMemberEmail = memberRepository.findByMemberEmail(memberDto.getMemberEmail());
        if (byMemberEmail.isPresent()) {
            // 조회 결과가 있으면
            MemberEntity memberEntity = byMemberEmail.get();
            if (memberEntity.getMemberPassword().equals(memberDto.getMemberPassword())) {
                // 비밀번호 일치
                MemberDto dto = MemberDto.toMemberDto(memberEntity);
                return dto;
            } else {
                // 로그인 실패
                return null;
            }
        } else
            // 조회 결과가 없으면 널
            return null;
    }

    public List<MemberDto> findAll() {
        List<MemberEntity> memberEntityList = memberRepository.findAll();

        List<MemberDto> memberDtoList = new ArrayList<>();
        for (MemberEntity memberEntity : memberEntityList) {
            memberDtoList.add(MemberDto.toMemberDto(memberEntity));
        }
        return memberDtoList;
    }

    public MemberDto findById(Long id) {
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findById(id);
        if (optionalMemberEntity.isPresent()) {
            MemberEntity memberEntity = optionalMemberEntity.get();
            MemberDto memberDto = MemberDto.toMemberDto(memberEntity);

            return memberDto;
        } else {
            return null;
        }
    }

    public MemberDto updateForm(String myEmail) {
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findByMemberEmail(myEmail);
        if (optionalMemberEntity.isPresent()) {
            return MemberDto.toMemberDto(optionalMemberEntity.get());
        } else {
            return null;
        }
    }

    public void update(MemberDto memberDto) {
        memberRepository.save(MemberEntity.toUpdateMemberEntity(memberDto));
    }

    public void delete(Long id) {
        memberRepository.deleteById(id);
    }

    public String emailCheck(String memberEmail) {
        Optional<MemberEntity> byMemberEmail = memberRepository.findByMemberEmail(memberEmail);
        if (byMemberEmail.isPresent()) {
            return null;
        } else {
            return "ok";
        }
    }
}
