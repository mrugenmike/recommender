package com.recommender;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

public class SparkApp {

    public static void main(String[] args) throws SchedulerException {
        if(args.length<1){
            System.out.println("USAGE: java -jar com.pathSparkApp <frequencyAfterSeconds>");
            return;
        }
        final JobDetail eventCrunchJob = JobBuilder.newJob(EventCruncher.class).build();
        final SimpleTrigger trigger = TriggerBuilder.newTrigger().withDescription("Crunch Events from Mongo for FPGrowth").withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(Integer.parseInt(args[0])).repeatForever()).startNow().build();

        final Scheduler defaultScheduler = StdSchedulerFactory.getDefaultScheduler();
        defaultScheduler.start();
        defaultScheduler.scheduleJob(eventCrunchJob,trigger);
    }
}
