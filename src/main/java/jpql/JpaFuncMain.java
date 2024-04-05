package jpql;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaFuncMain {
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

            String query = "select size(t.members) from Team t";

            List<Integer> result = em.createQuery(query, Integer.class).getResultList();

            for(Integer s : result) {
                System.out.println("s = " + s);
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
