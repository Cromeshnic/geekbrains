package ru.dsi.geekbrains.testproject.repositories;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import ru.dsi.geekbrains.testproject.entities.Task;
import ru.dsi.geekbrains.testproject.exceptions.MyException;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class HibernateTaskRepository implements TaskRepository {
    private SessionFactory factory;

    public HibernateTaskRepository() {

    }

    @Autowired
    @Qualifier(value = "sessionFactory")
    public void setFactory(SessionFactory factory) {
        this.factory = factory;
    }

    @Override
    public List<Task> getTasks() throws MyException {
        EntityManager entityManager = factory.createEntityManager();
        List<Task> tasks = entityManager.createQuery("SELECT t from Task t", Task.class).getResultList();
        entityManager.close();
        return tasks;
    }

    @Override
    public void addTask(Task task) throws MyException {
        EntityManager entityManager = factory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(task);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    @Override
    public Task getTask(long id) throws MyException {
        EntityManager entityManager = factory.createEntityManager();
        entityManager.getTransaction().begin();
        Task task = entityManager.find(Task.class, id);
        entityManager.getTransaction().commit();
        entityManager.close();
        return task;
    }

    @Override
    public void removeTask(long id) throws MyException {
        EntityManager entityManager = factory.createEntityManager();
        entityManager.getTransaction().begin();
        Task task = entityManager.find(Task.class, id);
        if(task!=null) {
            entityManager.remove(task);
        }
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    @Override
    public void removeTask(String title) throws MyException {
        EntityManager entityManager = factory.createEntityManager();
        entityManager.getTransaction().begin();
        for (Task task : entityManager.createQuery("SELECT t from Task t WHERE t.title = '" + title + "'", Task.class).getResultList()) {
            entityManager.remove(task);
        }
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    @Override
    public void removeTask(Task task) throws MyException {
        EntityManager entityManager = factory.createEntityManager();
        entityManager.getTransaction().begin();
        if(task!=null) {
            entityManager.remove(task);
        }
        entityManager.getTransaction().commit();
        entityManager.close();
    }

}
