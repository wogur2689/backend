package com.example.bookbackend.member.repository;

import com.example.bookbackend.member.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@DataJpaTest
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @DisplayName("아이디 값을 전달 받아 사용자의 정보를 조회한다.")
    @Test
    void findMemberById() {
        // given
        Member member = createMember();
        Member savedMember = memberRepository.save(member);

        // when
        Member findMember = memberRepository.findById(savedMember.getId())
                .orElseThrow(() -> new IllegalArgumentException("잘못된 아이디 입니다."));

        // then
        assertThat(findMember.getName()).isEqualTo("hong");
    }

    @DisplayName("이메일을 전달 받아 회원정보를 조회한다.")
    @Test
    void findMemberByEmail() {
        // given
        Member member = createMember();
        memberRepository.save(member);

        // when
        Member findMember = memberRepository.findByEmail(member.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("해당 이메일을 가진 회원이 존재하지 않습니다."));

        // then
        assertThat(findMember.getName()).isEqualTo("hong");
    }

    @DisplayName("기존에 저장된 이메일이 있는지 확인한다.")
    @Test
    void existsByEmail() {
        // given
        Member member = createMember();
        memberRepository.save(member);

        // when
        boolean isExist = memberRepository.existsByEmail("hong@gmail.com");

        // then
        assertThat(isExist).isTrue();
    }

    private Member createMember() {
        return Member.builder()
                .email("hong@gmail.com")
                .password("zxcv1234")
                .name("hong")
                .build();
    }
}