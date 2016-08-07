package CardCount;

import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.partition.HashPartitioner;
import org.apache.hadoop.mapreduce.lib.reduce.LongSumReducer;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import mappers.CardBySuitMapper;
//import reducers.CardBySuitReducer;

public class CardCountBySuit extends Configured implements Tool{
	
	public int run(String[] args) throws Exception{
		Job job = Job.getInstance(getConf());
		
		job.setJarByClass(getClass()); // calls the classes in the jar for mappers , reducers
		
		FileInputFormat.setInputPaths(job, new Path(args[0]));
		
		job.setMapperClass(CardBySuitMapper.class);
		job.setMapOutputKeyClass(Text.class); // suit name
		job.setMapOutputValueClass(LongWritable.class);
		
		job.setPartitionerClass(HashPartitioner.class); // default, but just mentioning it
		job.setNumReduceTasks(2); // hinting at 2 reducers, but for small data mapreduce will use just 1
		
		job.setReducerClass(LongSumReducer.class) ; // default in build
		job.setOutputKeyClass(Text.class); // suit name
		job.setOutputValueClass(LongWritable.class); // number for each suit in deck
		
		FileOutputFormat.setOutputPath(job, new Path(args[1]));
		
		return job.waitForCompletion(true) ? 0 : 1;
	}
	
	public static void main(String[] args) throws Exception{
		System.exit(ToolRunner.run(new CardCountBySuit(), args)); // our current 
	}
}
