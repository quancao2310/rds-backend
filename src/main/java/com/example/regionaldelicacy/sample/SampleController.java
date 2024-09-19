package com.example.regionaldelicacy.sample;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/samples")
@Tag(name = "Sample", description = "Sample API")
public class SampleController {
    private final SampleService sampleService;

    public SampleController(SampleService sampleService) {
        this.sampleService = sampleService;
    }

    @Operation(summary = "Get all samples", description = "Retrieve a list of all samples")
    @ApiResponse(responseCode = "200", description = "List of samples",
            content = {@Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = Sample.class)))})
    @GetMapping("")
    public List<Sample> getAllSamples(
            @Parameter(description = "Category of the Sample to be filtered") @RequestParam(
                    required = false) String category) {
        if (category == null) {
            return sampleService.getAllSamples();
        }
        return sampleService.getSamplesByCategory(category);
    }

    @Operation(summary = "Create a new sample", description = "Create a new sample")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Sample created",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Sample.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content)})
    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public Sample createSample(@RequestBody Sample sample) {
        return sampleService.saveSample(sample);
    }

    @Operation(summary = "Get a sample by ID", description = "Retrieve a sample by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sample found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Sample.class))}),
            @ApiResponse(responseCode = "404", description = "Sample not found",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid ID supplied",
                    content = @Content)})
    @GetMapping("/{id}")
    public ResponseEntity<Sample> getSampleById(
            @Parameter(description = "ID of the Sample") @PathVariable Long id) {
        Optional<Sample> sample = sampleService.getSampleById(id);
        return sample.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Update a sample", description = "Update a sample by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sample updated",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Sample.class))}),
            @ApiResponse(responseCode = "404", description = "Sample not found",
                    content = @Content)})
    @PutMapping("/{id}")
    public ResponseEntity<Sample> updateSample(
            @Parameter(description = "ID of the Sample") @PathVariable Long id,
            @RequestBody Sample sampleDetails) {
        Optional<Sample> sample = sampleService.updateSample(id, sampleDetails);
        return sample.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Delete a sample", description = "Delete a sample by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Sample deleted", content = @Content),
            @ApiResponse(responseCode = "404", description = "Sample not found",
                    content = @Content)})
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteSample(
            @Parameter(description = "ID of the Sample") @PathVariable Long id) {
        boolean deleted = sampleService.deleteSample(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
