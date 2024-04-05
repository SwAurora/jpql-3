package jpql;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaNamedQueryMain {
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

            List<Member> result = em.createNamedQuery("Member.findByUsername", Member.class).setParameter("username", "member1").getResultList();

            for(Member m : result) {
                System.out.println("m = " + m);
            }

            Long result2 = em.createNamedQuery("Member.count", Long.class).getSingleResult();

            System.out.println("result2 = " + result2);

            tx.commit();
        } catch(Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
