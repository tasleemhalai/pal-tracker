package io.pivotal.pal.tracker;

import org.apache.tomcat.jni.Time;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InMemoryTimeEntryRepository implements TimeEntryRepository{

    HashMap<Long,TimeEntry> timeEntryHashMap=new HashMap<Long, TimeEntry>();
    @Override
    public TimeEntry create(TimeEntry te){

        long id=timeEntryHashMap.size()+1;

        timeEntryHashMap.put(id,te);
        te.setId(id);
        return te;

    }

    @Override
    public TimeEntry find(long id) {
       return timeEntryHashMap.get(id);
    }

    @Override
    public  List<TimeEntry>  list() {
   return new ArrayList<TimeEntry>(timeEntryHashMap.values());
    }


    @Override
    public TimeEntry update(long id, TimeEntry te) {

        te.setId(id);
        timeEntryHashMap.put(id,te);

        return timeEntryHashMap.get(id);
    }

    @Override
    public void delete(long id) {
        timeEntryHashMap.remove(id);
    }

    @Override
    public ResponseEntity<TimeEntry> read(long id) {
        return null;
    }
}
