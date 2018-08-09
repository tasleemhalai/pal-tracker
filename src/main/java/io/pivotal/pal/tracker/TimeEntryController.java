package io.pivotal.pal.tracker;

import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.boot.actuate.metrics.GaugeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/time-entries")
public class TimeEntryController {

  private TimeEntryRepository timeEntryRepository;
  private CounterService counterService;
  private GaugeService gauge;
   public TimeEntryController(TimeEntryRepository timeEntryRepository, CounterService service,GaugeService gauge){

       this.timeEntryRepository=timeEntryRepository;
       this.counterService=service;
       this.gauge=gauge;
   }

   @PostMapping
    public ResponseEntity create(@RequestBody TimeEntry timeEntry){

    TimeEntry newTimeEntry=timeEntryRepository.create(timeEntry);
       counterService.increment("TimeEntry.created");
       gauge.submit("timeEntries.count", timeEntryRepository.list().size());
    return new ResponseEntity(newTimeEntry, HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<TimeEntry> read(@PathVariable long id){
     TimeEntry timeEntry = timeEntryRepository.find(id);
     if(timeEntry==null){

         return new ResponseEntity<TimeEntry>(HttpStatus.NOT_FOUND);
     }
     counterService.increment("TimeEntry.read");
     return new ResponseEntity<>(timeEntry,HttpStatus.OK);

    }

    @GetMapping
    public ResponseEntity<List<TimeEntry>> list(){
       counterService.increment("TimeEntry.listed");
       return new ResponseEntity<List<TimeEntry>>(timeEntryRepository.list(),HttpStatus.OK);
}
@PutMapping("{id}")
    public ResponseEntity update(@PathVariable long id, @RequestBody TimeEntry timeEntry){
    TimeEntry updatedTimeEntry=    timeEntryRepository.update(id,timeEntry);
    if(updatedTimeEntry==null){
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }
    counterService.increment("TimeEntry.updated");
    return new ResponseEntity(updatedTimeEntry,HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity delete(@PathVariable long id){
        timeEntryRepository.delete(id);
        counterService.increment("TimeEntry.deleted");
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }


}
