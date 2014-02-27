package org.apache.hadoop.examples;

import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;

public class Crime {

  public static class CrimeMapper 
       extends Mapper<LongWritable, Text, Text, IntWritable>{
    
    private final static IntWritable one = new IntWritable(1);
      
    public void map(LongWritable key, Text value, Context context
                    ) throws IOException, InterruptedException {
    
    	try{
    	//initializing location , crimetype values to null
    	String easting=null;
    	String northing=null;
    	String Crime_type=null;
    	// Splitting the line with Comma delimiter , and storing each split into an array.
    	String[] fields = value.toString().split(",");
        //Region definition 2: first three digits of the coordinates to define a region
        //. (535xx, 726xx) is one region
    	try{
    	if((fields[4].length() == 6)&&(fields[4].matches("[0-9]+")==true)&&(fields[5].length()==6)&&(fields[5].matches("[0-9]+")==true))
    	{
    	//Easting = FirstThreeFieldsXXX
    	 easting = fields[4].substring(0,3);
        //northing = FirstThreeFieldsXXX
    	 northing = fields[5].substring(0,3);
    	//Check if easting and northing  is integer value
    	}
    	else
    	{
    		 easting="missing ";
       	     northing="missing";
    	}
    	}catch(Exception e){
   	    easting="missing";
   	     northing="missing";
   	}
    	try{
    	if((fields[7].matches("[0-9]+")==false)&&fields[7].length() > 0){

          //Crime type is the 7th field in the Line
          Crime_type = fields[7];
          //Check is Crime type is  a line  
    	}
    	else{
    		Crime_type="not valid type";
    	}
    	}catch(Exception e){
   	      Crime_type="not valid type";
   	}
        //Bind these into single String
        String Final_field = " E: " + easting+"xxx"+"  N: "+northing+"xxx   "+Crime_type+" : ";
        context.write(new Text(Final_field), one);
  	}	catch(Exception e){
 	      //Do Nothing - Ignore the String
 	}
    }
  }
  
  public static class IntSumReducer 
       extends Reducer<Text,IntWritable,Text,IntWritable> {
    private IntWritable result = new IntWritable();

    public void reduce(Text key, Iterable<IntWritable> values, 
                       Context context
                       ) throws IOException, InterruptedException {
      int sum = 0;
      for (IntWritable val : values) {
        sum += val.get();
      }
      result.set(sum);
      context.write(key, result);
    }
  }

  public static void main(String[] args) throws Exception {
    Configuration conf = new Configuration();
   
    Job job = new Job(conf, "crimer");
    job.setJarByClass(Crime.class);
    job.setMapperClass(CrimeMapper.class);
    //Max Splitsize in Bytes
    //33554432 =32MB
    //67108864 = 64MB
    //1048576=1MB
    //Default = 128MB for cluster05master  file system
    //minimumSize < blockSize < maximumSize
    FileInputFormat.setMaxInputSplitSize(job, 1048576); //Set this to modify number ofmappers
    //FileInputFormat.setMinInputSplitSize(Job, long)
    job.setCombinerClass(IntSumReducer.class);
    job.setReducerClass(IntSumReducer.class);
    job.setOutputKeyClass(Text.class);
    //Set number of reducer tasks to 2
    //job.setNumReduceTasks(2);
    job.setOutputValueClass(IntWritable.class);
  
    FileInputFormat.addInputPath(job, new Path(args[0]));
    FileOutputFormat.setOutputPath(job, new Path(args[1]));
    System.exit(job.waitForCompletion(true) ? 0 : 1);
  }
}

