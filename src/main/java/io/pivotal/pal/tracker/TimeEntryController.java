package io.pivotal.pal.tracker;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/time-entries")
public class TimeEntryController {

  private TimeEntryRepository timeEntryRepository;
   public TimeEntryController(TimeEntryRepository timeEntryRepository){
       this.timeEntryRepository=timeEntryRepository;
   }

   @PostMapping
    public ResponseEntity create(@RequestBody TimeEntry timeEntry){

    TimeEntry newTimeEntry=timeEntryRepository.create(timeEntry);
    return new ResponseEntity(newTimeEntry, HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<TimeEntry> read(@PathVariable long id){
     TimeEntry timeEntry = timeEntryRepository.find(id);
     if(timeEntry==null){
         return new ResponseEntity<TimeEntry>(HttpStatus.NOT_FOUND);
     }
     return new ResponseEntity<>(timeEntry,HttpStatus.OK);

    }

    @GetMapping
    public ResponseEntity<List<TimeEntry>> list(){
       return new ResponseEntity<List<TimeEntry>>(timeEntryRepository.list(),HttpStatus.OK);
}
@PutMapping("{id}")
    public ResponseEntity update(@PathVariable long id, @RequestBody TimeEntry timeEntry){
    TimeEntry updatedTimeEntry=    timeEntryRepository.update(id,timeEntry);
    if(updatedTimeEntry==null){
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity(updatedTimeEntry,HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity delete(@PathVariable long id){
        timeEntryRepository.delete(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }


}