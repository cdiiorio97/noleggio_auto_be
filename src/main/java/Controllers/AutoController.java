package Controllers;

import DTO.DtoAuto;
import Services.AutoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/auto")
public class AutoController {
    
    @Autowired
    private AutoService autoService;

    @GetMapping("/get-all")
    public ResponseEntity getauto() {
        List<DtoAuto> auto = autoService.trovaAuto();
        return ResponseEntity.ok(auto);
    }

    @GetMapping("/get-by-id")
    public ResponseEntity getAutoById(@RequestParam Integer id) {
        DtoAuto auto = autoService.trovaAutoById(id);
        return ResponseEntity.ok(auto);
    }
}
