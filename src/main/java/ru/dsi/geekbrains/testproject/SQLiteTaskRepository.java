package ru.dsi.geekbrains.testproject;

import ru.dsi.geekbrains.testproject.exceptions.MyException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SQLiteTaskRepository implements TaskRepository, AutoCloseable {
    private Connection connection;

    public SQLiteTaskRepository(String dbpath, boolean createTables) throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        connection = DriverManager.getConnection("jdbc:sqlite:"+dbpath);
        connection.setAutoCommit(true);
        //Создаём таблицы, если нужно
        if(createTables){
            try(Statement ps = connection.createStatement()) {
                ps.executeUpdate("DROP TABLE IF EXISTS task");
                ps.executeUpdate("CREATE TABLE task (id INTEGER PRIMARY KEY, " +
                        "title TEXT," +
                        "owner TEXT, " +
                        "assignee TEXT, " +
                        "description TEXT, " +
                        "status TEXT " +
                        ")");
            }
        }
    }

    private Task getTaskFromRS(ResultSet rs) throws SQLException {
        final long id = rs.getLong("id");
        final String title = rs.getString("title");
        final String owner = rs.getString("owner");
        final String assignee = rs.getString("assignee");
        final String description = rs.getString("description");
        final String status = rs.getString("status");
        Task task = new Task(id,title, owner, status);
        task.setAssignee(assignee);
        task.setDescription(description);
        return task;
    }

    @Override
    public List<Task> getTasks() throws MyException{
        List<Task> tasks = new ArrayList<>();
        try(PreparedStatement ps = connection.prepareStatement("SELECT * FROM task")){
            try(ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    tasks.add(getTaskFromRS(rs));
                }
            }
        } catch (SQLException e) {
            throw new MyException("Ошибка получения списка задач", e);
        }
        return tasks;
    }

    @Override
    public void addTask(Task task) throws MyException {
        try (PreparedStatement ps = connection.prepareStatement("INSERT INTO task (id, title, owner, assignee, description, status) VALUES(?,?,?,?,?,?)")){
            ps.setLong(1, task.getId());
            ps.setString(2, task.getTitle());
            ps.setString(3, task.getOwner());
            ps.setString(4, task.getAssignee());
            ps.setString(5, task.getDescription());
            ps.setString(6, task.getStatus());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new MyException("Ошибка при добавлении задачи "+task, e);
        }
    }

    @Override
    public Task getTask(long id) throws MyException {
        Task task = null;
        try(PreparedStatement ps = connection.prepareStatement("SELECT * FROM task WHERE id = ?")){
            ps.setLong(1, id);
            try(ResultSet rs = ps.executeQuery()) {
                if(rs.next()) {
                    task = getTaskFromRS(rs);
                }
            }
        } catch (SQLException e) {
            throw new MyException("Ошибка получения задачи с id = "+id, e);
        }
        return task;
    }

    /**
     * Если в базе нет задачи с таким id, то ошибки не будет. При желании можно изменить логику.
     * @param id
     * @throws MyException
     */
    @Override
    public void removeTask(long id) throws MyException {
        try(PreparedStatement ps = connection.prepareStatement("DELETE FROM task WHERE id=?")){
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new MyException("Ошибка удаления задачи id = "+id, e);
        }
    }

    /**
     * Удалит все задачи с title. Прижелании можно изменить логику.
     * @param title
     * @throws MyException
     */
    @Override
    public void removeTask(String title) throws MyException {
        try(PreparedStatement ps = connection.prepareStatement("DELETE FROM task WHERE title=?")){
            ps.setString(1, title);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new MyException("Ошибка удаления задачи с title = "+title, e);
        }
    }

    @Override
    public void removeTask(Task task) throws MyException {

    }

    @Override
    public void close() throws Exception {
        this.connection.close();
    }
}
