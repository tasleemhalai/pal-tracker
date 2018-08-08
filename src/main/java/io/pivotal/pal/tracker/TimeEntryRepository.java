package io.pivotal.pal.tracker;

import org.springframework.http.ResponseEntity;

import java.util.List;

public interface TimeEntryRepository {
    public TimeEntry create(TimeEntry te);
    public TimeEntry find(long id);
    public  List<TimeEntry>  list();
    public TimeEntry update(long id,TimeEntry te);
    public void delete(long id);
    public  ResponseEntity<TimeEntry> read(long id);
}
