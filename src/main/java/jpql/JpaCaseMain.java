package jpql;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaCaseMain {
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

            /*String query = "select " +
                    "case when m.age <= 10 then '학생요금' " +
                    "     when m.age >= 60 then '경로요금' " +
                    "     else '일반요금' " +
                    "end " +
                    "from Member m";*/

            String query = "select coalesce(m.username, '이름 없는 회원') from Member m where m = :member";

            List<String> result = em.createQuery(query, String.class)
                    .setParameter("member", member)
                    .getResultList();

            for(String s : result) {
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
