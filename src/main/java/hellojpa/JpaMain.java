package hellojpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.hibernate.metamodel.model.domain.internal.MapMember;

import java.util.List;

public class JpaMain {
    public static void main(String[] args) {
        // "hello" persistence에 적은 이름
        // db당 하나씩 EntityManagerFactory
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        // db작업을 해야되면 EntityManager통해 작업해야한다
        EntityManager em = emf.createEntityManager();    // 데이터베이스 커넥션 받았다 생각하면 됨

        // db의 모든 변경은 트랜잭션 안에서
        EntityTransaction tx = em.getTransaction();
        tx.begin();     // DB트랜잭션 시작

        try {
            //code
            /*
            Member member = new Member();
            member.setId(1L);
            member.setName("HelloA");
            */

            //Member findMember = em.find(Member.class, 1L);
            //System.out.println("findMember.id="+findMember.getId());
            //System.out.println("findMember.name="+findMember.getName());

            //수정
            //findMember.setName("HelloJPA");
            //em.persist(findMember);  // 안해도됨  jpa통해서 entity를 가져오면 jpa가 관리함
                                        // jpa가 변경된게 있는지 트랜잭션 커밋하는 시점에 체크함 바뀐게 있으면 updateQuery날리고 commit함

            /*
            삭제
            em.remove(findMember);
            */

            //em.persist(member);   // 실제로는 spring이 다른코드들을 해주니까 em.persist 만 씀

            // 전체회원 조회
            // jpa는 테이블 대상으로 코드 짜지않음 멤버객체 대상으로 쿼리
            // JPQL 장점 ex)페이징 => .setFirstResult(1).setMaxResults(8) 방언에맞게 다 고쳐줌
            /*
            List<Member> result = em.createQuery("select m from Member as m", Member.class).getResultList();

            for (Member member : result) {
                System.out.println("member.name = "+member.getName());
            }
            */

            /*
            플러시
            Member member = new Member(200L,"member200");
            em.persist(member);

            em.flush();    // DB에 즉시 쿼리 나감 , flush해도 1차캐시는 그대로
            */

            /*
            // 준영속
            Member member = em.find(Member.class, 100L);   // 영속상태
            member.setName("AAA");

            em.detach(member);     // select 쿼리는 나감   update 쿼리는 안나감
            */

            /*
            // 저장
            Team team = new Team();
            team.setName("TeamA");
            em.persist(team);

            Member member = new Member();
            member.setUsername("member1");
            // member.setTeamID(team.getId());
            member.setTeam(team);
            em.persist(member);


            // 양쪽 모두 값 설정 => 메소드를 생성하면 편함
            //team.getMembers().add(member);

            // 조회결과는 1차캐시에서 가져오므로 쿼리 안날림
            // 쿼리 보고싶으면 em.flush(); em.clear();
            em.flush();
            em.clear();
            */


            /*
            // 조회
            Member findMember = em.find(Member.class, member.getId());

            List<Member> members = findMember.getTeam().getMembers();
            for(Member member1 : members) {
                System.out.println("m = " + member.getUsername());
            }
             */


            // teamId -> team 으로 안 찾고 바로 team을 출력할 수 있음
            //Team findTeam = findMember.getTeam();
            //System.out.println("findTeam = " + findTeam.getName());

            // 수정
            //Team newTeam = em.find(Team.class, 100L);
            //findMember.setTeam(newTeam);


            /*
            // Item
            Movie movie = new Movie();
            movie.setDirector("aaa");
            movie.setActor("bbb");
            movie.setName("해리포터");
            movie.setPrice(10000);

            em.persist(movie);
             */

            //em.clear();
            //em.flush();

            //Movie findmovie = em.find(Movie.class, movie.getId());
            //System.out.println("findmovie = " + findmovie);


            /*
            // 지연로딩
            Team team = new Team();
            team.setName("TeamA");
            em.persist(team);

            Member member = new Member();
            member.setUsername("member1");
            member.setTeam(team);
            em.persist(member);

            em.flush();
            em.clear();

            Member m = em.find(Member.class, member.getId());

            System.out.println("m = " + m.getTeam().getClass());

            System.out.println("====================");
            m.getTeam();               // 프록시 사용
            m.getTeam().getName();     // 실제 Team을 사용하는 시점에 초기화된다
            System.out.println("====================");
            */

            /*
            Child child1 = new Child();
            Child child2 = new Child();

            Parent parent = new Parent();
            parent.addChild(child1);
            parent.addChild(child2);

            em.persist(parent);
            // persist해야 저장되는데 parent에 cascade = CascadeType.ALL 하면 안적어도 저장됨
            //em.persist(child1);
            //em.persist(child2);
            */

            Member member = new Member();
            member.setUsername("A");
            member.setHomeAddress(new Address("city","street","100"));
            member.setWorkPeriod(new Period());

            em.persist(member);

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();

    }
}
