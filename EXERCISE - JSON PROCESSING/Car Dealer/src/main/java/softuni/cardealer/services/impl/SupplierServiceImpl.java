package softuni.cardealer.services.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.cardealer.domain.dtos.exportDtos.LocalSupplierExportDto;
import softuni.cardealer.domain.dtos.importDtos.SupplierSeedDto;
import softuni.cardealer.domain.entities.Supplier;
import softuni.cardealer.domain.repositories.SupplierRepository;
import softuni.cardealer.services.SupplierService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Service
public class SupplierServiceImpl implements SupplierService {
    private final static String SUPPLIER_JSON = "src/main/resources/jsons/suppliers.json";
    private final SupplierRepository supplierRepository;
    private final ModelMapper modelMapper;
    private final Gson gson;

    @Autowired
    public SupplierServiceImpl(SupplierRepository supplierRepository, ModelMapper modelMapper, Gson gson) {
        this.supplierRepository = supplierRepository;
        this.modelMapper = modelMapper;
        this.gson = gson;
    }

    @Override
    public void seedSupplier() throws IOException {
        String content = String.join("", Files.readAllLines(Path.of(SUPPLIER_JSON)));
        SupplierSeedDto[] supplierSeedDtos = this.gson.fromJson(content, SupplierSeedDto[].class);
        Arrays.stream(supplierSeedDtos)
                .forEach(dto -> this.supplierRepository.saveAndFlush(
                        this.modelMapper.map(dto, Supplier.class)
                ));
    }

    @Override
    public String localSuppliers() {
        Set<Supplier> allByImporterFalse = this.supplierRepository.getAllByImporterFalse();
        List<LocalSupplierExportDto> toExport = new ArrayList<>();
        for (Supplier supplier : allByImporterFalse) {
            LocalSupplierExportDto exportDto = this.modelMapper.map(supplier, LocalSupplierExportDto.class);
            exportDto.setPartsCount(supplier.getParts().size());
            toExport.add(exportDto);
        }

        String toJson = this.gson.toJson(toExport);

        return toJson;
    }
}
