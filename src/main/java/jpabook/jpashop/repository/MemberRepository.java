package jpabook.jpashop.repository;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import jpabook.jpashop.domain.Member;
import lombok.RequiredArgsConstructor;

/**
 * @packageName : jpabook.jpashop.repository
 * @fileName    : MemberRepository.java
 * @author      : 권유진
 * @date        : 2023.06.04
 * @description : 회원 레포지토리
 */
@Repository
@RequiredArgsConstructor
public class MemberRepository {
	
	/** EntityManager를 빈으로 주입할 때 사용하는 어노테이션 (@PersistenceUnit의 경우 엔티티 매니저 팩토리를 직접 주입받는 것도 가능) */
	/* @PersistenceContext => Spring Data JPA의 경우에는 @Autowired로 주입이 가능 */
	private final EntityManager em;
	
	/**
	 * @methodName  : save
	 * @author      : 권유진
	 * @date        : 2023.06.04
	 * @description : 회원 등록
	 * @param member
	 */
	public void save(Member member) {
		em.persist(member);
	}
	
	
	/**
	 * @methodName  : findOne
	 * @author      : 권유진
	 * @date        : 2023.06.04
	 * @description : 회원 조회
	 * @param id
	 * @return Member
	 */
	public Member findOne(Long id) {
		return em.find(Member.class, id);
	}
	
	/**
	 * @methodName  : findAll
	 * @author      : 권유진
	 * @date        : 2023.06.04
	 * @description : 회원 목록 조회
	 * @return List<Member>
	 */
	public List<Member> findAll() {
		return em.createQuery("select m from Member m", Member.class)
				 .getResultList();
	}
	
	/**
	 * @methodName  : findByName
	 * @author      : 권유진
	 * @date        : 2023.06.04
	 * @description : 이름 동일한 회원 목록 조회
	 * @param name
	 * @return List<Member>
	 */
	public List<Member> findByName(String name) {
		return em.createQuery("select m from Member m where m.name = :name", Member.class)
				 .setParameter("name", name)
				 .getResultList();
	}
	
	

}
