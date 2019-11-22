package ru.dsi.geekbrains.testproject;

import ru.dsi.geekbrains.testproject.exceptions.MyException;

import java.io.*;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

class TaskService implements AutoCloseable{
    private TaskRepository taskRepository;

    public TaskService() throws MyException{
        //this.taskRepository = new LinkedListTaskRepository();
        /*try {
            this.taskRepository = new SQLiteTaskRepository("data/tasks.db", true);
        } catch (ClassNotFoundException | SQLException e) {
            throw new MyException("Невозможно инициализировать репозиторий", e);
        }*/
        this.taskRepository = new HibernateTaskRepository(true);
    }


    public void addTask(Task task){
        try {
            this.taskRepository.addTask(task);
        } catch (MyException e) {
            e.printStackTrace();
        }
    }

    public void removeTask(long id){
        try {
            this.taskRepository.removeTask(id);
        } catch (MyException e) {
            e.printStackTrace();
        }
    }

    public void removeTask(String title){
        try {
            this.taskRepository.removeTask(title);
        } catch (MyException e) {
            e.printStackTrace();
        }
    }

    public void removeTask(Task task){
        try {
            this.taskRepository.removeTask(task);
        } catch (MyException e) {
            e.printStackTrace();
        }
    }

    public void printTasks(){
        System.out.println("Tasks:");
        List<Task> tasks = null;
        try {
            tasks = taskRepository.getTasks();
        } catch (MyException e) {
            e.printStackTrace();
        }
        if(tasks!=null){
            tasks.forEach(System.out::println);
        }
        System.out.println("---");
    }

    //a.
    public List<Task> getTasksByStatus(String status){
        try {
            return this.taskRepository.getTasks().stream()
                    .filter(task -> status==null ? task.getStatus()==null : status.equals(task.getStatus()))
                    .collect(Collectors.toList());
        } catch (MyException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }
    //b.
    public boolean taskExists(long id){
        try {
            return this.taskRepository.getTasks().stream().anyMatch(task -> task.getId()==id);
        } catch (MyException e) {
            e.printStackTrace();
        }
        return false;
    }
    //c.
    public List<Task> getTasksSortedByStatus(){
        try {
            return this.taskRepository.getTasks().stream()
                    .sorted(Comparator.comparing(task -> Utils.maskNull(task.getStatus())))
                    .collect(Collectors.toList());
        } catch (MyException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }
    //d.
    public long getTaskCountByStatus(String status){
        try {
            return this.taskRepository.getTasks().stream()
                    .filter(task -> status==null ? task.getStatus()==null : status.equals(task.getStatus()))
                    .count();
        } catch (MyException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public void exportToFile(List<Task> tasks, File file) throws IOException {
        try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file))){
            out.writeObject(tasks);
        }
    }

    public List<Task> loadFromFile(File file) throws IOException, ClassNotFoundException {
        try(ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))){
            return (List<Task>) in.readObject();
        }
    }

    /**
     * Closes this resource, relinquishing any underlying resources.
     * This method is invoked automatically on objects managed by the
     * {@code try}-with-resources statement.
     *
     * <p>While this interface method is declared to throw {@code
     * Exception}, implementers are <em>strongly</em> encouraged to
     * declare concrete implementations of the {@code close} method to
     * throw more specific exceptions, or to throw no exception at all
     * if the close operation cannot fail.
     *
     * <p> Cases where the close operation may fail require careful
     * attention by implementers. It is strongly advised to relinquish
     * the underlying resources and to internally <em>mark</em> the
     * resource as closed, prior to throwing the exception. The {@code
     * close} method is unlikely to be invoked more than once and so
     * this ensures that the resources are released in a timely manner.
     * Furthermore it reduces problems that could arise when the resource
     * wraps, or is wrapped, by another resource.
     *
     * <p><em>Implementers of this interface are also strongly advised
     * to not have the {@code close} method throw {@link
     * InterruptedException}.</em>
     * <p>
     * This exception interacts with a thread's interrupted status,
     * and runtime misbehavior is likely to occur if an {@code
     * InterruptedException} is {@linkplain Throwable#addSuppressed
     * suppressed}.
     * <p>
     * More generally, if it would cause problems for an
     * exception to be suppressed, the {@code AutoCloseable.close}
     * method should not throw it.
     *
     * <p>Note that unlike the {@link Closeable#close close}
     * method of {@link Closeable}, this {@code close} method
     * is <em>not</em> required to be idempotent.  In other words,
     * calling this {@code close} method more than once may have some
     * visible side effect, unlike {@code Closeable.close} which is
     * required to have no effect if called more than once.
     * <p>
     * However, implementers of this interface are strongly encouraged
     * to make their {@code close} methods idempotent.
     *
     * @throws Exception if this resource cannot be closed
     */
    @Override
    public void close() throws Exception {
        if(taskRepository instanceof AutoCloseable){
            ((AutoCloseable) taskRepository).close();
        }
    }
}