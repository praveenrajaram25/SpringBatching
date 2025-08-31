# SpringBatching
A Batching application which loads huge volume of data using spring batch module

Spring Batch uses a set of interfaces such as Job,Step,JobLauncher,ItemReader,ItemProcessor,ItemWriter to perform huge volume
of parallel data transition,migration effectively.

StudentJob reads the data from students.csv and loads the data into college_students table

SpringBatch Flow:

          +------------------+
          |   JobLauncher    |
          +------------------+
                    |
                    v
          +------------------+
          |       Job        |
          +------------------+
                    |
         -------------------------
        |            Step          |
        |   (Reader-Processor-Writer)
         -------------------------
        |   ItemReader  ---------> Reads input
        |        |
        |   ItemProcessor -------> Transforms data
        |        |
        |   ItemWriter  ---------> Writes to output
         -------------------------
                    |
          +------------------+
          |  JobRepository   |
          | (Execution Meta) |
          +------------------+

API:

Run Student DataLoad Job- http://localhost:8080/Batching/studentJob
Suspend a Job- http://localhost:8080/Batching/stop?executionId={enter job id from meta data [batch_job_execution] table}
Job Resume - http://localhost:8080/Batching/resume?executionId={enter job id from meta data [batch_job_execution] table}
Job Status - http://localhost:8080/Batching/status?executionId={enter job id from meta data [batch_job_execution] table}

SQL :

CREATE TABLE college_students (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    department VARCHAR(50) NOT NULL,
    age INT CHECK (age BETWEEN 17 AND 30),
    dob DATE NOT NULL,
    address VARCHAR(255),
    type ENUM('dayscholar', 'hostel') NOT NULL,
    year ENUM('1st year', '2nd year', '3rd year', '4th year') NOT NULL,
    batch VARCHAR(20) NOT NULL,
    cgpa DECIMAL(4,2) CHECK (cgpa BETWEEN 0.00 AND 10.00)
);
COMMIT;
