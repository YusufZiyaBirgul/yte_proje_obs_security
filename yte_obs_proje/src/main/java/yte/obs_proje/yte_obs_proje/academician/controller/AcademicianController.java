package yte.obs_proje.yte_obs_proje.academician.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import yte.obs_proje.yte_obs_proje.academician.controller.request.AddAcademicianRequest;
import yte.obs_proje.yte_obs_proje.academician.controller.request.UpdateAcademicianRequest;
import yte.obs_proje.yte_obs_proje.academician.controller.response.AcademicianQueryModel;
import yte.obs_proje.yte_obs_proje.academician.entity.Academician;
import yte.obs_proje.yte_obs_proje.academician.service.AcademicianService;
import yte.obs_proje.yte_obs_proje.common.response.MessageResponse;
import yte.obs_proje.yte_obs_proje.security.AuthenticationService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/academicians")
@RequiredArgsConstructor
@Validated
public class AcademicianController {


    private final AcademicianService academicianService;
    private final AuthenticationService authenticationService;

    @PostMapping
    @PreAuthorize("hasAuthority('Academician')")
    public MessageResponse addAcademician(@Valid @RequestBody AddAcademicianRequest addAcademicianRequest) {
        Academician academician=authenticationService.academician();
        return academicianService.addAcademician(addAcademicianRequest.toDomainEntity());
    }

    @PutMapping("{id}")
    public MessageResponse updateAcademician(@Valid @RequestBody UpdateAcademicianRequest updateAcademicianRequest, @PathVariable Long id) {
        return academicianService.updateAcademician(id, updateAcademicianRequest.toDomainEntity());
    }

    @DeleteMapping("{id}")
    public MessageResponse deleteAcademician(@PathVariable Long id) {
        return academicianService.deleteAcademician(id);
    }


    @GetMapping
    public List<AcademicianQueryModel> getAllAcademician() {
        return academicianService.getAllAcademician()
                .stream()
                .map(academician -> new AcademicianQueryModel(academician))
                .toList();
    }

    @GetMapping("{id}")
    public AcademicianQueryModel getAcademicianById(@PathVariable Long id) {
        return new AcademicianQueryModel(academicianService.getById(id));
    }


}
