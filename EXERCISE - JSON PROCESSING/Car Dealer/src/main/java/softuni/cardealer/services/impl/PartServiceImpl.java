package softuni.cardealer.services.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.cardealer.domain.dtos.importDtos.PartSeedDto;
import softuni.cardealer.domain.entities.Part;
import softuni.cardealer.domain.entities.Supplier;
import softuni.cardealer.domain.repositories.PartRepository;
import softuni.cardealer.domain.repositories.SupplierRepository;
import softuni.cardealer.services.PartService;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.Random;

@Service
public class PartServiceImpl implements PartService {
    private final static String PART_JSON = "src/main/resources/jsons/parts.json";
    private final PartRepository partRepository;
    private final SupplierRepository supplierRepository;
    private final ModelMapper modelMapper;
    private final Gson gson;

    @Autowired
    public PartServiceImpl(PartRepository partRepository, SupplierRepository supplierRepository, ModelMapper modelMapper, Gson gson) {
        this.partRepository = partRepository;
        this.supplierRepository = supplierRepository;
        this.modelMapper = modelMapper;
        this.gson = gson;
    }

    @Override
    public void seedParts() throws Exception {
        String content = String.join("", Files.readAllLines(Path.of(PART_JSON)));
        PartSeedDto[] partSeedDtos = this.gson.fromJson(content, PartSeedDto[].class);
        for (PartSeedDto partSeedDto : partSeedDtos) {
            Part part = this.modelMapper.map(partSeedDto, Part.class);
            part.setSupplier(getRandomSupplier());
            this.partRepository.saveAndFlush(part);
        }

    }

    private Supplier getRandomSupplier() throws Exception {
        Random random = new Random();
        long index = random.nextInt((int) this.supplierRepository.count()) + 1;
        Optional<Supplier> supplier = this.supplierRepository.findById(index);
        if (supplier.isPresent()) {
            return supplier.get();
        } else {
            throw new Exception("Supplier doesn't exist!");
        }
    }
}
