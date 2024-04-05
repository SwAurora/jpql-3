package jpql;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaUpdateMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();

        try {
            Member member = new Member();
            member.setUsername("member1");
            member.setAge(10);
            member.setType(MemberType.ADMIN);

            Team team = new Team();
            team.setName("teamA");
            member.changeTeam(team);

            em.persist(team);
            em.persist(member);

            em.flush();
            em.clear();

            int resultCnt = em.createQuery("update Member m set m.age = 20").executeUpdate();

            System.out.println("resultCnt = " + resultCnt);

            em.clear();

            Member findMember = em.find(Member.class, member.getId());

            System.out.println("member.getAge() = " + member.getAge());
            System.out.println("findMember.getAge() = " + findMember.getAge());

            tx.commit();
        } catch(Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
