package jpabook.jpashop.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;

/**
 * @packageName : jpabook.jpashop.service
 * @fileName    : MemberService.java
 * @author      : 권유진
 * @date        : 2023.06.04
 * @description : 회원 서비스
 */
@Service
@Transactional(readOnly = true) // 읽기 전용일 경우 최적화 진행함
@RequiredArgsConstructor // final이 있는 필드만 생성자 주입
public class MemberService {

	private final MemberRepository memberRepository;
	
	/**
	 * @methodName  : join
	 * @author      : 권유진
	 * @date        : 2023.06.04
	 * @description : 회원 가입
	 * @param member
	 * @return Long
	 */
	@Transactional
	public Long join(Member member) {
		validateDuplicateMember(member); // 중복 회원 검증 로직
		memberRepository.save(member);
		return member.getId();
	}
	
	/**
	 * @methodName  : validateDuplicateMember
	 * @author      : 권유진
	 * @date        : 2023.06.04
	 * @description : 중복 회원 검증
	 * @param member
	 */
	private void validateDuplicateMember(Member member) {
		//EXCEPTION
		List<Member> findMembers = memberRepository.findByName(member.getName());
		if(!findMembers.isEmpty()) {
			throw new IllegalStateException("이미 존재하는 회원입니다.");
		}
	}
	
	/**
	 * @methodName  : findMembers
	 * @author      : 권유진
	 * @date        : 2023.06.04
	 * @description : 회원 목록 조회
	 * @return List<Member>
	 */
	public List<Member> findMembers() {
		return memberRepository.findAll();
	}
	
	/**
	 * @methodName  : findMember
	 * @author      : 권유진
	 * @date        : 2023.06.04
	 * @description : 회원 조회
	 * @param memberId
	 * @return Member
	 */
	public Member findMember(Long memberId) {
		return memberRepository.findOne(memberId);
	}
	
	

}
