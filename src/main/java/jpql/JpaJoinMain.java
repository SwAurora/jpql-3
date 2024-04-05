package jpql;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaJoinMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();

        try {

            for(int i = 0; i < 10; i++) {
                Member member = new Member();
                member.setUsername("member" + i);
                member.setAge(i);

                Team team = new Team();
                team.setName("team" + i);
                member.changeTeam(team);

                em.persist(team);
                em.persist(member);
            }

            em.flush();
            em.clear();

            List<Member> result = em.createQuery("select m from Member m join m.team t", Member.class)
                    .getResultList();

            System.out.println("result.size() = " + result.size());
            for(Member member : result) {
                System.out.println("member = " + member);
                System.out.println("member.getTeam() = " + member.getTeam());
            }

            tx.commit();
        } catch(Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
