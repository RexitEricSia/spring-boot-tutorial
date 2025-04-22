package com.rexit.crondemo.cron;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class ScheduledMessageService {

    // Example: This method runs at 10:30 AM every day
    // @Scheduled(cron = "0 30 10 * * *")
    // Example: This method runs every Monday at 12:00 PM (noon)
    // @Scheduled(cron = "0 0 12 * * MON")

    @Scheduled(cron = "*/5 * * * * *")
    public void printMessage() {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        System.out.println("[" + timestamp + "] Cron job is working!");
    }
}
