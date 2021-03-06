
package com.my;

import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import com.my.sink.AlertSink;
import com.my.entity.Alert;
import com.my.entity.Transaction;
import com.my.source.TransactionSource;

/**
 * Skeleton code for the datastream walkthrough
 */
public class FraudDetectionJob {
	public static void main(String[] args) throws Exception {
		StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

		//env.setParallelism(2);
		DataStream<Transaction> transactions = env
			.addSource(new TransactionSource())
			.name("transactions");



		DataStream<Alert> alerts = transactions
			.keyBy(Transaction::getAccountId)
			.process(new FraudDetector())
			.name("process信息");

		alerts
			.addSink(new AlertSink())
			.name("sink信息");

		env.execute("欺诈检测");
		//System.out.println(env.getExecutionPlan());
	}
}
