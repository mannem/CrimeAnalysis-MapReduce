CrimeAnalysis-MapReduce
=======================

Map reduce code for Crime analyssi of Large POLICE data collected . Studied Performance and working of Hadoop HDFS


Cloud Computing - project1
Raja jaya chandra Mannem
rxm124330@utdallas.edu
Hadoop-1.1.0

Contents:
1. Project1 Report.doc  - Contains report of performances and jobs
2. outputs.txt
3. Job tracker log for single job.txt
4. graphs.xlsx - contains performance graphs
5. fsimage.txt - FS Image
6. proj1 - project description
7. data_distr.txt - Input data distribution.
8. READ ME.txt - Readme file 
9. Crime - Folder contains source including hadoop libraries . 
   java location - Crime\src\org\apache\hadoop\examples

IDE USED - Eclipse
1.Install hadoop plugin compatible with 1.1.0 in Eclipse IDE
2.Import java file to a new Hadoop project :
 File->import
3. Export the compiled code as Jar file , with class file linked to this jar . 
4. Copy this jar file to a local unix directory.
5. SCP this jar file to Hadoop cluster
	scp Crime.jar rxm124330@cluster05master:/home/cloud2014/rxm124330/Crime.jar
6. Open the Hadoop cluster  and run the jar file for input located at  /user/cloud/full_input/single/
	
	hadoop jar /home/cloud2014/rxm124330/Crime.jar /user/cloud/full_input/single/ /user/cloud2014/rxm124330/Crime/test1

7. See the output at  /user/cloud2014/rxm124330/Crime/test1
hadoop fs -cat /user/cloud2014/rxm124330/Crime/test1/part-r-00000

8.see the hadoop logs at
 /home/cloud/hadoop_logs/

