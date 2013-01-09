# This file should contain all the record creation needed to seed the database with its default values.
# The data can then be loaded with the rake db:seed (or created alongside the db with db:setup).
#
# Examples:
#
#   cities = City.create([{ :name => 'Chicago' }, { :name => 'Copenhagen' }])
#   Mayor.create(:name => 'Emanuel', :city => cities.first)
User.delete_all
Course.delete_all
Assignment.delete_all
Question.delete_all
Submission.delete_all

jeremy_student = User.create(:username => "Jeremy", :password => "comp4350", :email => "jeremy@uofm.com", :role => "student", :has_seen_home => false, :has_seen_courses => false, :has_seen_grades => false)
nathan_student = User.create(:username => "Nathan", :password => "comp4350", :email => "nathan@uofm.com", :role => "student", :has_seen_home => false, :has_seen_courses => false, :has_seen_grades => false)
joe_student = User.create(:username => "Joe", :password => "comp4350", :email => "joe@uofm.com", :role => "student", :has_seen_home => false, :has_seen_courses => false, :has_seen_grades => false)
jane_student = User.create(:username => "Jane", :password => "comp4350", :email => "jane@uofm.com", :role => "student", :has_seen_home => false, :has_seen_courses => false, :has_seen_grades => false)
mark_student = User.create(:username => "Mark", :password => "comp4350", :email => "mark@uofm.com", :role => "student", :has_seen_home => false, :has_seen_courses => false, :has_seen_grades => false)
mary_student = User.create(:username => "Mary", :password => "comp4350", :email => "mary@uofm.com", :role => "student", :has_seen_home => false, :has_seen_courses => false, :has_seen_grades => false)
zapp_student = User.create(:username => "ZappStudent", :password => "comp4350", :email => "zappstudent@uofm.com", :role => "student", :has_seen_home => false, :has_seen_courses => false, :has_seen_grades => false)

zapp_teacher = User.create(:username => "Zapp", :password => "comp4350", :email => "zapp@uofm.com", :role => "teacher", :has_seen_home => false, :has_seen_courses => false, :has_seen_grades => false)
graham_teacher = User.create(:username => "Graham", :password => "comp4350", :email => "graham@uofm.com", :role => "teacher", :has_seen_home => false, :has_seen_courses => false, :has_seen_grades => false)

comp3430 = Course.create(:course_name => "COMP3430", :course_description => "Operating Systems")
comp4020 = Course.create(:course_name => "COMP4020", :course_description => "Human Computer Interaction II")
comp4350 = Course.create(:course_name => "COMP4350", :course_description => "Software Development II")
comp4050 = Course.create(:course_name => "COMP4050", :course_description => "Human Computer Interaction II")
comp4190 = Course.create(:course_name => "COMP4190", :course_description => "Artificial Intelligence II")
comp4580 = Course.create(:course_name => "COMP4580", :course_description => "Computer Security")

jeremy_student.courses = [comp3430, comp4350]
  jeremy_student.save
nathan_student.courses = [comp4020, comp4350]
  nathan_student.save
joe_student.courses = [comp4050, comp4350]
  joe_student.save
jane_student.courses = [comp4190, comp4350]
  jane_student.save
mark_student.courses = [comp4580, comp4350]
  mark_student.save
mary_student.courses = [comp3430, comp4020, comp4350, comp4050, comp4190, comp4580]
  mary_student.save
zapp_student.courses = [comp3430, comp4020, comp4350, comp4050, comp4190, comp4580]
  mary_student.save


zapp_teacher.courses = [comp4350, comp4020, comp4050, comp4190]
  zapp_teacher.save
graham_teacher.courses = [comp3430, comp4580]
  graham_teacher.save

Announcement.create(:course => comp3430, :title => "Good news everyone!", :text => "Assignment 1 is available", :created_at => "2012-01-15 11:34:16 UTC", :updated_at => "2012-01-15 11:34:16 UTC")
Announcement.create(:course => comp3430, :title => "Assignment 1 revised", :text => "Please look at Assignment 1 again, as I have modified question 2.", :created_at => "2012-1-17 20:01:58 UTC", :updated_at => "2012-1-17 20:01:58 UTC")
Announcement.create(:course => comp4350, :title => "Last day for cookies!", :text => "Remember to pick up your bag of cookies tomorrow..", :created_at => "2012-03-14 11:05:45 UTC", :updated_at => "2012-03-14 11:05:45 UTC")

#### COMP3430
a1_3430 = Assignment.create(:course => comp3430, :title => "Assignment 1", :description => "regular assignment", :value => 8, :due => "3/2/2012")
q1_a1_3430 = Question.create(:assignment => a1_3430, :question => "Explain why it does not make sense to make an exec call in multi-threaded code. What might happen if two threads (in the same address space) both executed an exec at exactly the same time on a multi-core machine? Explain!", :points => 3)
Submission.create(:response => "Response 1", :user => jeremy_student, :question => q1_a1_3430, :graded => true, :points => 3) 
q2_a1_3430 = Question.create(:assignment => a1_3430, :question => "In the first lab, you probably used the string tokenizer (strtok) library routine to isolate the arguments to each program entered to your shell. The routine strtok was designed to be used with non-concurrent code. There is another version called strtok_r that can be safely used in concurrent programs. Briefly explore this version (using the  man command or checking the web) and briefly explain why this version is necessary and how it circumvents the problem that would occur if strtok were to be used in a concurrent program.", :points => 2)
Submission.create(:response => "Response 2", :user => jeremy_student, :question => q2_a1_3430, :graded => true, :points => 2) 
q3_a1_3430 = Question.create(:assignment => a1_3430, :question => "Command shells commonly provide a facility whereby shell \"variables\" may be defined and then used in commands  provided to the shell. Such variable names normally begin with a special symbol (e.g. \'$\') and are then followed by some letters (e.g. \'$HOMEDIR\'). A mechanism must be provided to set such variables and then whenever the variable occurs, it is replaced by the string it was set to. For example, a user might type something like \'set $HOMEDIR=/home/cs/staff/pgraham\' at the shell prompt. Later, if the same user typed \'ls $HOMEDIR\', the shell would execute the command \'ls /home/cs/staff/pgraham\'. You are to extend the mini-shell you created for the last question in the lab to support such variables and their use based on the syntax described in the examples above. There is lots of C code on the web which you are free to consult. To avoid possible issues of academic dishonesty, however, do not cut and paste any of the code you may find online into your program.", :points => 10)
Submission.create(:response => "Response 3", :user => jeremy_student, :question => q3_a1_3430, :graded => true, :points => 10) 
q4_a1_3430 = Question.create(:assignment => a1_3430, :question => "You are to write the following program using p-threads in C on Linux. Your program will begin by initializing an in-memory array \'ar\' to contain integers read from a file  named \'numbers.txt\'. (The first entry in the array will correspond to the integer on the first line of the file, the second entry to the value from the second line and so on.) Your program should keep track of the number of integers read in a variable NumRead. Your program will be passed two command line parameters: NumThreads and NumElts (both integers). After reading the integers, it must then create  NumThreads threads each running a function DoAve. Your program should explicitly pass both NumElts and NumRead to each thread running DoAve. The routine DoAve should run a loop from NumElts times  and in each iteration of the loop it should generate a random number between 0 and NumRead-1 (see \'man srand\'), add the contents of the corresponding array element from ar into a sum and then compute the integer average of the NumElts numbers and print it together with the thread\'s p-threads identifier, then generate a second random number between 0 and 2 and sleep for that number of seconds (see \'man sleep\'). Note that there is also lots of p-threads example code available on the web that you can look at. To avoid possible issues of academic dishonesty, again, do not cut and paste any of the code you may find into your program. Explain briefly why you get the output that you do.", :points => 10)
Submission.create(:response => "Response 4", :user => jeremy_student, :question => q4_a1_3430, :graded => true, :points => 10) 

a2_3430 = Assignment.create(:course => comp3430, :title => "Assignment 2", :description => "regular assignment", :value => 8, :due => "4/3/2012")
q1_a2_3430 = Question.create(:assignment => a2_3430, :question => "One process collects data from an experimental instrument as it operates (e.g. from a detector in a particle accelerator) producing one new dataset each minute. Another process  preprocesses such  datasets to produce  new ones in a form that is more convenient for later processing (e.g. recognizing and filtering noise from the data). A third and final process uploads the pre-processed datasets to a web site where they can later be accessed by scientists who will  download and  mine the data to  discover any scientifically relevant patterns. All three processes must execute concurrently. Obviously we cannot pre-process a dataset that does not yet exist, nor can we upload one that has not yet been pre-processed. In general, the speed of the three processes is comparable but sometimes processes may be delayed due to other activity on the machine(s) they are running on.  Using your knowledge of synchronization from the lectures, you are to design a synchronization solution for this problem and then provide pseudo-code that synchronizes the concurrent operation of the three processes using (a) semaphores (with types of your choosing) and (b) monitors with whatever data structures you decided to use. You are free to expand on and re-use techniques discussed in the lectures. How might an implementation of your solution change if one of the latter two processes was periodically delayed for a longer  time? Why do you think it is necessary to deal with this issue? (Hint: think about the first process.)", :points => 12)
Submission.create(:response => "Response 1", :user => jeremy_student, :question => q1_a2_3430, :graded => true, :points => 12) 
q2_a2_3430 = Question.create(:assignment => a2_3430, :question => "You are to write a simple P-threads program (no synchronization required) whose main thread will declare a global shared array of 480,000 integers where each element is initialized to the value of the corresponding array index (i.e. element i in the array will always contain the value i).  The main thread should then prompt for and read two values (one between 0 and 79,999 which is the value to be searched for in the array and a second (having the value of 1, 2, 4 or 8) which is the number of threads to use in doing the search. For those who are unfamiliar with C, you can use the following code sequence to do the necessary I/O.<p style=\"margin:10px\">int  SearchVal, NumThreads;<br/>printf(\"Please enter the value to search for: \");<br/>scanf(\"%d\",&SearchVal);<br/>printf(\"Please enter the number of threads to use: \");<br/>scanf(\"%d\",&NumThreads);</p>Your main thread should then spawn NumThreads threads each one running the function search, which is passed its thread number (not thread id), MyThreadNum, which is in the range 0 through  NumThreads-1. At this point, the main thread may simply call pthread_exit() to terminate. The idea is that to speed up the searching, the processing on the array will be divided into  NumThreads pieces with each one being done by a separate thread. After partitioning the array, each piece of the array will contain NumElts = 480,000/NumThreads elements.) Your search routine should search for the value SearchVal in elements  MyThreadNum*NumElts through  ((MyThreadNum+1)* NumElts)-1 (inclusive) of the array. For convenience, you may assume that SearchVal is declared globally just like the array. Whichever thread finds the value in the array should print a message announcing that the value has been found and the position in the array at which it was found.<br/><br/>You can run your program on any of the Linux Lab machines specifying a search key value of 479,999 for runs with 1, 2, 4, and 8 threads, respectively. Use the Linux time command to determine the \"user time\" (see  man time) for each run. You must include a file (called  Results.txt)  specifying and briefly discussing the runtimes you discover together with your code when you hand it in. Note that the times you see will depend on what other work is running on the machine when you do your testing so you should run your program multiple times (ideally when the machine is lightly loaded) and report  average runtimes. You can, of course, log in to any Linux lab machine to get the code working prior to your testing. Do not leave this to the last minute or you will mess up one another\'s runtimes! Based on the runtimes you see, how many cores do you think the machine has? How many does it actually have (remember /proc/cpuinfo?) Try to explain any differences.", :points => 6)
Submission.create(:response => "Response 2", :user => jeremy_student, :question => q2_a2_3430, :graded => true, :points => 6) 
q3_a2_3430 = Question.create(:assignment => a2_3430, :question => "Using pthreads, you are to write a C program that will implement the key functionality of a multi-threaded print manager. Your program should accept two integer command line parameters: NumPrintClients and NumPrinters (via argv[]). It should begin by first creating  NumPrinters instances of a  PrintServer thread and then creating NumPrintClients instances of a PrintClient thread (both described below).  Your main thread should then simply exit (remembering to dutifully call  pthread_exit()before doing so). The PrintClient threads (which play the role of applications requesting printing) and the PrintServer threads (which play the role of the OS code managing each printer) should communicate with each other using a bounded buffer containing up to 3outstanding PrintRequest entries. A PrintRequest structure contains three things: a long integer  client id, a string file name, and an  integer file size. A suitable C language struct definition for a PrintRequest is the following:<p style=\"margin:10px\">typedef struct PR {<br/>&nbsp;&nbsp;&nbsp;&nbsp;long clientID;<br/>&nbsp;&nbsp;&nbsp;&nbsp;char *fileName; //ptr to a dynamically alloc\'d string<br/>&nbsp;&nbsp;&nbsp;&nbsp;int fileSize;<br/>} PrintRequest,*PrintRequestPTR;</p>A declaration for the bounded buffer itself would then be the following:<p style=\"margin:10px\">PrintRequest BoundedBuffer[3];</p>Your PrintClient code should simply consist of a loop that runs six (6) times. In eachiteration of the loop your code should insert a new PrintRequest entry into the bounded buffer. Each entry should contain the client\'s pthread-id (i.e. obtained by calling pthread_self()), a \"filename\" consisting of the string \'File\' with the client id and loop iteration appended to it separated with underscores (e.g. the filename would be File_248_3 if the thread had id 248 and was executing in the 3rd iteration of its loop), and a random number between 200 and  20,000 representing the \"size\" (in characters) of the corresponding file.  Your client should print a message identifying itself (using its thread id) together with the relevant filename immediately after it inserts an entry into the bounded buffer. At the end of each iteration, your PrintClient code should sleep for a random number of seconds (between 1 and 3).<br/><br/>Your PrintServer code should run an infinite loop attempting to remove entries from the bounded buffer. When an entry is removed, the code should print a message specifying its own id (via  pthread_self() again) together with all the information from the PrintRequest and the current time (using the time and ctime functions described in <time.h>) indicating that it is starting to \"print\" the job. It should then sleep for a number of seconds determined by the size of the \"file\" to be printed. You should assume that printers print 8000 characters per second. When it awakens, your thread code should print a message containing the same information (with updated time) indicating it has completed the print job.<br/><br/>Your code should include separate routines called by the  PrintClient and PrintServer threads (in the obvious way) to insert into and remove from the bounded buffer. These routines should manipulate a mutex variable to ensure mutually exclusive access to the buffer as well as two condition variables used to allow threads attempting to insert into a full buffer or remove from an empty one to wait and be signaled when the condition they are waiting on has been satisfied. (i.e. You will be doing a pseudo-monitor implementation of the producer consumer problem described in class.)<br/><br/>Test your program with five (5) print clients and two (2) printers. Note that since your PrintServer threads are running infinite loops, you will need to use CTRL-C to terminate your program once it is finished.", :points => 22)
Submission.create(:response => "Response 3", :user => jeremy_student, :question => q3_a2_3430, :graded => true, :points => 22) 

a3_3430 = Assignment.create(:course => comp3430, :title => "Assignment 3", :description => "regular assignment", :value => 8, :due => "19/3/2012")
q1_a3_3430 = Question.create(:assignment => a3_3430, :question => "Consider a system with 5 processes as described in the following table.<table border=\"1px\" style=\"margin:10px;\" width=\"100%\"><thead><tr><th>Process</th><th>Arrival Time</th><th>Duration</th><th>Priority</th></tr></thead><tbody><tr><td>P1</td><td>2</td><td>5</td><td>3</td></tr><tr><td>P2</td><td>4</td><td>6</td><td>1</td></tr><tr><td>P3</td><td>5</td><td>6</td><td>4</td></tr><tr><td>P4</td><td>5</td><td>7</td><td>2</td></tr><tr><td>P5</td><td>8</td><td>5</td><td>2</td></tr></tbody></table>Illustrate the execution of the processes under the PRIORITY  scheduling algorithm (assuming 4 is high priority) for time slices of 2 and 3 time units and then again under the round robin scheduling algorithm (where priorities are irrelevant), also for 2 and 3 time units. Use GANTT charts (plots of activity by processes vs. time) to show when the various processes execute. You will have a total of four GANTT charts, one for each combination of algorithm and time slice size. Be sure to factor in arrival time in your work.", :points => 16)
Submission.create(:response => "Response 1", :user => jeremy_student, :question => q1_a3_3430, :graded => true, :points => 16) 
q2_a3_3430 = Question.create(:assignment => a3_3430, :question => "Using Linux  processes (not p-threads), SysV shared memory segments and SysV semaphores, you are to write a C program that will re-implement the key functionality of the multi-threaded print manager from assignment 2. You are free to re-use any code you find in the sample solution for A2 but will, of course, have to change it to work with processes, shared memory segments and semaphores instead of threads with mutexes and condition variables. To simplify the problem, you need only support a single  PrintClient and PrintServer. A modified description of the problem from the last assignment follows:<br/><br/>Your program should begin by first creating single instances of a PrintServer process and a  PrintClient process. The  PrintClient process (which plays the role of an application requesting printing) and the PrintServer process (which plays the role of the OS code managing a printer) should communicate with each other using a bounded buffer containing up to  3 outstanding  PrintRequest entries  stored in a shared memory segment (created using your student number as a key). A  PrintRequest structure contains three things: a long integer client id, a string file name, and an integer file size. A suitable C language struct definition for a PrintRequest is the following:<p style=\"margin:10px;\">typedef struct PR {<br/>&nbsp;&nbsp;&nbsp;&nbsp;long clientID;<br/>&nbsp;&nbsp;&nbsp;&nbsp;char filename[64]; //ptr unusable in shared memory<br/>&nbsp;&nbsp;&nbsp;&nbsp;int fileSize;<br/>} PrintRequest;</p>A declaration for the bounded buffer to be stored in the shared segment would then be:<p style=\"margin:10px;\">PrintRequest BoundedBuffer[4];</p>Note that you will also need to store the various queue-related variables (pointers, count of elements in the queue) in the shared segment.<br/><br/>Your PrintClient code should simply consist of a loop that runs six (6) times. In each iteration of the loop your code should insert a new PrintRequest entry into the bounded buffer. Each entry should contain the client\'s process id (obtained by calling getpid()), a \"filename\" consisting of the string \'File\' with the client\'s  process id and loop iteration appended to it separated with underscores (e.g. the filename would be File_2487_3 if the process had id 2487 and was executing in the 3rd iteration of its loop), and a random number between 500 and 40,000 representing the \"size\" (in characters) of the corresponding file.  Your client should print a message identifying itself (using its process id) together with the relevant filename immediately after it inserts an entry into the bounded buffer. At the end of each iteration, your code should sleep for a random number of seconds (between 1 and 3).<br/><br/>Your PrintServer code should run an infinite loop attempting to remove entries from the bounded buffer. When an entry is successfully removed, the code should print a message specifying its own id (via  getpid() again) together with all the information from the PrintRequest indicating that it is starting to \"print\" the job. It should then sleep for a number of seconds determined by the size of the \"file\" to be printed. You should assume that printers can print 7000 characters per second. When it awakens, your process code should print a message containing the same information indicating that it has completed the print job.<br/><br/>Your code should include separate routines called by the  PrintClient and PrintServer processes (in the obvious way) to  insert into and  remove from the bounded buffer. These routines should manipulate a semaphore to ensure mutually exclusive access to the buffer as well as two other semaphores used to allow a process attempting to insert into a full buffer or remove from an empty one to wait and  be signaled when the condition they are waiting on has been satisfied.<br/><br/>Note that since your PrintServer  process is running an infinite loop, you will need to use CTRL-C to terminate your program once it is finished", :points => 24)
Submission.create(:response => "Response 2", :user => jeremy_student, :question => q2_a3_3430, :graded => true, :points => 24) 

a4_3430 = Assignment.create(:course => comp3430, :title => "Assignment 4", :description => "regular assignment", :value => 8, :due => "4/4/2012")
q1_a4_3430 = Question.create(:assignment => a4_3430, :question => "Q1", :points => 40)

l1_3430 = Assignment.create(:course => comp3430, :title => "Lab 1", :description => "regular lab", :value => 3, :due => "19/1/2012")
q1_l1_3430 = Question.create(:assignment => l1_3430, :question => "Q1", :points => 40)
Submission.create(:response => "Response 2", :user => jeremy_student, :question => q1_l1_3430, :graded => true, :points => 40) 

l2_3430 = Assignment.create(:course => comp3430, :title => "Lab 2", :description => "regular lab", :value => 3, :due => "9/2/2012")
q1_l2_3430 = Question.create(:assignment => l2_3430, :question => "Q1", :points => 40)
Submission.create(:response => "Response 2", :user => jeremy_student, :question => q1_l2_3430, :graded => true, :points => 40) 

l3_3430 = Assignment.create(:course => comp3430, :title => "Lab 3", :description => "regular lab", :value => 3, :due => "8/3/2012")
q1_l3_3430 = Question.create(:assignment => l3_3430, :question => "Q1", :points => 40)
Submission.create(:response => "Response 2", :user => jeremy_student, :question => q1_l3_3430, :graded => true, :points => 40) 

l4_3430 = Assignment.create(:course => comp3430, :title => "Lab 4", :description => "regular lab", :value => 3, :due => "22/3/2012")
q1_l4_3430 = Question.create(:assignment => l4_3430, :question => "Q1", :points => 40)

t1_3430 = Assignment.create(:course => comp3430, :title => "Midterm Test", :description => "term test", :value => 16, :due => "17/2/2012")
q1_t1_3430 = Question.create(:assignment => t1_3430, :question => "Q1", :points => 40)
Submission.create(:response => "Response 2", :user => jeremy_student, :question => q1_t1_3430, :graded => true, :points => 40) 

t2_3430 = Assignment.create(:course => comp3430, :title => "Final Exam", :description => "final test", :value => 40, :due => "18/4/2012")
q1_t2_3430 = Question.create(:assignment => t2_3430, :question => "Q1", :points => 40)


#### COMP4020
a1_4020 = Assignment.create(:course => comp4020, :title => "Term Test", :description => "Test", :value => 40, :due => "19/3/2012")
q1_a1_4020 = Question.create(:assignment => a1_4020, :question => "Q1", :points => 22)
Submission.create(:response => "Response 1", :user => zapp_student, :question => q1_a1_4020, :graded => true, :points => 16) 
Submission.create(:response => "Response 1", :user => nathan_student, :question => q1_a1_4020, :graded => true, :points => 21) 
Submission.create(:response => "Response 1", :user => mary_student, :question => q1_a1_4020, :graded => true, :points => 20) 
q2_a1_4020 = Question.create(:assignment => a1_4020, :question => "Q2", :points => 27)
Submission.create(:response => "Response 2", :user => zapp_student, :question => q2_a1_4020, :graded => true, :points => 25) 
Submission.create(:response => "Response 2", :user => nathan_student, :question => q2_a1_4020, :graded => true, :points => 23) 
Submission.create(:response => "Response 2", :user => mary_student, :question => q2_a1_4020, :graded => true, :points => 15) 
q3_a1_4020 = Question.create(:assignment => a1_4020, :question => "Q3", :points => 21)
Submission.create(:response => "Response 3", :user => zapp_student, :question => q3_a1_4020, :graded => true, :points => 21) 
Submission.create(:response => "Response 3", :user => nathan_student, :question => q3_a1_4020, :graded => true, :points => 19) 
Submission.create(:response => "Response 3", :user => mary_student, :question => q3_a1_4020, :graded => true, :points => 20) 
q4_a1_4020 = Question.create(:assignment => a1_4020, :question => "Q4", :points => 25)
Submission.create(:response => "Response 4", :user => zapp_student, :question => q4_a1_4020, :graded => true, :points => 23) 
Submission.create(:response => "Response 4", :user => nathan_student, :question => q4_a1_4020, :graded => true, :points => 25) 
Submission.create(:response => "Response 4", :user => mary_student, :question => q4_a1_4020, :graded => true, :points => 22) 
q5_a1_4020 = Question.create(:assignment => a1_4020, :question => "Q4", :points => 30)
Submission.create(:response => "Response 5", :user => zapp_student, :question => q5_a1_4020, :graded => true, :points => 30) 
Submission.create(:response => "Response 5", :user => nathan_student, :question => q5_a1_4020, :graded => true, :points => 28) 
Submission.create(:response => "Response 5", :user => mary_student, :question => q5_a1_4020, :graded => true, :points => 29) 

a2_4020 = Assignment.create(:course => comp4020, :title => "Final Examination", :description => "Final", :value => 60, :due => "18/4/2012")
q1_a2_4020 = Question.create(:assignment => a2_4020, :question => "Q1", :points => 40)
Submission.create(:response => "Response 1", :user => zapp_student, :question => q1_a2_4020, :graded => false, :points => 0) 
Submission.create(:response => "Response 1", :user => nathan_student, :question => q1_a2_4020, :graded => false, :points => 0) 
Submission.create(:response => "Response 1", :user => mary_student, :question => q1_a2_4020, :graded => false, :points => 0) 
q2_a2_4020 = Question.create(:assignment => a2_4020, :question => "Q2", :points => 40)
Submission.create(:response => "Response 2", :user => zapp_student, :question => q2_a2_4020, :graded => false, :points => 0) 
Submission.create(:response => "Response 2", :user => nathan_student, :question => q2_a2_4020, :graded => false, :points => 0) 
Submission.create(:response => "Response 2", :user => mary_student, :question => q2_a2_4020, :graded => false, :points => 0) 
q3_a2_4020 = Question.create(:assignment => a2_4020, :question => "Q3", :points => 20)
Submission.create(:response => "Response 3", :user => zapp_student, :question => q3_a2_4020, :graded => false, :points => 0) 
Submission.create(:response => "Response 3", :user => nathan_student, :question => q3_a2_4020, :graded => false, :points => 0) 
Submission.create(:response => "Response 3", :user => mary_student, :question => q3_a2_4020, :graded => false, :points => 0) 
q4_a2_4020 = Question.create(:assignment => a2_4020, :question => "Q4", :points => 20)
Submission.create(:response => "Response 4", :user => zapp_student, :question => q4_a2_4020, :graded => false, :points => 0) 
Submission.create(:response => "Response 4", :user => nathan_student, :question => q4_a2_4020, :graded => false, :points => 0) 
Submission.create(:response => "Response 4", :user => mary_student, :question => q4_a2_4020, :graded => false, :points => 0) 
q5_a2_4020 = Question.create(:assignment => a2_4020, :question => "Q4", :points => 30)
Submission.create(:response => "Response 5", :user => zapp_student, :question => q5_a2_4020, :graded => false, :points => 0) 
Submission.create(:response => "Response 5", :user => nathan_student, :question => q5_a2_4020, :graded => false, :points => 0) 
Submission.create(:response => "Response 5", :user => mary_student, :question => q5_a2_4020, :graded => false, :points => 0) 

#### COMP4350
a1_4350 = Assignment.create(:course => comp4350, :title => "Proposal", :description => "Project Proposal", :value => 5, :due => "19/1/2012")
q1_a1_4350 = Question.create(:assignment => a1_4350, :question => "Proposal", :points => 5)
Submission.create(:response => "Response 1", :user => zapp_student, :question => q1_a1_4350, :graded => true, :points => 5) 
Submission.create(:response => "Response 1", :user => jeremy_student, :question => q1_a1_4350, :graded => true, :points => 4.5) 
Submission.create(:response => "Response 1", :user => nathan_student, :question => q1_a1_4350, :graded => true, :points => 4.5) 
Submission.create(:response => "Response 1", :user => joe_student, :question => q1_a1_4350, :graded => true, :points => 1) 
Submission.create(:response => "Response 1", :user => jane_student, :question => q1_a1_4350, :graded => true, :points => 2) 
Submission.create(:response => "Response 1", :user => mark_student, :question => q1_a1_4350, :graded => true, :points => 3) 
Submission.create(:response => "Response 1", :user => mary_student, :question => q1_a1_4350, :graded => true, :points => 4) 

a2_4350 = Assignment.create(:course => comp4350, :title => "Snapshot 1", :description => "Snapshot 1", :value => 10, :due => "18/2/2012")
q1_a2_4350 = Question.create(:assignment => a2_4350, :question => "Snapshot 1", :points => 10)
Submission.create(:response => "Response 2", :user => zapp_student, :question => q1_a2_4350, :graded => true, :points => 10) 
Submission.create(:response => "Response 2", :user => jeremy_student, :question => q1_a2_4350, :graded => true, :points => 2) 
Submission.create(:response => "Response 2", :user => nathan_student, :question => q1_a2_4350, :graded => true, :points => 2) 
Submission.create(:response => "Response 2", :user => joe_student, :question => q1_a2_4350, :graded => true, :points => 6) 
Submission.create(:response => "Response 2", :user => jane_student, :question => q1_a2_4350, :graded => true, :points => 7) 
Submission.create(:response => "Response 2", :user => mark_student, :question => q1_a2_4350, :graded => true, :points => 8) 
Submission.create(:response => "Response 2", :user => mary_student, :question => q1_a2_4350, :graded => true, :points => 9) 

a3_4350 = Assignment.create(:course => comp4350, :title => "Snapshot 2", :description => "Snapshot 2", :value => 10, :due => "29/2/2012")
q1_a3_4350 = Question.create(:assignment => a3_4350, :question => "Snapshot 2", :points => 10)
Submission.create(:response => "Response 3", :user => zapp_student, :question => q1_a3_4350, :graded => true, :points => 10) 
Submission.create(:response => "Response 3", :user => jeremy_student, :question => q1_a3_4350, :graded => true, :points => 5) 
Submission.create(:response => "Response 3", :user => nathan_student, :question => q1_a3_4350, :graded => true, :points => 5) 
Submission.create(:response => "Response 3", :user => joe_student, :question => q1_a3_4350, :graded => true, :points => 7) 
Submission.create(:response => "Response 3", :user => jane_student, :question => q1_a3_4350, :graded => true, :points => 8) 
Submission.create(:response => "Response 3", :user => mark_student, :question => q1_a3_4350, :graded => true, :points => 9) 
Submission.create(:response => "Response 3", :user => mary_student, :question => q1_a3_4350, :graded => true, :points => 10) 

a4_4350 = Assignment.create(:course => comp4350, :title => "Snapshot 3", :description => "Snapshot 3", :value => 10, :due => "16/3/2012")
q1_a4_4350 = Question.create(:assignment => a4_4350, :question => "Snapshot 3", :points => 10)
Submission.create(:response => "Response 4", :user => zapp_student, :question => q1_a4_4350, :graded => true, :points => 10) 
Submission.create(:response => "Response 4", :user => jeremy_student, :question => q1_a4_4350, :graded => true, :points => 10) 
Submission.create(:response => "Response 4", :user => nathan_student, :question => q1_a4_4350, :graded => true, :points => 10) 
Submission.create(:response => "Response 4", :user => joe_student, :question => q1_a4_4350, :graded => true, :points => 7) 
Submission.create(:response => "Response 4", :user => jane_student, :question => q1_a4_4350, :graded => true, :points => 7) 
Submission.create(:response => "Response 4", :user => mark_student, :question => q1_a4_4350, :graded => true, :points => 8) 
Submission.create(:response => "Response 4", :user => mary_student, :question => q1_a4_4350, :graded => true, :points => 8) 

a5_4350 = Assignment.create(:course => comp4350, :title => "Final Implementation", :description => "Final Implementation", :value => 25, :due => "25/3/2012")
q1_a5_4350 = Question.create(:assignment => a5_4350, :question => "Final Implementation", :points => 25)
Submission.create(:response => "Response 5", :user => zapp_student, :question => q1_a5_4350, :graded => false, :points => 0) 

a6_4350 = Assignment.create(:course => comp4350, :title => "Presentation", :description => "Presentation", :value => 5, :due => "5/4/2012")
q1_a6_4350 = Question.create(:assignment => a6_4350, :question => "Presentation", :points => 5)
Submission.create(:response => "Response 6", :user => zapp_student, :question => q1_a6_4350, :graded => false, :points => 0) 

a7_4350 = Assignment.create(:course => comp4350, :title => "Peer Evaluation", :description => "Peer Evaluation", :value => 5, :due => "5/4/2012")
q1_a7_4350 = Question.create(:assignment => a7_4350, :question => "Peer Evaluation", :points => 5)
Submission.create(:response => "Response 7", :user => zapp_student, :question => q1_a7_4350, :graded => false, :points => 0) 

a8_4350 = Assignment.create(:course => comp4350, :title => "Term Test", :description => "Term Test", :value => 30, :due => "13/3/2012")
q1_a8_4350 = Question.create(:assignment => a8_4350, :question => "Q1", :points => 5)
Submission.create(:response => "Response 1", :user => zapp_student, :question => q1_a8_4350, :graded => false, :points => 0) 

#### COMP4050
a1_4050 = Assignment.create(:course => comp4050, :title => "Final Examination", :description => "Project Proposal", :value => 100, :due => "16/4/2012")
q1_a1_4050 = Question.create(:assignment => a1_4050, :question => "Proposal", :points => 5)
Submission.create(:response => "Response 1", :user => mary_student, :question => q1_a1_4050, :graded => true, :points => 5) 
Submission.create(:response => "Response 1", :user => zapp_student, :question => q1_a1_4050, :graded => false, :points => 0) 

#### COMP4190
a1_4190 = Assignment.create(:course => comp4190, :title => "Final Examination", :description => "Project Proposal", :value => 100, :due => "14/4/2012")
q1_a1_4190 = Question.create(:assignment => a1_4190, :question => "Proposal", :points => 10)
Submission.create(:response => "Response 1", :user => mary_student, :question => q1_a1_4190, :graded => true, :points => 10) 
Submission.create(:response => "Response 1", :user => zapp_student, :question => q1_a1_4190, :graded => false, :points => 0) 

#### COMP4580
a1_4580 = Assignment.create(:course => comp4580, :title => "Final Examination", :description => "Project Proposal", :value => 100, :due => "17/4/2012")
q1_a1_4580 = Question.create(:assignment => a1_4580, :question => "Proposal", :points => 29)
Submission.create(:response => "Response 1", :user => mary_student, :question => q1_a1_4580, :graded => true, :points => 28) 
Submission.create(:response => "Response 1", :user => zapp_student, :question => q1_a1_4580, :graded => false, :points => 0) 
