package com.example.regionaldelicacy.sample;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SampleService {
    private final SampleRepository sampleRepository;

    public SampleService(SampleRepository sampleRepository) {
        this.sampleRepository = sampleRepository;
    }

    public List<Sample> getAllSamples() {
        return sampleRepository.findAll();
    }

    public Optional<Sample> getSampleById(Long id) {
        return sampleRepository.findById(id);
    }

    public List<Sample> getSamplesByCategory(String category) {
        return sampleRepository.findByCategory(category);
    }

    public Sample saveSample(Sample sample) {
        return sampleRepository.save(sample);
    }

    public Optional<Sample> updateSample(Long id, Sample sampleDetails) {
        return sampleRepository.findById(id).map(sample -> {
            sample.setName(sampleDetails.getName());
            sample.setDescription(sampleDetails.getDescription());
            sample.setCategory(sampleDetails.getCategory());
            return sampleRepository.save(sample);
        });
    }

    public boolean deleteSample(Long id) {
        if (!sampleRepository.existsById(id)) {
            return false;
        }
        sampleRepository.deleteById(id);
        return true;
    }
}
