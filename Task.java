import java.util.*;
import java.io.*;
import java.text.SimpleDateFormat;

//Task class: Represents one task
public class Task {
    //Variables of Task Class
    private String title;
    private String description;
    private Date deadline;
    private String priority;//high, medium, low
    private boolean isCompleted;

    //Initializing Task Class
    public Task(String title, String description, Date deadline, String priority) {
        this.title = title;
        this.description = description;
        this.deadline = deadline;
        this.priority = priority;
        this.isCompleted = false; //Default Value
    }

    //Getter method for title
    public String getTitle() {
        return title;
    }

    //Getter method for priority level
    public String getPriority() {
        return priority;
    }

    //Setter method for task description
    public void setDescription(String description) {
        this.description = description;
    }

    //Setter method for task deadline
    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    //Setter method for task priority
    public void setPriority(String priority) {
        this.priority = priority;
    }

    //Method for task completion
    public void markCompleted(){
        this.isCompleted = true;
    }

    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        return String.format("Title: %s Description: %s Deadline: %s Priority: %s Completed: %s",
                title, description, deadline, priority, isCompleted ? "Yes" : "No");
    }
}

//Task class: Represents task's functions
class TaskManager{
    private ArrayList<Task> tasks;

    //Constructor
    public TaskManager(){
       tasks = new ArrayList<>();
    }

    //Add a task
    public void addTask(Task task){
        tasks.add(task);
        System.out.println("Task added: " + task);
    }

    //Edit a task by title
    public void setTasks(String title, String newDescription, Date newDeadline, String newPriority){
        for (Task task : tasks){
            if (task.getTitle().equalsIgnoreCase(title)){
                task.setDescription(newDescription);
                task.setDeadline(newDeadline);
                task.setPriority(newPriority);
                System.out.println("Task updated");
                return;
            }
        }
        System.out.println("Task not found");
    }

    //Mark a task completed
    public void completeTask( String title){
        for (Task task : tasks){
            if (task.getTitle().equalsIgnoreCase(title)){
                task.markCompleted();
                System.out.println("Task completed");
                return;
            }
        }
    }

    //Method display all tasks
    public void displayTasks(){
        if(tasks.isEmpty()){
            System.out.println("No tasks found");
        } else {
            for (Task task : tasks){
                System.out.println(task);
            }
        }
    }

    //Method filter tasks by priority then display
    public void displayTasksByPriority(String priority){
        boolean found = false;
        for (Task task : tasks){
            if (task.getPriority().equalsIgnoreCase(priority)){
                System.out.println(task);
                found = true;
            }
        } if (!found) {
            System.out.println("No tasks found with " + priority + "priority");
        }
    }

    //Save tasks to file
    public void saveTasks(String filename){

        try
            (ObjectOutputStream tos = new ObjectOutputStream(new FileOutputStream(filename))){
            tos.writeObject(tasks);
            System.out.println("Tasks saved");
            } catch (IOException e){
                System.err.println("Error saving tasks" + e.getMessage());
        }
    }

    //Load tasks from file
    @SuppressWarnings("unchecked")
    public void loadTasks(String filename){
        try(ObjectInputStream tis = new ObjectInputStream(new FileInputStream(filename))){
            tasks = (ArrayList<Task>) tis.readObject();
            System.out.println("Tasks loaded");
        } catch (IOException | ClassNotFoundException e){
            System.err.println("Error loading tasks" + e.getMessage());
        }
    }

    //Main class
    public static void main (String[] args){
        TaskManager tm = new TaskManager();
        Scanner scnr = new Scanner(System.in);
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

        while(true){
            System.out.println("\nWelcome to Task Management.");
            System.out.println("Menu:");
            System.out.println("1. Add Task");
            System.out.println("2. Edit Task");
            System.out.println("3. Complete Task");
            System.out.println("4. Display Tasks");
            System.out.println("5. Display Tasks by Priority");
            System.out.println("6. Save Tasks");
            System.out.println("7. Load File");
            System.out.println("8. Exit");
            System.out.print("Enter choice: ");
            int choice = scnr.nextInt();
            scnr.nextLine();//Avoid line skipping
            switch(choice){
                case 1: //Add task

                    System.out.println("Enter title: ");
                    String title = scnr.nextLine();

                    System.out.println("Enter description: ");
                    String description = scnr.nextLine();

                    System.out.println("Enter deadline: ");
                    String dateString = scnr.nextLine();

                    System.out.println("Enter priority: ");
                    String priority = scnr.nextLine();
                    try{
                        Date deadline = sdf.parse(dateString);
                        tm.addTask(new Task(title, description, deadline, priority));
                    } catch (Exception e){
                        System.err.println("Error");
                    }
                    break;

                case 2: // Edit task
                    System.out.print("Enter title of the task you want to edit: ");
                    String editTitle = scnr.nextLine();

                    System.out.print("Enter new description of task: ");
                    String editDescription = scnr.nextLine();

                    System.out.print("Enter new deadline: ");
                    String editDeadline = scnr.nextLine();

                    System.out.print("Enter new priority: ");
                    String editPriority = scnr.nextLine();
                    try {
                        Date newDeadline = sdf.parse(editDeadline);
                        tm.setTasks(editTitle, editDescription, newDeadline, editPriority);
                    } catch (Exception e){
                        System.err.println("Error");
                    }
                    break;
                    case 3: // Complete task
                        System.out.print("Enter title of completed task: ");
                        String completeTitle = scnr.nextLine();
                        tm.completeTask(completeTitle);
                        break;
                case 4: // View all tasks
                    tm.displayTasks();
                    break;

                case 5 : // View filtered task list
                    System.out.print("Enter priority level: ");
                    String filterPriority = scnr.nextLine();
                    tm.displayTasksByPriority (filterPriority);
                    break;

                    case 6: // Save tasks to file
                        System.out.println("Enter Name of File: ");
                        String savedFile = scnr.nextLine();
                        tm.saveTasks(savedFile);
                        break;

                        case 7:// Load Tasks from file
                            System.out.println("Enter Name of File: ");
                            String loadedFilename = scnr.nextLine();
                            tm.loadTasks(loadedFilename);
                            break;

                            case 8:// Exit Application
                                System.out.println("Goodbye.");
                                scnr.close();
                                return;
                default:
                    System.out.println("Invalid");
            }
        }
    }
}

