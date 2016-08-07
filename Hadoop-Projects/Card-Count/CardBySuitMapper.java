package mappers;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class CardBySuitMapper 
extends Mapper<LongWritable,Text,Text,LongWritable>{
	@Override
	
	public void map(LongWritable lineOffest, Text record, Context context) 
			throws IOException, InterruptedException{
		
		// split at \\| splits BLACK|SPADE|2 into Black, Spade and 2. [1] gives Spade
		String parts[] = record.toString().split("\\|");
		if(parts.length > 1){ // Doing a check here just to safeguard failure -ArrayOutOfBounds
			context.write(new Text(parts[1]), new LongWritable(1));
		}
	}
} 
