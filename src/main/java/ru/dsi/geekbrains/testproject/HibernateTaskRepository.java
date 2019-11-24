package ru.dsi.geekbrains.testproject;

import org.hibernate.cfg.Configuration;
import ru.dsi.geekbrains.testproject.exceptions.MyException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

public class HibernateTaskRepository implements TaskRepository, AutoCloseable{

    private EntityManagerFactory factory;
    private EntityManager entityManager;

    public HibernateTaskRepository(boolean createTables) {
        // Получаем фабрику менеджеров сущностей
        this.factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .buildSessionFactory();
        // Из фабрики создаем EntityManager
        this.entityManager = factory.createEntityManager();
        if(createTables){
            this.createTables();
        }
    }

    @Override
    public void close() throws MyException {
        this.factory.close();
    }

    protected void createTables(){
        this.entityManager.getTransaction().begin();
        this.entityManager.createNativeQuery("DROP TABLE IF EXISTS task").executeUpdate();
        this.entityManager.createNativeQuery("CREATE TABLE task (id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "title TEXT," +
                "owner TEXT, " +
                "assignee TEXT, " +
                "description TEXT, " +
                "status TEXT " +
                ")").executeUpdate();
        this.entityManager.createNativeQuery("insert into sqlite_sequence (name,seq) values (\"task_id\",1)").executeUpdate();
        this.entityManager.getTransaction().commit();
    }

    @Override
    public List<Task> getTasks() throws MyException {
        return entityManager.createQuery("SELECT t from Task t", Task.class).getResultList();
    }

    @Override
    public void addTask(Task task) throws MyException {
        entityManager.getTransaction().begin();
        entityManager.persist(task);
        entityManager.getTransaction().commit();
    }

    @Override
    public Task getTask(long id) throws MyException {
        entityManager.getTransaction().begin();
        Task task = entityManager.find(Task.class, id);
        entityManager.getTransaction().commit();
        return task;
    }

    @Override
    public void removeTask(long id) throws MyException {
        entityManager.getTransaction().begin();
        Task task = entityManager.find(Task.class, id);
        if(task!=null) {
            entityManager.remove(task);
        }
        entityManager.getTransaction().commit();
    }

    @Override
    public void removeTask(String title) throws MyException {
        entityManager.getTransaction().begin();
        for (Task task : entityManager.createQuery("SELECT t from Task t WHERE t.title = '" + title + "'", Task.class).getResultList()) {
            entityManager.remove(task);
        }
        entityManager.getTransaction().commit();
    }

    @Override
    public void removeTask(Task task) throws MyException {
        entityManager.getTransaction().begin();
        if(task!=null) {
            entityManager.remove(task);
        }
        entityManager.getTransaction().commit();
    }

}
