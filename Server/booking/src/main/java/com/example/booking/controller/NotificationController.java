package com.example.booking.controller;

        import com.example.booking.dto.*;
        import com.example.booking.model.Notification;
        import com.example.booking.service.interfaces.IAccommodationService;
        import com.example.booking.service.interfaces.INotificationService;
        import com.example.booking.service.interfaces.IRatingCommentService;
        import com.example.booking.service.interfaces.IUserService;
        import org.springframework.http.HttpStatus;
        import org.springframework.http.MediaType;
        import org.springframework.http.ResponseEntity;
        import org.springframework.security.access.prepost.PreAuthorize;
        import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notifications/")
public class NotificationController {

    private final INotificationService notificationService;

    public NotificationController(INotificationService notificationService){
        this.notificationService = notificationService;
    }

    @GetMapping(value = "{receiverId}", produces = MediaType.APPLICATION_JSON_VALUE)
//    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> getLastUnreceivedByReceiverId(@PathVariable("receiverId") Long receiverId) {
        try {
            Notification notification = this.notificationService.getLastUnreceivedByReceiverId(receiverId).get();
            return new ResponseEntity<>(notification.parseToDisplay(), HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(new MessageDTO(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
//    @PreAuthorize("hasAuthority('OWNER')")
    public ResponseEntity<?> createNotification(@RequestBody NotificationDTO notification){
        this.notificationService.createNotification(notification);
        return new ResponseEntity<>(new MessageDTO("Created new notification"), HttpStatus.OK);
    }

    @GetMapping(value = "all/{receiverId}", produces = MediaType.APPLICATION_JSON_VALUE)
//    @PreAuthorize("hasAnyAuthority('OWNER','GUEST')")
    public ResponseEntity<?> getAllByReceiverId(@PathVariable("receiverId") Long receiverId) {
        try {
            return new ResponseEntity<>(this.notificationService.getAllByReceiverId(receiverId), HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(new MessageDTO(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
}
